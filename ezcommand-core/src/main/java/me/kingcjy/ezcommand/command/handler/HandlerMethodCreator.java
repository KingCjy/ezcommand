package me.kingcjy.ezcommand.command.handler;

import java.util.Map;

public interface HandlerMethodCreator {
    Map<HandlerKey, InvocableHandlerMethod> createInvocableHandlerMethods(Object bean);
}
