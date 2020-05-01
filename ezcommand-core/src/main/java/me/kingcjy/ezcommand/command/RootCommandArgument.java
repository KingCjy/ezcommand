package me.kingcjy.ezcommand.command;

public class RootCommandArgument {
    protected String fullCommand;

    public RootCommandArgument() {}
    public RootCommandArgument(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    public String getFullCommand() {
        return fullCommand;
    }

    public void setFullCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }
}
