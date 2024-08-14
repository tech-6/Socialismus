package me.whereareiam.socialismus.common;

import com.google.inject.Injector;
import me.whereareiam.socialismus.api.AnsiColor;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.PluginType;
import me.whereareiam.socialismus.api.model.chat.ChatMessages;
import me.whereareiam.socialismus.api.model.chat.ChatSettings;
import me.whereareiam.socialismus.api.output.ListenerRegistrar;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.output.command.CommandService;
import me.whereareiam.socialismus.api.output.module.ModuleService;
import me.whereareiam.socialismus.common.chat.worker.FormatSelector;
import me.whereareiam.socialismus.common.chat.worker.chatmessage.ChatSelector;
import me.whereareiam.socialismus.common.chat.worker.chatmessage.RecipientSelector;
import me.whereareiam.socialismus.common.container.ChatContainer;

import java.util.ArrayList;
import java.util.List;

public class CommonSocialismus {
    private Injector injector;

    public void onEnable() {
        injector = CommonInjector.getInjector();

        printWelcomeMessage();

        // Initialize all component before first event is triggered, leads to faster response time
        injector.getInstance(ChatContainer.class);
        injector.getInstance(ChatSelector.class);
        injector.getInstance(RecipientSelector.class);
        injector.getInstance(FormatSelector.class);
        injector.getInstance(ChatMessages.class);
        injector.getInstance(ChatSettings.class);

        injector.getInstance(CommandService.class).registerCommands();

        injector.getInstance(LoggingHelper.class).info("");
        injector.getInstance(ListenerRegistrar.class).registerListeners();

        injector.getInstance(Updater.class).start();
        injector.getInstance(ModuleService.class).loadModules();
    }

    public void onDisable() {

    }

    private void printWelcomeMessage() {
        LoggingHelper loggingHelper = injector.getInstance(LoggingHelper.class);
        List<String> content = new ArrayList<>();

        content.add(" ");
        content.add(AnsiColor.CYAN + "  █▀ █▀▀   " + AnsiColor.RESET + "Socialismus v" + AnsiColor.GRAY + Constants.getVersion() + AnsiColor.RESET);
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
