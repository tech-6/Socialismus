package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.PluginType;
import me.whereareiam.socialismus.api.input.PluginInteractor;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

@Singleton
@Command("%command.main")
public class DebugCommand implements CommandBase {
    private final SerializationService serializer;
    private final Provider<Messages> messages;

    // Data provider
    private final PluginInteractor pluginInteractor;
    private final PlatformInteractor platformInteractor;

    @Inject
    public DebugCommand(SerializationService serializer, Provider<Messages> messages, PluginInteractor pluginInteractor, PlatformInteractor platformInteractor) {
        this.serializer = serializer;
        this.messages = messages;
        this.pluginInteractor = pluginInteractor;
        this.platformInteractor = platformInteractor;
    }

    @Command("%command.debug")
    @CommandDescription("%description.debug")
    @Permission("%permission.debug")
    public void onCommand(DummyPlayer dummyPlayer) {
        String message = String.join("\n", messages.get().getCommands().getDebugCommand().getFormat());

        message = message.replace("{serverVersion}", platformInteractor.getServerVersion())
                .replace("{pluginVersion}", pluginInteractor.getPluginVersion())
                .replace("{serverPlatform}", PlatformType.getType().name())
                .replace("{pluginPlatform}", PluginType.getType().name())
                .replace("{javaVersion}", System.getProperty("java.version"))
                .replace("{os}", System.getProperty("os.name"));

        dummyPlayer.getAudience().sendMessage(serializer.format(dummyPlayer, message));
    }
}