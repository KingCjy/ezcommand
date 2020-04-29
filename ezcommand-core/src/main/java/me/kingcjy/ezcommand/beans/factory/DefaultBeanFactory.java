package me.kingcjy.ezcommand.beans.factory;

import me.kingcjy.ezcommand.beans.definition.BeanDefinition;
import me.kingcjy.ezcommand.beans.definition.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, BeanFactoryInitializer {

    private Set<BeanDefinition> beanDefinitions = new HashSet<>();
    private Map<String, Object> beans = new HashMap<>();

    public DefaultBeanFactory() {
        this.beans.put(BeanFactory.class.getName(), this);
    }

    @Override
    public void registerDefinition(BeanDefinition beanDefinition) {
        beanDefinitions.add(beanDefinition);
    }

    @Override
    public Set<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    @Override
    public <T> T getOrCreateBean(Class<T> type) {
        T bean = getBean(type);

        if(bean == null) {
            BeanDefinition beanDefinition = findBeanDefinitionByType(type);
            T instance = (T) beanDefinition.createBeanInstance(this);
            beans.put(type.getName(), instance);
            return instance;
        }

        return bean;
    }

    @Override
    public Object getBean(String name) {
        return beans.get(name);
    }

    @Override
    public <T> T getBean(Class<T> type) {
        return (T) getBean(type.getName());
    }

    @Override
    public <T> T getBean(String name, Class<T> type) {
        return (T) getBean(type.getName());
    }

    @Override
    public Object[] getBeans() {
        return beans.values().toArray();
    }

    @Override
    public BeanFactory initialize() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Object beanInstance = beanDefinition.createBeanInstance(this);
            beans.put(beanDefinition.getType().getName(), beanInstance);
        }

        return this;
    }

    private BeanDefinition findBeanDefinitionByType(Class<?> type) {
        return beanDefinitions.stream()
                .filter(beanDefinition -> beanDefinition.getType().equals(type))
                .findAny()
                .orElseThrow(() -> new RuntimeException("no Bean named " + type.getName() + " is defined"));
    }
}
