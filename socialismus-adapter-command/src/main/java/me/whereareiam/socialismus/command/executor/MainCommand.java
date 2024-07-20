package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.command.Commands;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

@Singleton
public class MainCommand implements CommandBase {
    private final CommandManager<DummyPlayer> commandManager;
    private final Provider<Commands> commands;

    @Inject
    public MainCommand(CommandManager<DummyPlayer> commandManager, Provider<Commands> commands) {
        this.commandManager = commandManager;
        this.commands = commands;
    }

    @Command("%command.main")
    @CommandDescription("%description.main")
    @Permission("%permission.main")
    public void onCommand(DummyPlayer dummyPlayer) {
        commandManager.commandExecutor().executeCommand(
                dummyPlayer,
                commands.get().getCommands().get("main").getAliases().getFirst()
                        + " "
                        + commands.get().getCommands().get("help").getAliases().getFirst()
                        + " 1"
        );
    }
}
