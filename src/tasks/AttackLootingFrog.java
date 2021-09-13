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

public class AttackLootingFrog extends Task
{
    Profile profile;
    private String status;

    public AttackLootingFrog(final Script script, final Profile profile) {
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
        return !this.script.getInventory().isFull() && this.profile.getFrogsArea().contains((Entity)this.script.myPlayer());
    }

    @Override
    public boolean execute() throws InterruptedException {
        final NPC frog = (NPC)this.script.getNpcs().closest(new Filter[] { (Filter<NPC>)new Filter<NPC>() {
            public boolean match(final NPC npc) {
                return npc.exists() && npc.getName().contains("Giant frog") && npc.getHealthPercent() == 100 && npc.isAttackable() && !npc.isUnderAttack();
            }
        } });
        final GroundItem bigbone = (GroundItem)this.script.getGroundItems().closest(new String[] { "Big bones" });
        if (frog != null && bigbone != null) {
            if (this.script.myPosition().distance(frog.getPosition()) <= this.script.myPosition().distance(bigbone.getPosition())) {
                this.attack(frog);
            }
            else {
                this.loot(bigbone);

            }
        }
        else if (frog != null) {
            this.attack(frog);
        }
        else if (bigbone != null) {
            this.loot(bigbone);
        }
        return true;
    }


    private void attack(final NPC frog) {
        if (!this.script.myPlayer().isUnderAttack() && this.script.myPlayer().getInteracting() == null && frog != null) {
            this.status = "Attacking " + frog.getName();
            frog.interact(new String[] { "Attack" });
            new ConditionalSleep(3000, 1000) {
                public boolean condition() throws InterruptedException {
                    return frog.isUnderAttack();
                }
            }.sleep();
        }
    }

    private void loot(final GroundItem bigbone) {
        if (!this.script.myPlayer().isUnderAttack() && this.script.myPlayer().getInteracting() == null && bigbone != null) {
            this.status = "Looting " + bigbone.getName();
            bigbone.interact(new String[] { "Take" });
            new ConditionalSleep(5000, 250) {
                public boolean condition() throws InterruptedException {
                    return !bigbone.exists();
                }
            }.sleep();
        }
    }
}