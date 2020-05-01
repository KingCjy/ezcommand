package me.kingcjy.ezcommand.command.handler.support;

import me.kingcjy.ezcommand.EzLogger;
import me.kingcjy.ezcommand.annotations.CommandMapping;
import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.command.handler.HandlerKey;
import me.kingcjy.ezcommand.command.handler.HandlerMethodCreator;
import me.kingcjy.ezcommand.command.handler.InvocableHandlerMethod;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolverComposite;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultHandlerMethodCreator implements HandlerMethodCreator {

    private Logger logger = EzLogger.getLogger(DefaultHandlerMethodCreator.class);

    private final HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite;
    private final CommandTypeDefinitionComposite commandTypeDefinitionComposite;

    public DefaultHandlerMethodCreator(HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite, CommandTypeDefinitionComposite commandTypeDefinitionComposite) {
        this.handlerMethodArgumentResolverComposite = handlerMethodArgumentResolverComposite;
        this.commandTypeDefinitionComposite = commandTypeDefinitionComposite;
    }

    @Override
    public Map<HandlerKey, InvocableHandlerMethod> createInvocableHandlerMethods(Object bean) {
        Map<HandlerKey, InvocableHandlerMethod> invocableHandlerMethods = new HashMap<>();
        Map<String, Method> commandsMap = findCommandsMap(bean.getClass());

        for (String command : commandsMap.keySet()) {
            InvocableHandlerMethod invocableHandlerMethod = new InvocableHandlerMethod(bean, commandsMap.get(command));
            invocableHandlerMethod.setHandlerMethodArgumentResolverComposite(handlerMethodArgumentResolverComposite);
            invocableHandlerMethods.put(new HandlerKey(commandTypeDefinitionComposite, command), invocableHandlerMethod);

            logger.log(Level.INFO, "[EzCommand] command /{0} is registered", command);
        }

        return invocableHandlerMethods;
    }

    public Map<String, Method> findCommandsMap(Class<?> targetClass) {
        Map<String, Method> commandsMap = new HashMap<>();
        for (Method method : targetClass.getDeclaredMethods()) {
            CommandMapping commandMapping = method.getDeclaredAnnotation(CommandMapping.class);

            if(commandMapping == null) {
                continue;
            }

            commandsMap.put(getCommand(method), method);
        }
        return commandsMap;
    }

    public String getCommand(Method method) {
        CommandMapping commandMapping = method.getDeclaringClass().getDeclaredAnnotation(CommandMapping.class);

        if(commandMapping == null) {
            return method.getDeclaredAnnotation(CommandMapping.class).value();
        }

        return commandMapping.value() + " " + method.getDeclaredAnnotation(CommandMapping.class).value();
    }
}
