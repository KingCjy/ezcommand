package me.kingcjy.ezcommand.bean.factory;

import me.kingcjy.ezcommand.annotations.Inject;
import me.kingcjy.ezcommand.beans.definition.ClassBeanDefinition;
import me.kingcjy.ezcommand.beans.factory.DefaultBeanFactory;
import org.junit.Assert;
import org.junit.Test;

public class DefaultBeanFactoryTest {

    @Test
    public void 생성자_인젝션_테스트() {
        ClassBeanDefinition classBeanDefinition = new ClassBeanDefinition(MessageCommand2.class);
        ClassBeanDefinition classBeanDefinition1 = new ClassBeanDefinition(MessageService.class);

        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        beanFactory.registerDefinition(classBeanDefinition);
        beanFactory.registerDefinition(classBeanDefinition1);
        beanFactory.initialize();

        MessageCommand2 messageCommand = beanFactory.getBean(MessageCommand2.class);
        String message = messageCommand.sendMessage("message!!!");

        Assert.assertEquals(message, "message!!!");
    }

    @Test
    public void 필드_인젝션_테스트() {

        ClassBeanDefinition classBeanDefinition = new ClassBeanDefinition(MessageCommand.class);
        ClassBeanDefinition classBeanDefinition1 = new ClassBeanDefinition(MessageService.class);

        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        beanFactory.registerDefinition(classBeanDefinition);
        beanFactory.registerDefinition(classBeanDefinition1);
        beanFactory.initialize();

        MessageCommand messageCommand = beanFactory.getBean(MessageCommand.class);
        String message = messageCommand.sendMessage("message!!!");

        Assert.assertEquals(message, "message!!!");
    }


    public static class MessageCommand {

        @Inject
        private MessageService messageService;

        public String sendMessage(String message) {
            return messageService.sendMessage(message);
        }
    }

    public static class MessageCommand2 {

        private final MessageService messageService;

        public MessageCommand2(MessageService messageService) {
            this.messageService = messageService;
        }

        public String sendMessage(String message) {
            return messageService.sendMessage(message);
        }
    }

    public static class MessageService {

        public String sendMessage(String message) {
            return message;
        }
    }
}
