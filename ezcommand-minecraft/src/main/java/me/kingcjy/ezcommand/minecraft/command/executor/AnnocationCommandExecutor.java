package me.kingcjy.ezcommand.minecraft.command.executor;

import me.kingcjy.ezcommand.beans.factory.BeanFactory;
import me.kingcjy.ezcommand.command.handler.CommandHandlerMapping;
import me.kingcjy.ezcommand.command.handler.InvocableHandlerMethod;
import me.kingcjy.ezcommand.exception.CommandAdviceBean;
import me.kingcjy.ezcommand.minecraft.command.CommandArgument;
import me.kingcjy.ezcommand.utils.BeanUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AnnocationCommandExecutor implements CommandExecutor {
    private CommandAdviceBean commandAdviceBean;
    private CommandHandlerMapping commandHandlerMapping;

    public AnnocationCommandExecutor(CommandAdviceBean commandAdviceBean, CommandHandlerMapping commandHandlerMapping) {
        this.commandAdviceBean = commandAdviceBean;
        this.commandHandlerMapping = commandHandlerMapping;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        String cmd = getCommandString(label, args);
        CommandArgument commandArgument = new CommandArgument(commandSender, command, label, args, cmd);

        try {
            InvocableHandlerMethod invocableHandlerMethod = commandHandlerMapping.getHandler(cmd);
            invocableHandlerMethod.invoke(commandArgument, commandArgument, commandSender, command);
        } catch (RuntimeException e) {
            if(commandAdviceBean.supportException(e.getClass())) {
                commandAdviceBean.invokeExceptionHandlerMethod(commandArgument, e);
            }
        }

        return true;
    }

    private String getCommandString(String label, String[] args) {
        return label + " " + String.join(" ", args);
    }
}
