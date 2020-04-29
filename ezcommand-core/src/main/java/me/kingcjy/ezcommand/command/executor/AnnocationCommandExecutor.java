package me.kingcjy.ezcommand.command.executor;

import me.kingcjy.ezcommand.command.CommandArgument;
import me.kingcjy.ezcommand.command.Handler.CommandHandlerMapping;
import me.kingcjy.ezcommand.command.Handler.InvocableHandlerMethod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AnnocationCommandExecutor implements CommandExecutor {
    private CommandHandlerMapping commandHandlerMapping;

    public AnnocationCommandExecutor(CommandHandlerMapping commandHandlerMapping) {
        this.commandHandlerMapping = commandHandlerMapping;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        String cmd = getCommandString(label, args);
        CommandArgument commandArgs = new CommandArgument(commandSender, command, label, args, cmd);

        InvocableHandlerMethod invocableHandlerMethod = commandHandlerMapping.getHandler(cmd);
        invocableHandlerMethod.invoke(commandArgs, commandArgs, commandSender, command);

        return true;
    }

    private String getCommandString(String label, String[] args) {
        return label + " " + String.join(" ", args);
    }
}
