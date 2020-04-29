package me.kingcjy.ezcommand.beans.definition;

public interface BeanDefinition {
    Class<?> getType();
    Object createBeanInstance(BeanDefinitionRegistry beanDefinitionRegistry);
}
