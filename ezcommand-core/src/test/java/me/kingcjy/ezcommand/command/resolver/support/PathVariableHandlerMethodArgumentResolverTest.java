package me.kingcjy.ezcommand.command.resolver.support;

import me.kingcjy.ezcommand.annotations.CommandMapping;
import me.kingcjy.ezcommand.annotations.PathVariable;

import me.kingcjy.ezcommand.command.RootCommandArgument;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
import me.kingcjy.ezcommand.command.definition.support.StringTypeDefinition;
import me.kingcjy.ezcommand.command.resolver.support.PathVariableHandlerMethodArgumentResolver;
import me.kingcjy.ezcommand.executor.method.MethodParameter;
import org.junit.Assert;
import org.junit.Test;

public class PathVariableHandlerMethodArgumentResolverTest {

    @Test
    public void 어노테이션_파라미터_지원_테스트() throws Exception {

        CommandTypeDefinitionComposite commandTypeDefinitionComposite = new CommandTypeDefinitionComposite();
        commandTypeDefinitionComposite.addCommandTypeDefinition(new StringTypeDefinition());

        PathVariableHandlerMethodArgumentResolver pathVariableHandlerMethodArgumentResolver = new PathVariableHandlerMethodArgumentResolver(commandTypeDefinitionComposite);

        MethodParameter methodParameter = new MethodParameter(TestCommand.class.getDeclaredMethod("saySomething", String.class), 0);

        Assert.assertTrue(pathVariableHandlerMethodArgumentResolver.supportsParameter(methodParameter));
    }

    @Test
    public void 인젝션_테스트() throws Exception {
        CommandTypeDefinitionComposite commandTypeDefinitionComposite = new CommandTypeDefinitionComposite();
        commandTypeDefinitionComposite.addCommandTypeDefinition(new StringTypeDefinition());

        PathVariableHandlerMethodArgumentResolver pathVariableHandlerMethodArgumentResolver = new PathVariableHandlerMethodArgumentResolver(commandTypeDefinitionComposite);

        MethodParameter methodParameter = new MethodParameter(TestCommand.class.getDeclaredMethod("saySomething", String.class), 0);
        RootCommandArgument rootCommandArgument = new RootCommandArgument("/say KingCjy!!!");

        String result = (String) pathVariableHandlerMethodArgumentResolver.resolveParameter(rootCommandArgument, methodParameter);
        Assert.assertEquals(result, "KingCjy!!!");
    }

    private static class TestCommand {
        @CommandMapping("/say <message>")
        public String saySomething(@PathVariable String message) {
            return message;
        }
    }
}
