package me.kingcjy.ezcommand.command.definition;

import me.kingcjy.ezcommand.command.CommandArgument;

public interface CommandTypeDefinition {
    boolean supportType(String type);
    boolean supportType(Class<?> type);
    String generateRegex(String name);
    Object transform(CommandArgument commandArgument, String command);

    String getTypeString();
}
