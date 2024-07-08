package me.whereareiam.socialismus.command.provider;

import com.google.inject.Provider;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.model.config.settings.MiscellaneousSettings;
import me.whereareiam.socialismus.api.model.config.settings.Settings;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.command.management.CommandExceptionHandler;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.exception.*;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;

public abstract class CommandManagerProvider implements Provider<CommandManager<DummyPlayer>> {
	protected final MiscellaneousSettings settings;
	private final CommandExceptionHandler exceptionHandler;

	public CommandManagerProvider(CommandExceptionHandler exceptionHandler, Settings settings) {
		this.exceptionHandler = exceptionHandler;
		this.settings = settings.getMisc();
	}

	@Override
	public CommandManager<DummyPlayer> get() {
		CommandManager<DummyPlayer> commandManager = switch (PlatformType.getType()) {
			case BUKKIT, SPIGOT -> createLegacyPaperCommandManager();
			case FOLIA, PAPER -> {
				if (settings.isAllowBrigadierCommands()) yield createPaperCommandManager();
				yield createLegacyPaperCommandManager();
			}
			case VELOCITY -> createVelocityCommandManager();
			case UNKNOWN -> throw new IllegalStateException("Unknown platform type");
		};

		createMinecraftExceptionHandler(commandManager);

		return commandManager;
	}

	protected abstract CommandManager<DummyPlayer> createLegacyPaperCommandManager();

	protected abstract CommandManager<DummyPlayer> createPaperCommandManager();

	protected abstract CommandManager<DummyPlayer> createVelocityCommandManager();

	private void createMinecraftExceptionHandler(CommandManager<DummyPlayer> commandManager) {
		MinecraftExceptionHandler.create(DummyPlayer::getAudience)
				.handler(ArgumentParseException.class, exceptionHandler::handleParseException)
				.handler(InvalidCommandSenderException.class, exceptionHandler::handleInvalidCommandSenderException)
				.handler(NoPermissionException.class, exceptionHandler::handleNoPermissionException)
				.handler(InvalidSyntaxException.class, exceptionHandler::handleInvalidSyntaxException)
				.handler(CommandExecutionException.class, exceptionHandler::handleCommandExecutionException)
				.registerTo(commandManager);
	}
}
