package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.CommandEntity;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

import java.util.Map;

@Singleton
public class MainCommand implements CommandBase {
    private final CommandManager<DummyPlayer> commandManager;
    private final Provider<Map<String, CommandEntity>> commands;

    @Inject
    public MainCommand(CommandManager<DummyPlayer> commandManager, Provider<Map<String, CommandEntity>> commands) {
        this.commandManager = commandManager;
        this.commands = commands;
    }

    @Command("%command.main")
    @CommandDescription("%description.main")
    @Permission("%permission.main")
    public void onCommand(DummyPlayer dummyPlayer) {
        commandManager.commandExecutor().executeCommand(
                dummyPlayer,
                commands.get().get("main").getAliases().getFirst()
                        + " "
                        + commands.get().get("help").getAliases().getFirst()
                        + " 1"
        );
    }

    @Override
    public Map<String, String> getTranslations() {
        CommandEntity commandEntity = commands.get().get("main");

        return Map.of(
                "commandEntity." + commandEntity.getAliases().getFirst() + ".name", commandEntity.getUsage().replace("{commandEntity}", String.join("|", commandEntity.getAliases())),
                "commandEntity." + commandEntity.getAliases().getFirst() + ".permission", commandEntity.getPermission(),
                "commandEntity." + commandEntity.getAliases().getFirst() + ".description", commandEntity.getDescription(),
                "commandEntity." + commandEntity.getAliases().getFirst() + ".usage", commandEntity.getUsage()
        );
    }
}
