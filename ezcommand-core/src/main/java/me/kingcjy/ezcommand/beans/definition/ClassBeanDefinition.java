package me.kingcjy.ezcommand.beans.definition;

import me.kingcjy.ezcommand.EzCommand;
import me.kingcjy.ezcommand.annotations.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassBeanDefinition implements BeanDefinition {

    private final static Logger logger = EzCommand.getLogger(ClassBeanDefinition.class);

    private Class<?> beanClass;
    private String name;

    public ClassBeanDefinition(Class<?> beanClass) {
        this(beanClass, beanClass.getName());
    }

    public ClassBeanDefinition(Class<?> beanClass, String name) {
        this.beanClass = beanClass;
        this.name = name;
    }

    @Override
    public Class<?> getType() {
        return beanClass;
    }

    @Override
    public Object createBeanInstance(BeanDefinitionRegistry beanDefinitionRegistry)  {
        try {
            Constructor constructor = getConstructor();
            Object[] parameters = getParameters(beanDefinitionRegistry, constructor.getParameterTypes());

            Object instance = constructor.newInstance(parameters);
            injectFields(beanDefinitionRegistry, instance);
            logger.log(Level.INFO, "[EzCommand] bean typed {0} created", getType().getName());
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void injectFields(BeanDefinitionRegistry beanDefinitionRegistry, Object instance) {
        for (Field field : beanClass.getDeclaredFields()) {
            injectField(beanDefinitionRegistry, instance, field);
        }
    }

    private void injectField(BeanDefinitionRegistry beanDefinitionRegistry, Object instance, Field field) {
        if(field.getAnnotation(Inject.class) == null) {
            return;
        }

        Object fieldTypeInstance = getInstanceBean(beanDefinitionRegistry, field.getType());

        field.setAccessible(true);
        try {
            field.set(instance, fieldTypeInstance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object[] getParameters(BeanDefinitionRegistry beanDefinitionRegistry, Class<?>[] typeParameters) {
        List<Object> instances = new ArrayList<>();
        for (Class<?> typeParameter : typeParameters) {
            Object instance = getInstanceBean(beanDefinitionRegistry, typeParameter);
            instances.add(instance);
        }

        return instances.toArray();
    }

    private Object getInstanceBean(BeanDefinitionRegistry beanDefinitionRegistry, Class<?> type) {
        return beanDefinitionRegistry.getOrCreateBean(type);
    }

    private Constructor getConstructor() throws NoSuchMethodException {
        Constructor[] constructors = beanClass.getConstructors();
        if(constructors.length == 0) {
            return beanClass.getConstructor();
        }

        return this.beanClass.getConstructors()[0];
    }
}
