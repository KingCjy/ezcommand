package me.kingcjy.ezcommand.command.registry;

import me.kingcjy.ezcommand.command.Handler.CommandHandlerMapping;

public interface CommandRegistry {
    void setHandlerMapping(CommandHandlerMapping commandHandlerMapping);
    void registerCommands();
}
