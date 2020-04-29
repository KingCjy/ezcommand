package me.kingcjy.ezcommand.config;

import me.kingcjy.ezcommand.annotations.Bean;
import me.kingcjy.ezcommand.annotations.Configuration;
import me.kingcjy.ezcommand.beans.factory.BeanFactory;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinition;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolver;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolverComposite;
import me.kingcjy.ezcommand.utils.BeanUtils;

import java.util.Set;

@Configuration
public class EzConfiguration {

    private final BeanFactory beanFactory;

    public EzConfiguration(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Bean
    public CommandTypeDefinitionComposite commandTypeDefinitionComposite() {
        CommandTypeDefinitionComposite commandTypeDefinitionComposite = new CommandTypeDefinitionComposite();
        return commandTypeDefinitionComposite;
    }
    @Bean
    public HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite() {
        return new HandlerMethodArgumentResolverComposite();
    }
}
