package me.kingcjy.ezcommand.command.handler;

import java.util.Set;

public interface CommandHandlerMapping {
    void initialize();

    InvocableHandlerMethod getHandler(String command);

    Set<HandlerKey> getHandlerKeys();
}
