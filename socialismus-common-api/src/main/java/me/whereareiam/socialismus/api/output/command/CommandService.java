package me.whereareiam.socialismus.api.output.command;

public interface CommandService {
    void registerCommand(CommandBase command);

    void registerCommands();

    int getCommandCount();
}
