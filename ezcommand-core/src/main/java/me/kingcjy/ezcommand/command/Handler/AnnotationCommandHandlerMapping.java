package me.kingcjy.ezcommand.command.Handler;

import me.kingcjy.ezcommand.annotations.Command;
import me.kingcjy.ezcommand.beans.factory.BeanFactory;
import me.kingcjy.ezcommand.utils.BeanUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationCommandHandlerMapping implements CommandHandlerMapping {

    private BeanFactory beanFactory;
    private HandlerMethodCreator handlerMethodCreator;
    private Map<HandlerKey, InvocableHandlerMethod> handlers = new HashMap<>();

    public AnnotationCommandHandlerMapping(BeanFactory beanFactory, HandlerMethodCreator handlerMethodCreator) {
        this.beanFactory = beanFactory;
        this.handlerMethodCreator = handlerMethodCreator;
        initialize();
    }

    @Override
    public void initialize() {
        Set<Object> annotatedClasses = BeanUtils.findAnnotatedBean(beanFactory, Command.class);

        for (Object annotatedClass : annotatedClasses) {
            handlers.putAll(handlerMethodCreator.createInvocableHandlerMethods(annotatedClass));
        }
    }

    @Override
    public InvocableHandlerMethod getHandler(String command) {
        HandlerKey handlerKey = handlers.keySet().stream()
                .filter(key -> key.matches(command))
                .findAny().get();

        return handlers.get(handlerKey);
    }

    @Override
    public Set<HandlerKey> getHandlerKeys() {
        return this.handlers.keySet();
    }

}
