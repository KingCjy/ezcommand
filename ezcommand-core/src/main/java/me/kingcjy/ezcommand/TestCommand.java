package me.kingcjy.ezcommand;

import me.kingcjy.ezcommand.annotations.Command;
import me.kingcjy.ezcommand.annotations.CommandMapping;
import me.kingcjy.ezcommand.annotations.PathVariable;
import org.bukkit.entity.Player;

@Command
@CommandMapping("message")
public class TestCommand {

    @CommandMapping("<Player:player> <message>")
    public void messageToPlayer(@PathVariable Player player, @PathVariable String message) {
        player.sendMessage(message);
    }
}
