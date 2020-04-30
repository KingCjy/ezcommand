package me.kingcjy.ezcommand;

import me.kingcjy.ezcommand.beans.definition.ClassBeanDefinition;
import me.kingcjy.ezcommand.context.EzApplicationContext;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class EzCommand {

    public static Logger getLogger(Class<?> targetClass) {

        return Logger.getLogger(targetClass.getName());
    }
}
