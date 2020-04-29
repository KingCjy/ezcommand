package me.kingcjy.ezcommand.command.definition.support;

import me.kingcjy.ezcommand.command.definition.support.StringTypeDefinition;
import me.kingcjy.ezcommand.command.CommandArgument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringTypeDefinitionTest {

    private StringTypeDefinition stringTypeDefinition;

    @Before
    public void before() {
        stringTypeDefinition = new StringTypeDefinition();
    }

    @Test
    public void String_캐스팅_테스트() {
        CommandArgument commandArgument = new CommandArgument(null, null, null, null, null);

        String result = (String) stringTypeDefinition.transform(commandArgument, "1234");

        Assert.assertEquals(result, "1234");
    }
}
