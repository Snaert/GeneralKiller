package core;

import org.osbot.rs07.script.Script;

public abstract class Task {
    public Script script;

    public Task(Script script) {
        this.script = script;
    }

    public String status() {
        return "";
    }

    public abstract boolean validate() throws InterruptedException;

    public abstract boolean execute() throws InterruptedException;
}
