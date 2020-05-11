package me.kingcjy.ezcommand.config;

import me.kingcjy.ezcommand.annotations.Bean;
import me.kingcjy.ezcommand.annotations.Configuration;
import me.kingcjy.ezcommand.beans.factory.BeanFactory;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolverComposite;

@Configuration
public class EzConfiguration {

    private final BeanFactory beanFactory;

    public EzConfiguration(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Bean
    public HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite() {
        return new HandlerMethodArgumentResolverComposite();
    }

}
