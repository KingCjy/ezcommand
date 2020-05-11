package me.kingcjy.ezcommand.minecraft;

import me.kingcjy.ezcommand.annotations.CommandAdvice;
import me.kingcjy.ezcommand.exception.CommandAdviceBean;
import me.kingcjy.ezcommand.minecraft.command.executor.AnnocationCommandExecutor;
import me.kingcjy.ezcommand.command.handler.AnnotationCommandHandlerMapping;
import me.kingcjy.ezcommand.minecraft.command.registry.AnnotationCommandRegistry;
import me.kingcjy.ezcommand.context.EzApplicationContext;
import org.bukkit.plugin.java.JavaPlugin;

public class EzCommand extends EzApplicationContext {

    public EzCommand(JavaPlugin javaPlugin) {
        super(javaPlugin);
        this.serverName = javaPlugin.getDescription().getName();
        this.serverVersion = javaPlugin.getDescription().getVersion();
    }

    @Override
    public void registerCommand(AnnotationCommandHandlerMapping annotationCommandHandlerMapping) {

        CommandAdviceBean commandAdviceBean = this.beanFactory.getBean(CommandAdviceBean.class);

        AnnocationCommandExecutor annocationCommandExecutor = new AnnocationCommandExecutor(commandAdviceBean, annotationCommandHandlerMapping);
        AnnotationCommandRegistry annotationCommandRegistry = new AnnotationCommandRegistry((JavaPlugin) mainClass, annocationCommandExecutor, annotationCommandHandlerMapping);
        annotationCommandRegistry.registerCommands();
    }
}
