package me.kingcjy.ezcommand.beans.scanner;

import me.kingcjy.ezcommand.beans.definition.BeanDefinitionRegistry;

public interface BeanDefinitionScanner {
    void setBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry);
    void scan(String basePackage);
}
