package tasks;

import core.Task;
import data.Profile;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class Looting extends Task {
    private Profile profile;
    private String status;

    public Looting(Script script, Profile profile) {
        super(script);
        this.profile = profile;
    }

    public String status() {
        return this.status;
    }

    public boolean validate() throws InterruptedException {
        return !this.script.getInventory().isFull() && this.profile.getCowsArea().contains(this.script.myPlayer());
    }

    public boolean execute() throws InterruptedException {
        final GroundItem cowhide = (GroundItem)this.script.getGroundItems().closest(new String[]{"Cowhide"});
        if (cowhide != null) {
            this.status = "Looting " + cowhide.getName();
            cowhide.interact(new String[]{"Take"});
            (new ConditionalSleep(5000, 250) {
                public boolean condition() throws InterruptedException {
                    return !cowhide.exists();
                }
            }).sleep();
        }

        return true;
    }
}