package me.kingcjy.ezcommand.command.definition;

import me.kingcjy.ezcommand.command.RootCommandArgument;

public abstract class AbstractCommandTypeDefinition<T extends RootCommandArgument> implements CommandTypeDefinition<T> {

    private Class<?> type;
    private String typeString;
    private String pattern;

    protected AbstractCommandTypeDefinition(Class<?> type, String typeString, String pattern) {
        this.type = type;
        this.typeString = typeString;
        this.pattern = pattern;
    }

    public boolean supportType(Class<?> type) {
        return this.type.equals(type);
    }
    public boolean supportType(String typeString) {
        return this.typeString.equals(typeString);
    }

    public String generateRegex(String name) {
        String regex = "(?<" + name + ">" + pattern + ")";
        return regex;
    }

    public String getTypeString() {
        return this.typeString;
    }
}
