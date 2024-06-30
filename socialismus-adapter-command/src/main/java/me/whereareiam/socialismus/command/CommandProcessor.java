package me.whereareiam.socialismus.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import me.whereareiam.socialismus.api.output.command.CommandService;
import me.whereareiam.socialismus.command.executor.HelpCommand;
import me.whereareiam.socialismus.command.executor.MainCommand;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.exception.ExceptionHandler;
import org.incendo.cloud.exception.NoSuchCommandException;

import java.util.stream.Stream;

@Singleton
public class CommandProcessor implements CommandService {
	private final Injector injector;
	private final AnnotationParser<DummyPlayer> annotationParser;

	@Inject
	@SuppressWarnings({"rawtypes", "unchecked"})
	public CommandProcessor(Injector injector, CommandManager commandManager, DynamicCommandProvider commandProvider) {
		this.injector = injector;
		this.annotationParser = new AnnotationParser<DummyPlayer>(commandManager, DummyPlayer.class);

		annotationParser.stringProcessor(commandProvider.getProcessor());
		annotationParser.parse(this);

		registerCommands();
	}

	private void registerCommands() {
		Stream.of(
				injector.getInstance(MainCommand.class),
				injector.getInstance(HelpCommand.class)
		).forEach(this::registerCommand);
	}

	@Override
	public void registerCommand(CommandBase command) {
		annotationParser.parse(command);
	}

	@ExceptionHandler(NoSuchCommandException.class)
	public void handleException(NoSuchCommandException exception) {
		System.out.println("Command not found: " + exception.suppliedCommand());
	}
}


