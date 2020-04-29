package me.kingcjy.ezcommand.utils;

import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.beans.factory.BeanFactory;
import me.kingcjy.ezcommand.beans.factory.DefaultBeanFactory;
import me.kingcjy.ezcommand.beans.scanner.AnnotationBeanDefinitionScanner;
import me.kingcjy.ezcommand.beans.scanner.BeanDefinitionScanner;
import me.kingcjy.ezcommand.beans.scanner.ClassPathBeanDefinitionScanner;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

@Component
public class BeanUtilsTest {

    private BeanFactory beanFactory;

    @Before
    public void before() {
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();

        BeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner();
        classPathBeanDefinitionScanner.setBeanDefinitionRegistry(defaultBeanFactory);
        classPathBeanDefinitionScanner.scan("");

        BeanDefinitionScanner annotationBeanDefinitionScanner = new AnnotationBeanDefinitionScanner();
        annotationBeanDefinitionScanner.setBeanDefinitionRegistry(defaultBeanFactory);
        annotationBeanDefinitionScanner.scan("");
        defaultBeanFactory.initialize();

        this.beanFactory = defaultBeanFactory;
    }

    @Test
    public void findSubTypeOfTest() throws Exception {
        Set<CommandTypeDefinition> commandTypeDefinitions = BeanUtils.findSubTypeOf(beanFactory, CommandTypeDefinition.class);

        System.out.println(commandTypeDefinitions);
    }

    @Test
    public void findAnnotatedBeanTest() throws Exception {
        Set<Object> annotatedBean = BeanUtils.findAnnotatedBean(beanFactory, Component.class);

        Assert.assertTrue(annotatedBean.stream().filter(o -> BeanUtilsTest.class.equals(o.getClass())).findAny().isPresent());
    }
}
