package me.kingcjy.ezcommand.command.registry;

import me.kingcjy.ezcommand.command.handler.CommandHandlerMapping;

public interface CommandRegistry {
    void setHandlerMapping(CommandHandlerMapping commandHandlerMapping);
    void registerCommands();
}
