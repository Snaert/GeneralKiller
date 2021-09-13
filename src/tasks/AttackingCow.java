package tasks;

import org.osbot.rs07.utility.ConditionalSleep;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import data.Profile;
import core.Task;

public class AttackingCow extends Task
{
    private Profile profile;
    private String status;

    public AttackingCow(final Script script, final Profile profile) {
        super(script);
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
        if (!this.script.myPlayer().isUnderAttack() && this.script.myPlayer().getInteracting() == null) {
            final NPC cow = (NPC)this.script.getNpcs().closest(new Filter[] { (Filter)new Filter<NPC>() {
                public boolean match(final NPC npc) {
                    return npc.exists() && npc.getName().contains("Cow") && npc.getHealthPercent() == 100 && npc.isAttackable() && !npc.isUnderAttack();
                }
            } });
            if (cow != null) {
                this.status = "Attacking " + cow.getName();
                cow.interact(new String[] { "Attack" });
                new ConditionalSleep(3000, 1000) {
                    public boolean condition() throws InterruptedException {
                        return cow.isUnderAttack();
                    }
                }.sleep();
            }
        }
        return true;
    }
}