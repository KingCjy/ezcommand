package me.kingcjy.ezcommand.command.resolver;

import me.kingcjy.ezcommand.command.RootCommandArgument;
import me.kingcjy.ezcommand.executor.method.MethodParameter;

import java.util.*;

public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {

    private Set<HandlerMethodArgumentResolver> argumentResolvers = new HashSet<>();

    private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache =
            new HashMap<>(256);

    public void addResolver(HandlerMethodArgumentResolver handlerMethodArgumentResolver) {
        this.argumentResolvers.add(handlerMethodArgumentResolver);
    }

    public void addResolver(Set<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers) {
        this.argumentResolvers.addAll(handlerMethodArgumentResolvers);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return getArgumentResolver(parameter) != null;
    }

    @Override
    public Object resolveParameter(RootCommandArgument commandArgument, MethodParameter parameter) {
        return getArgumentResolver(parameter).resolveParameter(commandArgument, parameter);
    }

    private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
        HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
        if (result == null) {
            for (HandlerMethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
                if (methodArgumentResolver.supportsParameter(parameter)) {
                    result = methodArgumentResolver;
                    this.argumentResolverCache.put(parameter, result);
                    break;
                }
            }
        }
        return result;
    }
}