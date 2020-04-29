package me.kingcjy.ezcommand.command.definition;

import me.kingcjy.ezcommand.command.CommandArgument;

import java.util.HashSet;
import java.util.Set;

public class CommandTypeDefinitionComposite {

//    private static final Logger logger = Bukkit.getLogger();

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
//            logger.log(Level.SEVERE, "ERROR CommandType {0} Not Supported", type);
            return "";
        }

        return commandTypeDefinition.generateRegex(name);
    }

    public <T> T transform(CommandArgument commandArgument, String command, Class<T> type) {
        CommandTypeDefinition commandTypeDefinition = getCommandTypeDefinition(type);

        if(commandTypeDefinition == null) {
//            logger.log(Level.SEVERE, "ERROR CommandType {0} Not Supported", type);
            return null;
        }

        return (T) commandTypeDefinition.transform(commandArgument, command);
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
