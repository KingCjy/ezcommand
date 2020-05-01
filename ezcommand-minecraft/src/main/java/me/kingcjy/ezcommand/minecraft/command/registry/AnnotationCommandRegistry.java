package me.kingcjy.ezcommand.minecraft.command.registry;

import me.kingcjy.ezcommand.command.handler.CommandHandlerMapping;
import me.kingcjy.ezcommand.command.handler.HandlerKey;
import me.kingcjy.ezcommand.command.registry.CommandRegistry;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnnotationCommandRegistry implements CommandRegistry {

    private CommandHandlerMapping commandHandlerMapping;
    private CommandExecutor commandExecutor;
    private JavaPlugin javaPlugin;

    public AnnotationCommandRegistry(JavaPlugin javaPlugin, CommandExecutor commandExecutor, CommandHandlerMapping commandHandlerMapping) {
        this.javaPlugin = javaPlugin;
        this.commandExecutor = commandExecutor;
        this.commandHandlerMapping = commandHandlerMapping;
    }

    @Override
    public void setHandlerMapping(CommandHandlerMapping commandHandlerMapping) {
        this.commandHandlerMapping = commandHandlerMapping;
    }

    @Override
    public void registerCommands() {
        CommandMap commandMap = getCommandMap();
        List<PluginCommand> pluginCommands = getPluginCommands();

        for (PluginCommand pluginCommand : pluginCommands) {
            commandMap.register("ezcommand", pluginCommand);
        }

        syncCommands();
    }

    private void syncCommands() {
        try {
            Method method = Bukkit.getServer().getClass().getDeclaredMethod("syncCommands");
            method.setAccessible(true);
            method.invoke(Bukkit.getServer());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {

        }
    }

    private List<PluginCommand> getPluginCommands() {
        Set<String> commands = findCommands();
        List<PluginCommand> pluginCommands = new ArrayList<>();

        for (String command : commands) {
            pluginCommands.add(createPluginCommand(command));
        }

        return pluginCommands;
    }

    private PluginCommand createPluginCommand(String command) {
        try {
            Constructor constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            PluginCommand pluginCommand = (PluginCommand) constructor.newInstance(command, javaPlugin);
            pluginCommand.setExecutor(commandExecutor);

            return pluginCommand;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Set<String> findCommands() {
        Set<String> commands = new HashSet<>();
        for (HandlerKey handlerKey : this.commandHandlerMapping.getHandlerKeys()) {
            String command = handlerKey.getCommand().split(" ")[0];
            commands.add(command);
        }
        return commands;
    }

    private CommandMap getCommandMap() {
        try {
            PluginManager pluginManager = Bukkit.getServer().getPluginManager();
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);

            CommandMap commandMap = (CommandMap) field.get(pluginManager);

            return commandMap;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
