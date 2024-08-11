package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.PluginType;
import me.whereareiam.socialismus.api.input.PluginInteractor;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.config.Commands;
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
    private final Provider<Commands> commands;

    // Data provider
    private final PluginInteractor pluginInteractor;
    private final PlatformInteractor platformInteractor;

    @Inject
    public DebugCommand(SerializationService serializer, Provider<Messages> messages, Provider<Commands> commands, PluginInteractor pluginInteractor, PlatformInteractor platformInteractor) {
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
        me.whereareiam.socialismus.api.model.Command command = commands.get().getCommands().get("debug");

        return Map.of(
                "command." + command.getAliases().getFirst() + ".name", command.getUsage().replace("{command}", String.join("|", command.getAliases())),
                "command." + command.getAliases().getFirst() + ".permission", command.getPermission(),
                "command." + command.getAliases().getFirst() + ".description", command.getDescription(),
                "command." + command.getAliases().getFirst() + ".usage", command.getUsage()
        );
    }
}