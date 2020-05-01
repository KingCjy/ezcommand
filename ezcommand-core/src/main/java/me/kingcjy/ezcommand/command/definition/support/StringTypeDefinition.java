package me.kingcjy.ezcommand.command.definition.support;

import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.command.RootCommandArgument;
import me.kingcjy.ezcommand.command.definition.AbstractCommandTypeDefinition;

@Component
public class StringTypeDefinition extends AbstractCommandTypeDefinition<RootCommandArgument> {

    public static final String PATTERN = "[^\\s]*";
    public static final Class<?> TYPE = String.class;
    public static final String TYPE_STRING = "String";

    public StringTypeDefinition() {
        super(TYPE, TYPE_STRING, PATTERN);
    }

    @Override
    public Object transform(RootCommandArgument rootCommandArgument, String command) {
        return command;
    }
}
