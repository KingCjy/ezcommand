package me.kingcjy.ezcommand.command.Handler;

import java.util.Set;

public interface CommandHandlerMapping {
    void initialize();

    InvocableHandlerMethod getHandler(String command);

    Set<HandlerKey> getHandlerKeys();
}
