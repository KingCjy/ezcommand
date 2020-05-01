package me.kingcjy.ezcommand.minecraft.command.definition;

import me.kingcjy.ezcommand.command.definition.AbstractCommandTypeDefinition;
import me.kingcjy.ezcommand.minecraft.command.CommandArgument;

public abstract class MinecraftCommandTypeDefinition extends AbstractCommandTypeDefinition<CommandArgument> {
    protected MinecraftCommandTypeDefinition(Class type, String typeString, String pattern) {
        super(type, typeString, pattern);
    }
}
