package me.kingcjy.ezcommand.minecraft;

import me.kingcjy.ezcommand.annotations.*;
import me.kingcjy.ezcommand.minecraft.annotations.Description;
import me.kingcjy.ezcommand.minecraft.annotations.GenerateHelpCommand;
import me.kingcjy.ezcommand.minecraft.annotations.Op;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Command
@GenerateHelpCommand
@CommandMapping("message")
public class MessageCommand {

    @Description("플레이어에게 메세지를 보냅니다.")
    @CommandMapping("one <Player:player> <message>")
    public void messageToPlayer(@PathVariable Player player, @PathVariable String message) {
        player.sendMessage(message);
        throw new RuntimeException("asdbkajsbdkasd");
    }

    @Op
    @Description("오피만 보여야한다")
    @CommandMapping("all <message>")
    public void messageToAllPlayer(@PathVariable String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
}