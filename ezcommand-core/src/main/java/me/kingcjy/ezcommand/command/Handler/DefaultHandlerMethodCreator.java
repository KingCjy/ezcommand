package me.kingcjy.ezcommand.command.Handler;

import me.kingcjy.ezcommand.annotations.CommandMapping;
import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultHandlerMethodCreator implements HandlerMethodCreator {

    private final CommandTypeDefinitionComposite commandTypeDefinitionComposite;

    public DefaultHandlerMethodCreator(CommandTypeDefinitionComposite commandTypeDefinitionComposite) {
        this.commandTypeDefinitionComposite = commandTypeDefinitionComposite;
    }

    @Override
    public Map<HandlerKey, InvocableHandlerMethod> createInvocableHandlerMethods(Object bean) {
        Map<HandlerKey, InvocableHandlerMethod> invocableHandlerMethods = new HashMap<>();
        Map<String, Method> commandsMap = findCommandsMap(bean.getClass());

        for (String command : commandsMap.keySet()) {
            invocableHandlerMethods.put(new HandlerKey(commandTypeDefinitionComposite, command), new InvocableHandlerMethod(bean, commandsMap.get(command)));
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
