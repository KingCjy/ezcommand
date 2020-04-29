package me.kingcjy.ezcommand.command;

import me.kingcjy.ezcommand.command.CommandParser;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
import me.kingcjy.ezcommand.command.definition.support.LocationTypeDefinition;
import me.kingcjy.ezcommand.command.definition.support.PlayerTypeDefinition;
import me.kingcjy.ezcommand.command.definition.support.StringTypeDefinition;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParserTest {

    @Test
    public void 파싱_테스트() {
        CommandTypeDefinitionComposite commandTypeDefinitionComposite = new CommandTypeDefinitionComposite();
        commandTypeDefinitionComposite.addCommandTypeDefinition(new LocationTypeDefinition());
        commandTypeDefinitionComposite.addCommandTypeDefinition(new PlayerTypeDefinition());
        commandTypeDefinitionComposite.addCommandTypeDefinition(new StringTypeDefinition());

        CommandParser commandParser = new CommandParser(commandTypeDefinitionComposite, "teleport <playerName> <Location:location>");
//        CommandParser commandParser = new CommandParser(commandTypeDefinitionComposite, "message <Player:player> <message>");
        Assert.assertTrue(commandParser.matches("teleport KingCjy 2 0 0"));
//        Assert.assertTrue(commandParser.matches("teleport KingCjy hi"));
    }

    @Test
    public void RegexTest() throws Exception {

        LocationTypeDefinition locationTypeDefinition = new LocationTypeDefinition();

        CommandTypeDefinitionComposite commandTypeDefinitionComposite = new CommandTypeDefinitionComposite();
        commandTypeDefinitionComposite.addCommandTypeDefinition(locationTypeDefinition);

        String pattern = "\\ \\<(.*?)\\>";
        String command = "teleport <Vector3d:vector3d>";

        Map<String, String> typeMap = new HashMap<>();

        String regex = command;

        Matcher matcher = Pattern.compile(pattern).matcher(command);

        while(matcher.find()) {

            System.out.println(matcher.group(0));
            String parseCommand = matcher.group(1);

            if(hasType(parseCommand)) {
                String[] commandArr = parseCommand.split(":");

                String type = commandArr[0];
                String name = commandArr[1];

                typeMap.put(name, type);


                if(commandTypeDefinitionComposite.supportsType(type)) {
                    regex = regex.replace(matcher.group(0), " " + commandTypeDefinitionComposite.generateRegex(type, name));
                }
            }
        }

        Pattern pattern1 = Pattern.compile(regex);

        System.out.println(regex);
        System.out.println(pattern1.matcher("/teleport 1 2 3").find());
    }

    public boolean hasType(String parseCommand) {
        return parseCommand.split(":").length == 2 ? true : false;
    }

    @Test
    public void vector3dMatchTest() {
        String commandRegex = "teleport (?<vector3d>[0-9] [0-9] [0-9])";

        String commnad = "teleport 1 2 3";

        Matcher matcher = Pattern.compile(commandRegex).matcher(commnad);


        while(matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
        }
    }
}
