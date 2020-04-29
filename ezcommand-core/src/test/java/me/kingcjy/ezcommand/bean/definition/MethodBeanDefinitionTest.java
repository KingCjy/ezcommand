package me.kingcjy.ezcommand.bean.definition;

import me.kingcjy.ezcommand.annotations.Bean;
import me.kingcjy.ezcommand.beans.definition.ClassBeanDefinition;
import me.kingcjy.ezcommand.beans.definition.MethodBeanDefinition;
import me.kingcjy.ezcommand.beans.factory.DefaultBeanFactory;
import org.junit.Assert;
import org.junit.Test;

public class MethodBeanDefinitionTest {

    @Test
    public void Method_Bean_인스턴스_생성_테스트() throws Exception {
        ClassBeanDefinition classBeanDefinition = new ClassBeanDefinition(ConfigurationTest.class);
        MethodBeanDefinition methodBeanDefinition = new MethodBeanDefinition(ConfigurationTest.class.getDeclaredMethod("testClass"));

        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        beanFactory.registerDefinition(classBeanDefinition);
        beanFactory.registerDefinition(methodBeanDefinition);
        beanFactory.initialize();

        TestClass testClass = beanFactory.getBean(TestClass.class);
        Assert.assertEquals(testClass.getMessage(), "message!!!");

    }

    public static class ConfigurationTest {

        @Bean
        public TestClass testClass() {
            TestClass testClass = new TestClass("message!!!");
            return testClass;
        }
    }

    public static class TestClass {

        private String message;
        public TestClass(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
