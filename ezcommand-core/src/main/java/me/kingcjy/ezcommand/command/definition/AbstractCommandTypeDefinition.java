package me.kingcjy.ezcommand.command.definition;

public abstract class AbstractCommandTypeDefinition implements CommandTypeDefinition {

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
