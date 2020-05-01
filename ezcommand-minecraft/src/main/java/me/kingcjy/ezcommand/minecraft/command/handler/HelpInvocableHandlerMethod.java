package me.kingcjy.ezcommand.minecraft.command.handler;

import me.kingcjy.ezcommand.annotations.CommandMapping;
import me.kingcjy.ezcommand.command.RootCommandArgument;
import me.kingcjy.ezcommand.minecraft.annotations.Description;
import me.kingcjy.ezcommand.minecraft.annotations.Op;

import me.kingcjy.ezcommand.command.handler.InvocableHandlerMethod;
import me.kingcjy.ezcommand.minecraft.command.CommandArgument;
import me.kingcjy.ezcommand.utils.TagParser;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HelpInvocableHandlerMethod extends InvocableHandlerMethod {

    private Object bean;
    private Set<CommandInfo> commandInfos;
    private TagParser tagParser;

    public HelpInvocableHandlerMethod(TagParser tagParser, Object bean) {
        this.tagParser = tagParser;
        this.bean = bean;
        this.commandInfos = getCommandInfos(bean.getClass());
    }

    @Override
    public Object invoke(RootCommandArgument rootCommandArgument, Object... providedArguments) {
        CommandArgument commandArgument = (CommandArgument) rootCommandArgument;
        List<String> messages = new ArrayList<>();

        messages.add(tagParser.getFromTag("prefix"));
        for (CommandInfo commandInfo : commandInfos) {
            if((commandInfo.isOp() && commandArgument.getCommandSender().isOp()) || !commandInfo.isOp) {
                messages.add(replaceCommandAndDescription(commandInfo.getCommand(), commandInfo.getDescription()));
            }
        }
        messages.add(tagParser.getFromTag("suffix"));

        commandArgument.getCommandSender().sendMessage(messages.toArray(new String[] {}));

        return new Object();
    }

    private Set<CommandInfo> getCommandInfos(Class<?> targetClass) {
        Set<CommandInfo> commandInfos = new LinkedHashSet<>();

        boolean classOp = targetClass.getDeclaredAnnotation(Op.class) != null;

        for (Method method : targetClass.getDeclaredMethods()) {
            Description description = method.getDeclaredAnnotation(Description.class);
            boolean methodOp = method.getDeclaredAnnotation(Op.class) != null;

            if(description == null) {
                continue;
            }

            String command = getCommand(method);

            CommandInfo commandInfo = new CommandInfo(command, description.value(), classOp == true? classOp : methodOp);
            commandInfos.add(commandInfo);
        }

        return commandInfos;
    }

    private String getCommand(Method method) {
        CommandMapping commandMapping = method.getDeclaringClass().getDeclaredAnnotation(CommandMapping.class);

        if(commandMapping == null) {
            return method.getDeclaredAnnotation(CommandMapping.class).value();
        }

        return commandMapping.value() + " " + method.getDeclaredAnnotation(CommandMapping.class).value();
    }

    private String replaceCommandAndDescription(String command, String description) {
        return tagParser.getFromTag("command")
                .replaceAll("\\{help.command\\}", "/" + command)
                .replaceAll("\\{help.description\\}", description);
    }

    class CommandInfo {
        private String command;
        private String description;
        private boolean isOp;

        public CommandInfo(String command, String description, boolean isOp) {
            this.command = command;
            this.description = description;
            this.isOp = isOp;
        }

        public String getCommand() {
            return command;
        }

        public String getDescription() {
            return description;
        }

        public boolean isOp() {
            return isOp;
        }
    }
}
