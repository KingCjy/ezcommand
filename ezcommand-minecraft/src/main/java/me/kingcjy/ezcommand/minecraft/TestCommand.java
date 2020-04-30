package me.kingcjy.ezcommand.minecraft;

import me.kingcjy.ezcommand.annotations.Command;
import me.kingcjy.ezcommand.annotations.CommandMapping;
import me.kingcjy.ezcommand.minecraft.annotations.Description;
import me.kingcjy.ezcommand.minecraft.annotations.GenerateHelpCommand;
import me.kingcjy.ezcommand.command.CommandArgument;

@Command
@CommandMapping("test")
@GenerateHelpCommand
public class TestCommand {

    @Description("나가 뒤지세연")
    @CommandMapping("me")
    public void test(CommandArgument commandArgument) {
        commandArgument.getCommandSender().sendMessage("hi!");
    }
}
