package tasks;

import core.Task;
import data.Profile;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class Banking extends Task {
    private Profile profile;

    public Banking(Script script, Profile profile) {
        super(script);
        this.profile = profile;
    }

    public String status() {
        return "Banking";
    }

    public boolean validate() throws InterruptedException {
        return this.script.getInventory().isFull() && this.profile.getBankArea().contains(this.script.myPlayer());
    }

    public boolean execute() throws InterruptedException {
        if (this.profile.isDepositBox()) {
            Entity bank = this.script.getObjects().closest(new String[]{"Bank deposit box"});
            if (bank != null) {
                if (this.script.getDepositBox().isOpen()) {
                    if (this.script.getDepositBox().depositAll()) {
                        (new ConditionalSleep(1500, 250) {
                            public boolean condition() throws InterruptedException {
                                return Banking.this.script.getInventory().isEmpty();
                            }
                        }).sleep();
                    }
                } else if (bank.isVisible()) {
                    if (bank.interact(new String[]{"Deposit"})) {
                        (new ConditionalSleep(1500, 250) {
                            public boolean condition() throws InterruptedException {
                                return Banking.this.script.getBank().isOpen();
                            }
                        }).sleep();
                    }
                } else {
                    this.script.getWalking().walk(bank.getPosition());
                }
            }
        } else {
            NPC bank = (NPC)this.script.getNpcs().closest(new String[]{"Banker"});
            if (bank != null) {
                if (this.script.getBank().isOpen()) {
                    if (this.script.getBank().depositAll()) {
                        (new ConditionalSleep(1500, 250) {
                            public boolean condition() throws InterruptedException {
                                return Banking.this.script.getInventory().isEmpty();
                            }
                        }).sleep();
                    }
                } else if (bank.isVisible()) {
                    if (bank.interact(new String[]{"Bank"})) {
                        (new ConditionalSleep(1500, 250) {
                            public boolean condition() throws InterruptedException {
                                return Banking.this.script.getBank().isOpen();
                            }
                        }).sleep();
                    }
                } else {
                    this.script.getWalking().walk(bank.getPosition());
                }
            }
        }

        return true;
    }
}