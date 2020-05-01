package me.kingcjy.ezcommand.command.resolver;


import me.kingcjy.ezcommand.command.RootCommandArgument;
import me.kingcjy.ezcommand.executor.method.MethodParameter;

public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter parameter);
    Object resolveParameter(RootCommandArgument commandArgument, MethodParameter parameter);
}
