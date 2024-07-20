package me.whereareiam.socialismus.command.management;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import me.whereareiam.socialismus.api.output.command.CommandService;
import me.whereareiam.socialismus.command.executor.DebugCommand;
import me.whereareiam.socialismus.command.executor.HelpCommand;
import me.whereareiam.socialismus.command.executor.MainCommand;
import me.whereareiam.socialismus.command.executor.ReloadCommand;
import me.whereareiam.socialismus.command.provider.DynamicCommandProvider;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotations.AnnotationParser;

import java.util.stream.Stream;

@Singleton
public class CommandProcessor implements CommandService {
    private final Injector injector;
    private final LoggingHelper loggingHelper;
    private final CommandManager<DummyPlayer> commandManager;
    private final AnnotationParser<DummyPlayer> annotationParser;

    @Inject
    public CommandProcessor(Injector injector, CommandManager<DummyPlayer> commandManager, DynamicCommandProvider commandProvider, LoggingHelper loggingHelper) {
        this.injector = injector;
        this.commandManager = commandManager;
        this.annotationParser = new AnnotationParser<>(commandManager, DummyPlayer.class);
        this.loggingHelper = loggingHelper;

        annotationParser.stringProcessor(commandProvider.getProcessor());
    }

    @Override
    public void registerCommands() {
        commandManager.rootCommands().forEach(commandManager::deleteRootCommand);
        Stream.of(injector.getInstance(MainCommand.class),
                injector.getInstance(HelpCommand.class),
                injector.getInstance(DebugCommand.class),
                injector.getInstance(ReloadCommand.class)
        ).forEach(this::registerCommand);

        loggingHelper.info("  Loaded " + commandManager.commands().size() + " command" + (commandManager.commands().size() == 1 ? "" : "s"));
    }

    @Override
    public void registerCommand(CommandBase command) {
        annotationParser.parse(command);
    }

    @Override
    public int getCommandCount() {
        return commandManager.commands().size();
    }
}


