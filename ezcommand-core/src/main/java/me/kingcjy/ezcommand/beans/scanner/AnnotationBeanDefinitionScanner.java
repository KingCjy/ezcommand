package me.kingcjy.ezcommand.beans.scanner;

import me.kingcjy.ezcommand.annotations.Bean;
import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.beans.definition.BeanDefinitionRegistry;
import me.kingcjy.ezcommand.beans.definition.MethodBeanDefinition;
import me.kingcjy.ezcommand.utils.EzReflectionUtils;

import java.lang.reflect.Method;
import java.util.Set;

public class AnnotationBeanDefinitionScanner implements BeanDefinitionScanner {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    @Override
    public void setBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void scan(String basePackage) {
        Set<Class> classes = EzReflectionUtils.findAnnotatedClasses(basePackage, Component.class);

        for (Class<?> targetClass : classes) {
            findBeanRegisterMethods(targetClass);
        }
    }

    private void findBeanRegisterMethods(Class<?> targetClass) {
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            registerAnnotationBeanDefinition(method);
        }
    }

    private void registerAnnotationBeanDefinition(Method method) {
        if(method.isAnnotationPresent(Bean.class)) {
            beanDefinitionRegistry.registerDefinition(new MethodBeanDefinition(method));
        }
    }
}
