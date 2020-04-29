package me.kingcjy.ezcommand.beans.definition;

import java.util.Set;

public interface BeanDefinitionRegistry {
    void registerDefinition(BeanDefinition beanDefinition);
    Set<BeanDefinition> getBeanDefinitions();

    <T> T getOrCreateBean(Class<T> type);
}
