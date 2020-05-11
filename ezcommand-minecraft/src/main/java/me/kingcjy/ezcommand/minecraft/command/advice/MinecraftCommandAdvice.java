package me.kingcjy.ezcommand.minecraft.command.advice;

import me.kingcjy.ezcommand.EzLogger;
import me.kingcjy.ezcommand.annotations.CommandAdvice;
import me.kingcjy.ezcommand.annotations.ExceptionHandler;
import me.kingcjy.ezcommand.annotations.Order;
import me.kingcjy.ezcommand.command.RootCommandArgument;
import me.kingcjy.ezcommand.exception.DefaultCommandAdvice;
import me.kingcjy.ezcommand.minecraft.command.CommandArgument;

import java.util.logging.Level;
import java.util.logging.Logger;

@Order(Order.MIDDLE)
@CommandAdvice
public class MinecraftCommandAdvice {

    private final Logger logger = EzLogger.getLogger(DefaultCommandAdvice.class);

    @ExceptionHandler(RuntimeException.class)
    public void HandleRuntimeException(RuntimeException e, CommandArgument commandArgument) {
        logger.log(Level.INFO, "KingCjy!@#!@#!@#");
        logger.log(Level.INFO, e.getMessage());
        logger.log(Level.INFO, commandArgument.toString());
    }
}
