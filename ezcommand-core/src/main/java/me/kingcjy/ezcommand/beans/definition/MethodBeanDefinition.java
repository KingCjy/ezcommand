package me.kingcjy.ezcommand.beans.definition;

import me.kingcjy.ezcommand.EzCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MethodBeanDefinition implements BeanDefinition {

    private static final Logger logger = EzCommand.getLogger(MethodBeanDefinition.class);

    private Class<?> beanClass;
    private Method method;

    public MethodBeanDefinition(Method method) {
        this.beanClass = method.getReturnType();
        this.method = method;
    }

    @Override
    public Class<?> getType() {
        return this.beanClass;
    }

    @Override
    public Object createBeanInstance(BeanDefinitionRegistry beanDefinitionRegistry) {
        Object[] parameters = getParameters(beanDefinitionRegistry, method.getParameterTypes());
        Object instance = getInstanceBean(beanDefinitionRegistry, method.getDeclaringClass());

        try {
            Object result = method.invoke(instance, parameters);

            logger.log(Level.INFO, "bean typed {0} created", getType().getName());
            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
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
}
