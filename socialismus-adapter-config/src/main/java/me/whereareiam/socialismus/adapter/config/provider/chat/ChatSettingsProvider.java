package me.whereareiam.socialismus.adapter.config.provider.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.management.ConfigLoader;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.Registry;
import me.whereareiam.socialismus.api.model.config.chat.ChatSettings;

import java.nio.file.Path;

public class ChatSettingsProvider implements Provider<ChatSettings>, Reloadable {
    private final Path dataPath;
    private final ConfigLoader configLoader;
    private ChatSettings chatSettings;

    @Inject
    public ChatSettingsProvider(@Named("chatPath") Path dataPath, ConfigLoader configLoader, Registry<Reloadable> registry) {
        this.dataPath = dataPath;
        this.configLoader = configLoader;

        registry.register(this);
    }

    @Override
    public ChatSettings get() {
        if (chatSettings == null) load();

        return chatSettings;
    }

    @Override
    public void reload() {
        load();
    }

    private void load() {
        chatSettings = configLoader.load(dataPath.resolve("chat"), ChatSettings.class);
    }
}