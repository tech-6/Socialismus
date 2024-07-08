package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.config.message.CommandMessages;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.config.settings.Settings;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import me.whereareiam.socialismus.command.PaginationBuilder;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotation.specifier.Range;
import org.incendo.cloud.annotations.*;
import org.incendo.cloud.component.CommandComponent;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Command("%command.main")
public class HelpCommand implements CommandBase {
	private final CommandManager<DummyPlayer> commandManager;
	private final SerializationService serializer;

	private final Settings settings;
	private final Messages messages;

	private final PaginationBuilder paginationBuilder;

	@Inject
	public HelpCommand(CommandManager<DummyPlayer> commandManager, SerializationService serializer, Settings settings, Messages messages, PaginationBuilder paginationBuilder) {
		this.commandManager = commandManager;
		this.serializer = serializer;
		this.settings = settings;
		this.messages = messages;
		this.paginationBuilder = paginationBuilder;
	}

	@Command("%command.help")
	@CommandDescription("%description.help")
	@Permission("%permission.help")
	public void onCommand(DummyPlayer dummyPlayer, @Range(min = "1") @Default("1") @Argument(value = "page", description = "%argument.expected-number") int page) {
		Collection<org.incendo.cloud.Command<DummyPlayer>> allowedCommands = commandManager.commands().stream().filter(command -> dummyPlayer.getUsername() == null || commandManager.hasPermission(dummyPlayer, command.commandPermission().permissionString())).collect(Collectors.toList());

		String message = String.join("\n", messages.getCommands().getHelpCommand().getFormat());

		if (message.contains("{commands}")) message = buildCommandList(allowedCommands, message, page);
		if (message.contains("{pagination}")) message = paginationBuilder.build(message, allowedCommands.size(), page);

		dummyPlayer.getAudience().sendMessage(serializer.format(dummyPlayer, message));
	}

	private String buildCommandList(Collection<org.incendo.cloud.Command<DummyPlayer>> commands, String message, int page) {
		long skip = (long) (page - 1) * settings.getMisc().getCommandsPerPage();

		List<String> commandDescriptions = commands.stream().skip(skip).limit(settings.getMisc().getCommandsPerPage()).map(command -> {
			String commandFormat = messages.getCommands().getHelpCommand().getCommandFormat();
			return commandFormat.replace("{command}", command.rootComponent().name()).replace("{arguments}", " " + formatCommandArguments(command.rootComponent().name(), command.nonFlagArguments())).replace("{description}", command.commandDescription().description().textDescription());
		}).collect(Collectors.toList());

		String commandsString = String.join("\n", commandDescriptions);
		return message.replace("{commands}", commandsString);
	}

	private String formatCommandArguments(String commandName, List<? extends CommandComponent<?>> arguments) {
		CommandMessages.Format messageFormat = messages.getCommands().getFormat();

		return arguments.stream().filter(argument -> !argument.name().equals(commandName)).map(argument -> switch (argument.type()) {
			case REQUIRED_VARIABLE -> messageFormat.getArgument().replace("{argument}", argument.name());
			case OPTIONAL_VARIABLE -> messageFormat.getOptionalArgument().replace("{argument}", argument.name());
			default -> argument.name();
		}).collect(Collectors.joining(" "));
	}
}