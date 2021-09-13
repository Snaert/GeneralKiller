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

public class AttackLootingChicken extends Task
{
    Profile profile;
    private String status;

    public AttackLootingChicken(final Script script, final Profile profile) {
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
        return !this.script.getInventory().isFull() && this.profile.getChickenArea().contains((Entity)this.script.myPlayer());
    }

    @Override
    public boolean execute() throws InterruptedException {
        final NPC chicken = (NPC)this.script.getNpcs().closest(new Filter[] { (Filter<NPC>)new Filter<NPC>() {
            public boolean match(final NPC npc) {
                return npc.exists() && npc.getName().contains("Chicken") && npc.getHealthPercent() == 100 && npc.isAttackable() && !npc.isUnderAttack();
            }
        } });
        final GroundItem feather = (GroundItem)this.script.getGroundItems().closest(new String[] { "Feather", "Bones" });
        if (chicken != null && feather != null) {
            if (this.script.myPosition().distance(chicken.getPosition()) <= this.script.myPosition().distance(feather.getPosition())) {
                this.attack(chicken);
            }
            else {
                this.loot(feather);

            }
        }
        else if (chicken != null) {
            this.attack(chicken);
        }
        else if (feather != null) {
            this.loot(feather);
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


    private void attack(final NPC chicken) {
        if (!this.script.myPlayer().isUnderAttack() && this.script.myPlayer().getInteracting() == null && chicken != null) {
            this.status = "Attacking " + chicken.getName();
            chicken.interact(new String[] { "Attack" });
            new ConditionalSleep(3000, 1000) {
                public boolean condition() throws InterruptedException {
                    return chicken.isUnderAttack();
                }
            }.sleep();
        }
    }

    private void loot(final GroundItem feather) {
        if (!this.script.myPlayer().isUnderAttack() && this.script.myPlayer().getInteracting() == null && feather != null) {
            this.status = "Looting " + feather.getName();
            feather.interact(new String[] { "Take" });
            new ConditionalSleep(5000, 250) {
                public boolean condition() throws InterruptedException {
                    return !feather.exists();
                }
            }.sleep();
        }
    }
}