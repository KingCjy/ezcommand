package me.kingcjy.ezcommand.minecraft.command.handler;

import resource.ResourceLoader;
import me.kingcjy.ezcommand.EzLogger;
import me.kingcjy.ezcommand.annotations.CommandMapping;
import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.minecraft.annotations.GenerateHelpCommand;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
import me.kingcjy.ezcommand.command.handler.HandlerKey;
import me.kingcjy.ezcommand.command.handler.HandlerMethodCreator;
import me.kingcjy.ezcommand.command.handler.InvocableHandlerMethod;
import me.kingcjy.ezcommand.command.handler.support.DefaultHandlerMethodCreator;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolverComposite;
import me.kingcjy.ezcommand.utils.TagParser;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HelpHandlerMethodCreator implements HandlerMethodCreator {

    private Logger logger = EzLogger.getLogger(DefaultHandlerMethodCreator.class);

    private final HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite;
    private final CommandTypeDefinitionComposite commandTypeDefinitionComposite;

    public HelpHandlerMethodCreator(HandlerMethodArgumentResolverComposite handlerMethodArgumentResolverComposite, CommandTypeDefinitionComposite commandTypeDefinitionComposite) {
        this.handlerMethodArgumentResolverComposite = handlerMethodArgumentResolverComposite;
        this.commandTypeDefinitionComposite = commandTypeDefinitionComposite;
    }

    @Override
    public Map<HandlerKey, InvocableHandlerMethod> createInvocableHandlerMethods(Object bean) {

        CommandMapping commandMapping = bean.getClass().getDeclaredAnnotation(CommandMapping.class);

        if(bean.getClass().getDeclaredAnnotation(GenerateHelpCommand.class) == null) {
            return new HashMap<>();
        }

        if(commandMapping == null) {
            return new HashMap<>();
        }

        String command = commandMapping.value() + " help";

        TagParser tagParser = new TagParser(ResourceLoader.getInstance().read(ResourceLoader.HELP_FORMAT));

        HandlerKey handlerKey = new HandlerKey(commandTypeDefinitionComposite, command);
        InvocableHandlerMethod invocableHandlerMethod = new HelpInvocableHandlerMethod(tagParser, bean);
        Map<HandlerKey, InvocableHandlerMethod> invocableHandlerMethods = new HashMap<>();
        invocableHandlerMethods.put(handlerKey, invocableHandlerMethod);

        logger.log(Level.INFO, "[EzCommand] command /{0} is registered", command);

        return invocableHandlerMethods;
    }
}
