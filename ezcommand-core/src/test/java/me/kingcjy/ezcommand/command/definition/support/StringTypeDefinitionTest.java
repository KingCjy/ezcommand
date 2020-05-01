package me.kingcjy.ezcommand.command.definition.support;

import me.kingcjy.ezcommand.command.RootCommandArgument;
import me.kingcjy.ezcommand.command.definition.support.StringTypeDefinition;

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
        RootCommandArgument commandArgument = new RootCommandArgument();

        String result = (String) stringTypeDefinition.transform(commandArgument, "1234");

        Assert.assertEquals(result, "1234");
    }
}
