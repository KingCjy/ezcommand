package me.kingcjy.ezcommand.minecraft.command.executor;

import me.kingcjy.ezcommand.command.handler.CommandHandlerMapping;
import me.kingcjy.ezcommand.command.handler.InvocableHandlerMethod;
import me.kingcjy.ezcommand.minecraft.command.CommandArgument;
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
