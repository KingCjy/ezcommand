package me.kingcjy.ezcommand.utils;

import org.reflections.Reflections;

import javax.management.ReflectionException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EzReflectionUtils {

    private static Map<String, Reflections> reflectionsCache = new HashMap<>();

    private static Reflections createReflections(String basePackage) {
        if(reflectionsCache.get(basePackage) == null) {
            Reflections reflections = new Reflections(basePackage, "me.kingcjy.ezcommand");
            reflectionsCache.put(basePackage, reflections);
            return reflections;
        }

        return reflectionsCache.get(basePackage);
    }
    public static Set<Class> findAnnotatedClasses(String basePackage, Class<? extends Annotation>... annotations) {
        Reflections reflections = createReflections(basePackage);
        return getTypeAnnotatedClass(reflections, annotations);
    }

    public static Set<Class> findAssignableClass(String baseClass, Class targetInterface) {
        Reflections reflections = createReflections(baseClass);
        Set<Class> classes = reflections.getSubTypesOf(targetInterface);

        return classes;
    }

    private static Set<Class> getTypeAnnotatedClass(Reflections reflections, Class<? extends Annotation>... annotations) {
        Set<Class> result = new HashSet<>();

        for (Class<? extends Annotation> annotation : annotations) {
            Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(annotation);

            for (Class<?> annotatedClass : annotatedClasses) {
                if(annotatedClass.isAnnotation() == false && annotatedClass.isInterface() == false) {
                    result.add(annotatedClass);
                }
            }
        }

        return result;
    }
}
