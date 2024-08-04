package me.whereareiam.socialismus.common;

import com.google.inject.Injector;
import me.whereareiam.socialismus.api.AnsiColor;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.PluginType;
import me.whereareiam.socialismus.api.output.ListenerRegistrar;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.command.CommandService;
import me.whereareiam.socialismus.api.output.module.ModuleService;
import me.whereareiam.socialismus.common.chat.ChatContainer;
import me.whereareiam.socialismus.common.chat.worker.ChatSelector;
import me.whereareiam.socialismus.common.chat.worker.FormatSelector;

import java.util.ArrayList;
import java.util.List;

public class CommonSocialismus {
    private Injector injector;

    public void onEnable() {
        injector = CommonInjector.getInjector();

        printWelcomeMessage();

        // Initialize all chats before first event is triggered, leads to faster response time
        injector.getInstance(ChatContainer.class);
        injector.getInstance(ChatSelector.class);
        injector.getInstance(FormatSelector.class);

        injector.getInstance(CommandService.class).registerCommands();

        injector.getInstance(LoggingHelper.class).info("");
        injector.getInstance(ListenerRegistrar.class).registerListeners();

        injector.getInstance(Updater.class).start();
        injector.getInstance(ModuleService.class).loadModules();
        injector.getInstance(IntegrationInitializer.class);
    }

    public void onDisable() {

    }

    private void printWelcomeMessage() {
        LoggingHelper loggingHelper = injector.getInstance(LoggingHelper.class);
        List<String> content = new ArrayList<>();

        content.add(" ");
        content.add(AnsiColor.CYAN + "  █▀ █▀▀   " + AnsiColor.RESET + "Socialismus v" + AnsiColor.GRAY + Constants.getVersion());
        content.add(AnsiColor.CYAN + "  ▄█ █▄▄   " + AnsiColor.RESET + "Platform: "
                + AnsiColor.GRAY
                + PlatformType.getType().toString()
                + " [" + PluginType.getType().toString() + "]"
                + AnsiColor.RESET
        );
        content.add(" ");

        content.forEach(loggingHelper::info);
    }
}
