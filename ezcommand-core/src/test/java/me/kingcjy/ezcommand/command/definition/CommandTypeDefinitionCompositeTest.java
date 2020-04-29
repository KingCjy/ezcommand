package me.kingcjy.ezcommand.command.definition;

import me.kingcjy.ezcommand.command.CommandArgument;
import me.kingcjy.ezcommand.command.definition.AbstractCommandTypeDefinition;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommandTypeDefinitionCompositeTest {

    private CommandTypeDefinitionComposite commandTypeDefinitionComposite;

    private final String TEST_TYPE = "Integer";
    private final String PARAMETER_NAME = "x";

    @Before
    public void before() {
        commandTypeDefinitionComposite = new CommandTypeDefinitionComposite();
        commandTypeDefinitionComposite.addCommandTypeDefinition(new TestIntegerTypeDefinition());
    }

    @Test
    public void 타입_지원_테스트() {
        Assert.assertTrue(commandTypeDefinitionComposite.supportsType(TEST_TYPE));
    }

    @Test
    public void 정규식_생성_테스트() {
        final String regex = "(?<" + PARAMETER_NAME + ">[0-9])";
        String generatedRegex = commandTypeDefinitionComposite.generateRegex(TEST_TYPE, PARAMETER_NAME);

        Assert.assertEquals(regex, generatedRegex);
    }

    @Test
    public void 캐스팅_테스트() {
        CommandArgument commandArgument = new CommandArgument(null, null, null, null, null);
        Integer number = commandTypeDefinitionComposite.transform(commandArgument, "1", Integer.class);

        Assert.assertEquals(number.intValue(), 1);
    }

    private static class TestIntegerTypeDefinition extends AbstractCommandTypeDefinition {

        public static final Class<?> TYPE = Integer.class;
        public static final String TYPE_STRING = "Integer";
        public static final String PATTERN = "[0-9]";

        protected TestIntegerTypeDefinition() {
            super(TYPE, TYPE_STRING, PATTERN);
        }

        @Override
        public Object transform(CommandArgument commandArgument, String command) {
            return Integer.parseInt(command);
        }
    }
}
