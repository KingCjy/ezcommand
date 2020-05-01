package me.kingcjy.ezcommand.minecraft.config;

import me.kingcjy.ezcommand.annotations.Bean;
import me.kingcjy.ezcommand.annotations.Configuration;

import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
import me.kingcjy.ezcommand.minecraft.command.CommandArgument;

@Configuration
public class MinecraftConfig {
    @Bean
    public CommandTypeDefinitionComposite commandTypeDefinitionComposite() {
        CommandTypeDefinitionComposite commandTypeDefinitionComposite = new CommandTypeDefinitionComposite<CommandArgument>();
        return commandTypeDefinitionComposite;
    }
}
