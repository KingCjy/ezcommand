package me.kingcjy.ezcommand.beans.scanner;

import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.beans.definition.BeanDefinitionRegistry;
import me.kingcjy.ezcommand.beans.definition.ClassBeanDefinition;
import me.kingcjy.ezcommand.utils.EzReflectionUtils;

import java.util.Set;

public class ClassPathBeanDefinitionScanner implements BeanDefinitionScanner {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    @Override
    public void setBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void scan(String basePackage) {
        Set<Class> classes = EzReflectionUtils.findAnnotatedClasses(basePackage, Component.class);

        for (Class targetClass : classes) {
            Component component = (Component) targetClass.getAnnotation(Component.class);

            if(component != null && component.value().isEmpty() == false) {
                beanDefinitionRegistry.registerDefinition(new ClassBeanDefinition(targetClass, component.value()));
            } else {
                beanDefinitionRegistry.registerDefinition(new ClassBeanDefinition(targetClass));
            }
        }
    }
}
