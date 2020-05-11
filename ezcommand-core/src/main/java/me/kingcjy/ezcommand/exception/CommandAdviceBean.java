package me.kingcjy.ezcommand.exception;

import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.annotations.ExceptionHandler;
import me.kingcjy.ezcommand.command.RootCommandArgument;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandAdviceBean {

    private Object bean;
    private Map<Class<? extends RuntimeException>, Method> exceptionHandlers;

    public void setControllerAdvice(Object bean) {
        this.bean = bean;
        this.exceptionHandlers = findExceptionHandlers(bean);
    }

    private Map<Class<? extends RuntimeException>, Method> findExceptionHandlers(Object bean) {
        Map<Class<? extends RuntimeException>, Method> exceptionHandlers = new HashMap<>();
        Class<?> targetClass = bean.getClass();

        for (Method method : targetClass.getDeclaredMethods()) {
            ExceptionHandler exceptionHandler = method.getDeclaredAnnotation(ExceptionHandler.class);
            if(exceptionHandler == null) {
                continue;
            }

            exceptionHandlers.put(exceptionHandler.value(), method);
        }

        return exceptionHandlers;
    }

    public boolean supportException(Class<? extends RuntimeException> exception) {

        if(exceptionHandlers.keySet().contains(exception) == true) {
            return true;
        }

        for (Class<? extends RuntimeException> targetClass : exceptionHandlers.keySet()) {
            if(targetClass.isAssignableFrom(exception)) {
                return true;
            }
        }
        return false;
    }

    public <T extends RuntimeException> void invokeExceptionHandlerMethod(RootCommandArgument commandArgument, T exception) {
        try {
            Method exceptionHandler = getExceptionHandler(exception.getClass());

            if(exceptionHandler.getParameterCount() == 2) {
                exceptionHandler.invoke(bean, exception, commandArgument);
            } else {
                exceptionHandler.invoke(bean, exception);
            }
        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Method getExceptionHandler(Class<? extends RuntimeException> exception) {
        if(exceptionHandlers.keySet().contains(exception)) {
            return exceptionHandlers.get(exception);
        }

        for (Class<? extends RuntimeException> targetClass : exceptionHandlers.keySet()) {
            if(targetClass.isAssignableFrom(exception)) {
                return exceptionHandlers.get(targetClass);
            }
        }
        return null;
    }
}

