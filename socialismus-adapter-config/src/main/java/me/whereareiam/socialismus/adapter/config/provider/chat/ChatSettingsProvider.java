package me.whereareiam.socialismus.adapter.config.provider.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.management.ConfigLoader;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.model.chat.ChatSettings;

import java.nio.file.Path;

@Singleton
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
        if (chatSettings != null) return chatSettings;

        load();

        return chatSettings;
    }

    @Override
    public void reload() {
        load();
    }

    private void load() {
        chatSettings = configLoader.load(dataPath.resolve("settings"), ChatSettings.class);
    }
}