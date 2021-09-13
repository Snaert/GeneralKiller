package tasks;

import core.Task;
import data.Profile;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

public class Walking extends Task {
    private Profile profile;
    private String status;

    public Walking(Script script, Profile profile) {
        super(script);
        this.profile = profile;
    }

    public String status() {
        return this.status;
    }

    public boolean validate() throws InterruptedException {
        return this.script.getInventory().isFull() && !this.profile.getBankArea().contains(this.script.myPlayer()) || !this.script.getInventory().isFull() && !this.profile.getCowsArea().contains(this.script.myPlayer());
    }

    public boolean execute() throws InterruptedException {
        if (this.script.getInventory().isFull() && !this.profile.getBankArea().contains(this.script.myPlayer())) {
            this.status = "Walking to Bank";
            this.script.getWalking().webWalk(new Position[]{this.profile.getBankPosition()});
        } else {
            this.status = "Walking to NPCs";
            this.script.getWalking().webWalk(new Position[]{this.profile.getCowsPosition()});
        }

        return true;
    }
}