package me.kingcjy.ezcommand.command.definition.support;

import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.command.CommandArgument;
import me.kingcjy.ezcommand.command.definition.AbstractCommandTypeDefinition;

@Component
public class StringTypeDefinition extends AbstractCommandTypeDefinition {

    public static final String PATTERN = "[^\\s]*";
    public static final Class<?> TYPE = String.class;
    public static final String TYPE_STRING = "String";

    public StringTypeDefinition() {
        super(TYPE, TYPE_STRING, PATTERN);
    }

    @Override
    public Object transform(CommandArgument commandArgument, String command) {
        return command;
    }
}
