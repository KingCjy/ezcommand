package me.kingcjy.ezcommand.command.resolver;

import me.kingcjy.ezcommand.command.CommandArgument;
import me.kingcjy.ezcommand.executor.method.MethodParameter;

public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter parameter);
    Object resolveParameter(CommandArgument commandArgument, MethodParameter parameter);
}
