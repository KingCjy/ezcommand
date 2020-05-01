package me.kingcjy.ezcommand.command.definition;


import me.kingcjy.ezcommand.command.RootCommandArgument;

public interface CommandTypeDefinition<T extends RootCommandArgument> {
    boolean supportType(String type);
    boolean supportType(Class<?> type);
    String generateRegex(String name);
    Object transform(T t, String command);

    String getTypeString();
}
