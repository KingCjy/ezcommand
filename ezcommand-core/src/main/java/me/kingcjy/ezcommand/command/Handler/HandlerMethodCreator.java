package me.kingcjy.ezcommand.command.Handler;

import java.util.Map;

public interface HandlerMethodCreator {
    Map<HandlerKey, InvocableHandlerMethod> createInvocableHandlerMethods(Object bean);
}
