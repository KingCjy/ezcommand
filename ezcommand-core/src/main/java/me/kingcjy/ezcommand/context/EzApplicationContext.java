package me.kingcjy.ezcommand.context;

import resource.ResourceLoader;
import me.kingcjy.ezcommand.beans.factory.BeanFactory;
import me.kingcjy.ezcommand.beans.factory.DefaultBeanFactory;
import me.kingcjy.ezcommand.beans.scanner.AnnotationBeanDefinitionScanner;
import me.kingcjy.ezcommand.beans.scanner.BeanDefinitionScanner;
import me.kingcjy.ezcommand.beans.scanner.ClassPathBeanDefinitionScanner;
import me.kingcjy.ezcommand.command.handler.AnnotationCommandHandlerMapping;
import me.kingcjy.ezcommand.command.handler.HandlerMethodCreator;
import me.kingcjy.ezcommand.command.handler.HandlerMethodCreatorComposite;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinition;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolver;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolverComposite;
import me.kingcjy.ezcommand.utils.BeanUtils;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class EzApplicationContext implements ApplicationContext {

    protected String serverName;
    protected String serverVersion;
    protected LocalDateTime startDateTime;

    private BeanFactory beanFactory;

    protected Object mainClass;

    public EzApplicationContext(Object mainClass) {
        this.mainClass = mainClass;
        this.startDateTime = LocalDateTime.now();

        ResourceLoader.initialize(mainClass.getClass());

        String basePackage = mainClass.getClass().getPackage().getName();

        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();

        BeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner();
        classPathBeanDefinitionScanner.setBeanDefinitionRegistry(defaultBeanFactory);
        classPathBeanDefinitionScanner.scan(basePackage);

        BeanDefinitionScanner annotationBeanDefinitionScanner = new AnnotationBeanDefinitionScanner();
        annotationBeanDefinitionScanner.setBeanDefinitionRegistry(defaultBeanFactory);
        annotationBeanDefinitionScanner.scan(basePackage);
        this.beanFactory = defaultBeanFactory.initialize();

        Set<CommandTypeDefinition> commandTypeDefinitions = BeanUtils.findSubTypeOf(beanFactory, CommandTypeDefinition.class);
        CommandTypeDefinitionComposite commandTypeDefinitionComposite = this.beanFactory.getBean(CommandTypeDefinitionComposite.class);
        commandTypeDefinitionComposite.addCommandTypeDefinitions(commandTypeDefinitions);

        Set<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers = BeanUtils.findSubTypeOf(beanFactory, HandlerMethodArgumentResolver.class);
        handlerMethodArgumentResolvers = handlerMethodArgumentResolvers.stream().filter(t -> !t.getClass().equals(HandlerMethodArgumentResolverComposite.class)).collect(Collectors.toSet());

        HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite = this.beanFactory.getBean(HandlerMethodArgumentResolverComposite.class);
        handlerMethodArgumentResolverComposite.addResolver(handlerMethodArgumentResolvers);

        HandlerMethodCreator handlerMethodCreator = new HandlerMethodCreatorComposite(beanFactory);

        AnnotationCommandHandlerMapping annotationCommandHandlerMapping = new AnnotationCommandHandlerMapping(beanFactory, handlerMethodCreator);

        registerCommand(annotationCommandHandlerMapping);
    }

    public abstract void registerCommand(AnnotationCommandHandlerMapping annotationCommandHandlerMapping);

    @Override
    public String getServerName() {
        return "Ezframework";
    }

    @Override
    public String getServerVersion() {
        return "0.0.1";
    }

    @Override
    public LocalDateTime getStartDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public Object getBean(String name) {
        return this.beanFactory.getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> type) {
        return this.beanFactory.getBean(type);
    }

    @Override
    public <T> T getBean(String name, Class<T> type) {
        return this.beanFactory.getBean(name, type);
    }

    @Override
    public Object[] getBeans() {
        return this.beanFactory.getBeans();
    }
}
