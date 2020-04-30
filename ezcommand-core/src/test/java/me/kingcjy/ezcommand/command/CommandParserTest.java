package me.kingcjy.ezcommand.command;

import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
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
        commandTypeDefinitionComposite.addCommandTypeDefinition(new StringTypeDefinition());

        CommandParser commandParser = new CommandParser(commandTypeDefinitionComposite, "message <playerName> <message>");
//        CommandParser commandParser = new CommandParser(commandTypeDefinitionComposite, "message <Player:player> <message>");
        Assert.assertTrue(commandParser.matches("message hi man"));
//        Assert.assertTrue(commandParser.matches("teleport KingCjy hi"));
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
