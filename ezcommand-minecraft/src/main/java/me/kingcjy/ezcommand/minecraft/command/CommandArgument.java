package me.kingcjy.ezcommand.minecraft.command;

import me.kingcjy.ezcommand.command.RootCommandArgument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandArgument extends RootCommandArgument {
    private CommandSender commandSender;
    private Command command;
    private String label;
    private String[] args;

    public CommandArgument(CommandSender commandSender, Command command, String label, String[] args, String fullCommand) {
        this.commandSender = commandSender;
        this.command = command;
        this.label = label;
        this.args = args;
        this.fullCommand = fullCommand;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getFullCommand() {
        return fullCommand;
    }
}