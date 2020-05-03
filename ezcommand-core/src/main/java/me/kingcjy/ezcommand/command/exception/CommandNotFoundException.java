package me.kingcjy.ezcommand.command.exception;

public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException(String command) {
        super(command + " 는 존재하지 않는 명령어입니다.");
    }
}
