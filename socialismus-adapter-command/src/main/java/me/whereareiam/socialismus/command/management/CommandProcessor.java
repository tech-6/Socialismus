package me.whereareiam.socialismus.command.management;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.AnsiColor;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import me.whereareiam.socialismus.api.output.command.CommandService;
import me.whereareiam.socialismus.command.executor.*;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotations.AnnotationParser;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Singleton
public class CommandProcessor implements CommandService {
    private final Injector injector;
    private final LoggingHelper loggingHelper;
    private final Provider<CommandManager<DummyPlayer>> commandManager;
    private final CommandTranslator commandTranslator;
    private final Map<String, String> translations = new HashMap<>();
    private AnnotationParser<DummyPlayer> annotationParser;

    @Inject
    public CommandProcessor(Injector injector, LoggingHelper loggingHelper, Provider<CommandManager<DummyPlayer>> commandManager, CommandTranslator commandTranslator) {
        this.injector = injector;
        this.loggingHelper = loggingHelper;
        this.commandManager = commandManager;
        this.commandTranslator = commandTranslator;
    }

    @Override
    public void registerCommands() {
        final CommandManager<DummyPlayer> commandManager = this.commandManager.get();

        annotationParser = new AnnotationParser<>(commandManager, DummyPlayer.class);
        annotationParser.stringProcessor(commandTranslator.getProcessor());

        commandManager.rootCommands().forEach(commandManager::deleteRootCommand);
        Stream.of(injector.getInstance(MainCommand.class),
                injector.getInstance(HelpCommand.class),
                injector.getInstance(DebugCommand.class),
                injector.getInstance(ReloadCommand.class),
                injector.getInstance(ClearCommand.class)
        ).forEach(this::registerCommand);

        loggingHelper.info("  Loaded " + AnsiColor.GRAY + commandManager.commands().size() + AnsiColor.RESET + " command" + (commandManager.commands().size() == 1 ? "" : "s"));
    }

    @Override
    public void registerTranslation(String key, String value) {
        translations.put(key, value);
    }

    @Override
    public void registerCommand(CommandBase command) {
        translations.putAll(command.getTranslations());
        annotationParser.parse(command);
    }

    @Override
    public int getCommandCount() {
        return commandManager.get().commands().size();
    }

    @Override
    public String getTranslation(String key) {
        return translations.get(key);
    }

    @Override
    public Map<String, String> getTranslations() {
        return translations;
    }
}


