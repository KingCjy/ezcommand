package me.kingcjy.ezcommand.command;

import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    public static final String PATTERN = "\\ \\<(.*?)\\>";

    private CommandTypeDefinitionComposite commandTypeDefinitionComposite;

    private Pattern commandPattern;

    private Map<String, List<String>> typeNameMap = new HashMap<>();
    private Set<String> names = new HashSet<>();

    private String command;

    public CommandParser(CommandTypeDefinitionComposite commandTypeDefinitionComposite, String command) {
        this.commandTypeDefinitionComposite = commandTypeDefinitionComposite;
        this.command = command;
    }

    public void initialize() {
        this.commandTypeDefinitionComposite = commandTypeDefinitionComposite;

        String regex = command;

        Matcher matcher = Pattern.compile(PATTERN).matcher(command);

        while(matcher.find()) {
            String parseCommand = matcher.group(1);

            String[] commandArr = parseCommand.split(":");

            String type;
            String name;

            if(commandArr.length <= 1) {
                type = "String";
                name = commandArr[0];
            } else {
                type = commandArr[0];
                name = commandArr[1];
            }

            if(commandTypeDefinitionComposite.supportsType(type)) {
                regex = regex.replace(matcher.group(0), " " + commandTypeDefinitionComposite.generateRegex(type, name));

                List<String> names = typeNameMap.getOrDefault(type, new ArrayList<>());
                names.add(name);
                typeNameMap.put(type, names);
            }

            names.add(matcher.group(1));
        }

        regex+="$";
        commandPattern = Pattern.compile(regex);
    }

    public boolean matches(String command) {
        if(commandPattern == null) {
            initialize();
        }

        Matcher matcher = commandPattern.matcher(command);
        return matcher.find();
    }

    public <T> T trasform(CommandArgument commandArgument, String name, Class<T> type) {
        if(commandPattern == null) {
            initialize();
        }

        String typeString = commandTypeDefinitionComposite.getTypeString(type);

        if(typeNameMap.getOrDefault(typeString, new ArrayList<>()).contains(name) == false) {
            return null;
        }

        Matcher matcher = commandPattern.matcher(commandArgument.getFullCommand());

        if(matcher.find() == false) {
            return null;
        }

        String value = matcher.group(name).replaceAll("[\\<\\>]", "");

        return commandTypeDefinitionComposite.transform(commandArgument, value, type);
    }
}
