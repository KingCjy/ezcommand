package me.kingcjy.ezcommand.minecraft;

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
        AnnocationCommandExecutor annocationCommandExecutor = new AnnocationCommandExecutor(annotationCommandHandlerMapping);
        AnnotationCommandRegistry annotationCommandRegistry = new AnnotationCommandRegistry((JavaPlugin) mainClass, annocationCommandExecutor, annotationCommandHandlerMapping);
        annotationCommandRegistry.registerCommands();
    }
}
