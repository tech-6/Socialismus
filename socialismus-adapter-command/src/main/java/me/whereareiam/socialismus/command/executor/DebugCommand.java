package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.PluginType;
import me.whereareiam.socialismus.api.input.PluginInteractor;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.CommandEntity;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.Map;

@Singleton
@Command("%command.main")
public class DebugCommand implements CommandBase {
    private final SerializationService serializer;
    private final Provider<Messages> messages;
    private final Provider<Map<String, CommandEntity>> commands;

    // Data provider
    private final PluginInteractor pluginInteractor;
    private final PlatformInteractor platformInteractor;

    @Inject
    public DebugCommand(SerializationService serializer, Provider<Messages> messages, Provider<Map<String, CommandEntity>> commands, PluginInteractor pluginInteractor, PlatformInteractor platformInteractor) {
        this.serializer = serializer;
        this.messages = messages;
        this.commands = commands;
        this.pluginInteractor = pluginInteractor;
        this.platformInteractor = platformInteractor;
    }

    @Command("%command.debug")
    @CommandDescription("%description.debug")
    @Permission("%permission.debug")
    public void onCommand(DummyPlayer dummyPlayer) {
        String message = String.join("\n", messages.get().getCommands().getDebugCommand().getFormat());

        message = message.replace("{serverVersion}", platformInteractor.getServerVersion().name())
                .replace("{pluginVersion}", pluginInteractor.getPluginVersion())
                .replace("{serverPlatform}", PlatformType.getType().name())
                .replace("{pluginPlatform}", PluginType.getType().name())
                .replace("{javaVersion}", System.getProperty("java.version"))
                .replace("{os}", System.getProperty("os.name"));

        dummyPlayer.getAudience().sendMessage(serializer.format(dummyPlayer, message));
    }

    @Override
    public Map<String, String> getTranslations() {
        CommandEntity commandEntity = commands.get().get("debug");

        return Map.of(
                "commandEntity." + commandEntity.getAliases().getFirst() + ".name", commandEntity.getUsage().replace("{commandEntity}", String.join("|", commandEntity.getAliases())),
                "commandEntity." + commandEntity.getAliases().getFirst() + ".permission", commandEntity.getPermission(),
                "commandEntity." + commandEntity.getAliases().getFirst() + ".description", commandEntity.getDescription(),
                "commandEntity." + commandEntity.getAliases().getFirst() + ".usage", commandEntity.getUsage()
        );
    }
}