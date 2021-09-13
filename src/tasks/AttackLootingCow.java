package tasks;

import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.utility.ConditionalSleep;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import data.Profile;
import core.Task;

public class AttackLootingCow extends Task
{
    Profile profile;
    private String status;

    public AttackLootingCow(final Script script, final Profile profile) {
        super(script);
        this.status = "Determining actions...";
        this.profile = profile;
    }

    @Override
    public String status() {
        return this.status;
    }

    @Override
    public boolean validate() throws InterruptedException {
        return !this.script.getInventory().isFull() && this.profile.getCowsArea().contains((Entity)this.script.myPlayer());
    }

    @Override
    public boolean execute() throws InterruptedException {
        final NPC cow = (NPC)this.script.getNpcs().closest(new Filter[] { (Filter)new Filter<NPC>() {
            public boolean match(final NPC npc) {
                return npc.exists() && npc.getName().contains("Cow") && npc.getHealthPercent() == 100 && npc.isAttackable() && !npc.isUnderAttack();
            }
        } });
        final GroundItem cowhide = (GroundItem)this.script.getGroundItems().closest(new String[] { "Cowhide", "Bones" });
        if (cow != null && cowhide != null) {
            if (this.script.myPosition().distance(cow.getPosition()) <= this.script.myPosition().distance(cowhide.getPosition())) {
                this.attack(cow);
            }
            else {
                this.loot(cowhide);

            }
        }
        else if (cow != null) {
            this.attack(cow);
        }
        else if (cowhide != null) {
            this.loot(cowhide);
        }
        final Inventory inven = script.getInventory();
        if (inven.contains("Bones")) {
            script.getInventory().getItem("Bones").interact("Bury");
            (new ConditionalSleep(1500, 250) {
                public boolean condition() throws InterruptedException {
                    return !script.getInventory().contains("Bones");
                }
            }).sleep();
        }
        return true;
    }


    private void attack(final NPC cow) {
        if (!this.script.myPlayer().isUnderAttack() && this.script.myPlayer().getInteracting() == null && cow != null) {
            this.status = "Attacking " + cow.getName();
            cow.interact(new String[] { "Attack" });
            new ConditionalSleep(3000, 1000) {
                public boolean condition() throws InterruptedException {
                    return cow.isUnderAttack();
                }
            }.sleep();
        }
    }

    private void loot(final GroundItem cowhide) {
        if (!this.script.myPlayer().isUnderAttack() && this.script.myPlayer().getInteracting() == null && cowhide != null) {
            this.status = "Looting " + cowhide.getName();
            cowhide.interact(new String[] { "Take" });
            new ConditionalSleep(5000, 250) {
                public boolean condition() throws InterruptedException {
                    return !cowhide.exists();
                }
            }.sleep();
        }
    }
}