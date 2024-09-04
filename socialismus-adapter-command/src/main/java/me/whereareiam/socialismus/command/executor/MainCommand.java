package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.CommandEntity;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import me.whereareiam.socialismus.api.output.command.CommandCooldown;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.Map;

@Singleton
public class MainCommand extends CommandBase {
    private static final String COMMAND_NAME = "main";
    private final Provider<Map<String, CommandEntity>> commands;
    private final HelpCommand helpCommand;

    @Inject
    public MainCommand(Provider<Map<String, CommandEntity>> commands, HelpCommand helpCommand) {
        super(COMMAND_NAME);
        this.commands = commands;
        this.helpCommand = helpCommand;
    }

    @Command("%command." + COMMAND_NAME)
    @CommandDescription("%description." + COMMAND_NAME)
    @CommandCooldown("%cooldown." + COMMAND_NAME)
    @Permission("%permission." + COMMAND_NAME)
    public void onCommand(DummyPlayer dummyPlayer) {
        helpCommand.onCommand(dummyPlayer, 1);
    }

    @Override
    public CommandEntity getCommandEntity() {
        return commands.get().get(COMMAND_NAME);
    }
}
