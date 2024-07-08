package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import me.whereareiam.socialismus.command.builder.HelpBuilder;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotation.specifier.Range;
import org.incendo.cloud.annotations.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Singleton
@Command("%command.main")
public class HelpCommand implements CommandBase {
    private final CommandManager<DummyPlayer> commandManager;
    private final SerializationService serializer;
    private final HelpBuilder helpBuilder;

    @Inject
    public HelpCommand(CommandManager<DummyPlayer> commandManager, SerializationService serializer, HelpBuilder helpBuilder) {
        this.commandManager = commandManager;
        this.serializer = serializer;
        this.helpBuilder = helpBuilder;
    }

    @Command("%command.help")
    @CommandDescription("%description.help")
    @Permission("%permission.help")
    public void onCommand(DummyPlayer dummyPlayer, @Range(min = "1") @Default("1") @Argument(value = "page", description = "%argument.expected-number") int page) {
        Collection<org.incendo.cloud.Command<DummyPlayer>> allowedCommands = commandManager.commands()
                .stream()
                .filter(command -> dummyPlayer.getUsername() == null || commandManager.hasPermission(dummyPlayer, command.commandPermission().permissionString()))
                .collect(Collectors.toList());

        dummyPlayer.getAudience().sendMessage(serializer.format(dummyPlayer, helpBuilder.buildHelpMessage(allowedCommands, page)));
    }
}