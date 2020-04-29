package me.kingcjy.ezcommand.command.Handler;

import me.kingcjy.ezcommand.beans.factory.BeanFactory;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolverComposite;
import me.kingcjy.ezcommand.utils.BeanUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HandlerMethodCreatorComposite implements HandlerMethodCreator {

    private HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite;
    private BeanFactory beanFactory;

    private Set<HandlerMethodCreator> handlerMethodCreators = new HashSet<>();

    public HandlerMethodCreatorComposite(BeanFactory beanFactory, HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite) {
        this.beanFactory = beanFactory;
        this.handlerMethodArgumentResolverComposite = handlerMethodArgumentResolverComposite;

        initializeHandlerMethods();
    }

    public Map<HandlerKey, InvocableHandlerMethod> createInvocableHandlerMethods(Object bean) {
        Map<HandlerKey, InvocableHandlerMethod> invocableHandlerMethods = new HashMap();

        for (HandlerMethodCreator handlerMethodCreator : this.handlerMethodCreators) {
            invocableHandlerMethods.putAll(handlerMethodCreator.createInvocableHandlerMethods(bean));
        }

        return invocableHandlerMethods;
    }

    private void initializeHandlerMethods() {
        Set<HandlerMethodCreator> handlerMethodCreators = BeanUtils.findSubTypeOf(beanFactory, HandlerMethodCreator.class);
        this.handlerMethodCreators = handlerMethodCreators;
    }
}
