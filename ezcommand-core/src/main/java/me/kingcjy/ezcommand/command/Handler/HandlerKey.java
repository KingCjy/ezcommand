package me.kingcjy.ezcommand.command.Handler;

import me.kingcjy.ezcommand.command.CommandParser;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;

import java.util.Objects;

public class HandlerKey {

    private String command;
    private CommandParser commandParser;

    HandlerKey(CommandTypeDefinitionComposite commandTypeDefinitionComposite, String command) {
        this.command = command;
        this.commandParser = new CommandParser(commandTypeDefinitionComposite, command);
    }

    public String getCommand() {
        return command;
    }

    public boolean matches(String command) {
        return this.commandParser.matches(command);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerKey that = (HandlerKey) o;
        return Objects.equals(command, that.command) &&
                Objects.equals(commandParser, that.commandParser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, commandParser);
    }

    @Override
    public String toString() {
        return "HandlerKey{" +
                "command='" + command + '\'' +
                ", commandParser=" + commandParser +
                '}';
    }
}
