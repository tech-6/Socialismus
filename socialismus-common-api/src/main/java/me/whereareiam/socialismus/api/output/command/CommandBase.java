package me.whereareiam.socialismus.api.output.command;

import lombok.Getter;
import me.whereareiam.socialismus.api.model.CommandEntity;

import java.util.Map;

@Getter
public abstract class CommandBase {

    protected final String commandName;

    protected CommandBase(String commandName) {
        this.commandName = commandName;
    }

    public abstract CommandEntity getCommandEntity();

    public Map<String, String> getTranslations() {
        CommandEntity commandEntity = getCommandEntity();

        return Map.of(
                "command." + commandEntity.getAliases().getFirst() + ".name", commandEntity.getUsage().replace("{alias}", String.join("|", commandEntity.getAliases())),
                "command." + commandEntity.getAliases().getFirst() + ".permission", commandEntity.getPermission(),
                "command." + commandEntity.getAliases().getFirst() + ".description", commandEntity.getDescription(),
                "command." + commandEntity.getAliases().getFirst() + ".usage", commandEntity.getUsage()
        );
    }
}