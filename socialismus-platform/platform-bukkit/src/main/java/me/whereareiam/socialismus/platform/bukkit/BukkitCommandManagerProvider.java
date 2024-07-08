package me.whereareiam.socialismus.platform.bukkit;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.settings.Settings;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.command.management.CommandExceptionHandler;
import me.whereareiam.socialismus.command.provider.CommandManagerProvider;
import me.whereareiam.socialismus.platform.bukkit.mapper.CommandSenderMapper;
import org.bukkit.plugin.Plugin;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;

@Singleton
public class BukkitCommandManagerProvider extends CommandManagerProvider {
    private final Plugin plugin;

    // Mapper
    private final CommandSenderMapper commandSenderMapper;

    @Inject
    public BukkitCommandManagerProvider(CommandExceptionHandler exceptionHandler, Provider<Settings> settings, Plugin plugin, CommandSenderMapper commandSenderMapper) {
        super(exceptionHandler, settings);
        this.plugin = plugin;
        this.commandSenderMapper = commandSenderMapper;
    }

    @Override
    protected CommandManager<DummyPlayer> createLegacyPaperCommandManager() {
        LegacyPaperCommandManager<DummyPlayer> commandManager = new LegacyPaperCommandManager<>(
                plugin,
                ExecutionCoordinator.asyncCoordinator(),
                commandSenderMapper
        );

        if (commandManager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER) && settings.get().getMisc().isAllowBrigadierCommands())
            commandManager.registerBrigadier();
        if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION))
            commandManager.registerAsynchronousCompletions();

        return commandManager;
    }

    @Override
    protected CommandManager<DummyPlayer> createPaperCommandManager() {
        throw new UnsupportedOperationException("PaperCommandManager is not supported on Bukkit");
    }

    @Override
    protected CommandManager<DummyPlayer> createVelocityCommandManager() {
        throw new UnsupportedOperationException("VelocityCommandManager is not supported on Paper");
    }
}
