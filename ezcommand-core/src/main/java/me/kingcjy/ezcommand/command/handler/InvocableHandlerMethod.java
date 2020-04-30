package me.kingcjy.ezcommand.command.handler;

import me.kingcjy.ezcommand.command.CommandArgument;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolverComposite;
import me.kingcjy.ezcommand.executor.method.MethodParameter;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvocableHandlerMethod extends HandlerMethod {

    private static Logger logger = Bukkit.getLogger();

    private HandlerMethodArgumentResolverComposite resolvers = new HandlerMethodArgumentResolverComposite();

    public InvocableHandlerMethod() {

    }

    public InvocableHandlerMethod(Object instance, Method method) {
        super(instance, method);
    }

    public void setHandlerMethodArgumentResolverComposite(HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite) {
        this.resolvers = handlerMethodArgumentResolverComposite;
    }

    public Object invoke(CommandArgument commandArgument, Object... providedArguments) {
        Object[] args = getMethodArgumentValues(commandArgument, providedArguments);
        try {
            return getMethod().invoke(getInstance(), args);
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, "[EzCommand] " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            logger.log(Level.SEVERE, "[EzCommand] " + targetException.getMessage(), targetException);
            throw new RuntimeException(targetException);
        }
    }

    private Object[] getMethodArgumentValues(CommandArgument commandArgument, Object...providedArgs) {
        MethodParameter[] parameters = getMethodParameters();
        Object[] args = new Object[parameters.length];

        for(int i = 0; i < parameters.length; i++) {
            MethodParameter parameter = parameters[i];

            args[i] = findProvidedArgument(parameter, providedArgs);

            if(args[i] != null) {
                continue;
            }

            if(this.resolvers.supportsParameter(parameter)) {
                args[i] = this.resolvers.resolveParameter(commandArgument, parameter);
            }
        }

        return args;
    }

    private static Object findProvidedArgument(MethodParameter parameter, Object...providedArgs) {
        if(providedArgs == null) {
            return null;
        }

        for (Object providedArg : providedArgs) {
            if(parameter.getParameterType().isInstance(providedArg)) {
                return providedArg;
            }
        }

        return null;
    }
}
