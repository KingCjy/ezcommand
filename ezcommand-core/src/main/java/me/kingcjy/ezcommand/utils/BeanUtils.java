package me.kingcjy.ezcommand.utils;

import me.kingcjy.ezcommand.beans.factory.BeanFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class BeanUtils {

    public static <T> Set<T> findSubTypeOf(BeanFactory beanFactory, Class<T> targetType) {
        Set<T> result = new HashSet<>();
        for (Object bean : beanFactory.getBeans()) {
            if(targetType.isAssignableFrom(bean.getClass())) {
                result.add((T) bean);
            }
        }
        return result;
    }

    public static <T> Set<T> findTypeOf(BeanFactory beanFactory, Class<T> targetType) {
        Set<T> result = new HashSet<>();
        for (Object bean : beanFactory.getBeans()) {
            if(targetType.equals(bean.getClass())) {
                result.add((T) bean);
            }
        }
        return result;
    }

    public static Set<Object> findAnnotatedBean(BeanFactory beanFactory, Class<? extends Annotation> targetAnnotation) {
        Set<Object> result = new HashSet<>();

        for (Object bean : beanFactory.getBeans()) {
            if(bean.getClass().getAnnotation(targetAnnotation) != null) {
                result.add(bean);
            }
        }
        return result;
    }
}
