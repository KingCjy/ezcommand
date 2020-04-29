package me.kingcjy.ezcommand.bean.definition;

import me.kingcjy.ezcommand.beans.definition.ClassBeanDefinition;
import me.kingcjy.ezcommand.beans.factory.DefaultBeanFactory;
import org.junit.Assert;
import org.junit.Test;

public class ClassBeanDefinitionTest {

    @Test
    public void Bean_인스턴스_생성_테스트() {
        ClassBeanDefinition classBeanDefinition = new ClassBeanDefinition(TestBean.class);

        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        beanFactory.registerDefinition(classBeanDefinition);
        beanFactory.initialize();

        TestBean testBean = beanFactory.getBean(TestBean.class.getName(), TestBean.class);
        Assert.assertEquals(testBean.getString(), "test");
    }

    public static class TestBean {
        public String getString() {
            return "test";
        }
    }
}
