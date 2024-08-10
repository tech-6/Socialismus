package me.whereareiam.socialismus.api.output.command;

import java.util.Map;

public interface CommandService {
    void registerCommand(CommandBase command);

    void registerCommands();

    void registerTranslation(String key, String value);

    int getCommandCount();

    String getTranslation(String key);

    Map<String, String> getTranslations();
}
