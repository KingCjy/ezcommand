package me.kingcjy.ezcommand.command.definition;

import me.kingcjy.ezcommand.EzLogger;
import me.kingcjy.ezcommand.command.RootCommandArgument;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandTypeDefinitionComposite<T extends RootCommandArgument> {

    private static final Logger logger = EzLogger.getLogger(CommandTypeDefinitionComposite.class);

    private Set<CommandTypeDefinition> commandTypeDefinitions = new HashSet<>();

    public CommandTypeDefinitionComposite() {}

    public CommandTypeDefinitionComposite(Set<CommandTypeDefinition> commandTypeDefinitions) {
        this.commandTypeDefinitions = commandTypeDefinitions;
    }

    public void addCommandTypeDefinition(CommandTypeDefinition commandTypeDefinition) {
        this.commandTypeDefinitions.add(commandTypeDefinition);
    }

    public void addCommandTypeDefinitions(Set<CommandTypeDefinition> commandTypeDefinitions) {
        this.commandTypeDefinitions.addAll(commandTypeDefinitions);
    }

    public boolean supportsType(String type) {
        return getCommandTypeDefinition(type) != null;
    }

    public String generateRegex(String type, String name) {
        CommandTypeDefinition commandTypeDefinition = getCommandTypeDefinition(type);

        if(commandTypeDefinition == null) {
            logger.log(Level.SEVERE, "[EzCommand] ERROR CommandType {0} Not Supported", type);
            return "";
        }

        return commandTypeDefinition.generateRegex(name);
    }

    public <T2> T2 transform(T commandArgument, String command, Class<T2> type) {
        CommandTypeDefinition commandTypeDefinition = getCommandTypeDefinition(type);

        if(commandTypeDefinition == null) {
            logger.log(Level.SEVERE, "[EzCommand] ERROR CommandType {0} Not Supported", type);
            return null;
        }

        return (T2) commandTypeDefinition.transform(commandArgument, command);
    }

    public String getTypeString(Class<?> targetClass) {
        return getCommandTypeDefinition(targetClass).getTypeString();
    }

    private CommandTypeDefinition getCommandTypeDefinition(String typeString) {
        for (CommandTypeDefinition commandTypeDefinition : commandTypeDefinitions) {
            if(commandTypeDefinition.supportType(typeString)) {
                return commandTypeDefinition;
            }
        }
        return null;
    }

    private CommandTypeDefinition getCommandTypeDefinition(Class<?> type) {
        for (CommandTypeDefinition commandTypeDefinition : commandTypeDefinitions) {
            if(commandTypeDefinition.supportType(type)) {
                return commandTypeDefinition;
            }
        }
        return null;
    }
}
