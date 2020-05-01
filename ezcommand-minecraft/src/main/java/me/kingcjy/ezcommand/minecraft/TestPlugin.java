package me.kingcjy.ezcommand.minecraft;

import me.kingcjy.ezcommand.context.EzApplicationContext;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new EzCommand(this);
    }
}
