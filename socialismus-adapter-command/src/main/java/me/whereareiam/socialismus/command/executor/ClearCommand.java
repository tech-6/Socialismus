package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.CommandEntity;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.Map;

@Singleton
@Command("%command.main")
public class ClearCommand implements CommandBase {
    private final SerializationService serializer;
    private final Provider<Messages> messages;
    private final Provider<Map<String, CommandEntity>> commands;

    @Inject
    public ClearCommand(SerializationService serializer, Provider<Messages> messages, Provider<Map<String, CommandEntity>> commands) {
        this.serializer = serializer;
        this.messages = messages;
        this.commands = commands;
    }

    @Command("%command.clear")
    @CommandDescription("%description.clear")
    @Permission("%permission.clear")
    public void onCommand(DummyPlayer dummyPlayer, @Argument(value = "context") String context) {
        System.out.println("Deleting");
    }

    @Override
    public Map<String, String> getTranslations() {
        CommandEntity commandEntity = commands.get().get("clear");

        return Map.of(
                "command." + commandEntity.getAliases().getFirst() + ".name", commandEntity.getUsage().replace("{command}", String.join("|", commandEntity.getAliases())),
                "command." + commandEntity.getAliases().getFirst() + ".permission", commandEntity.getPermission(),
                "command." + commandEntity.getAliases().getFirst() + ".description", commandEntity.getDescription(),
                "command." + commandEntity.getAliases().getFirst() + ".usage", commandEntity.getUsage()
        );
    }
}