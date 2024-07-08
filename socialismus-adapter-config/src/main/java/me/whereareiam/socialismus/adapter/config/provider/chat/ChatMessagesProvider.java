package me.whereareiam.socialismus.adapter.config.provider.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.management.ConfigLoader;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.Registry;
import me.whereareiam.socialismus.api.model.config.chat.ChatMessages;

import java.nio.file.Path;

public class ChatMessagesProvider implements Provider<ChatMessages>, Reloadable {
    private final Path dataPath;
    private final ConfigLoader configLoader;
    private ChatMessages chatMessages;

    @Inject
    public ChatMessagesProvider(@Named("chatPath") Path dataPath, ConfigLoader configLoader, Registry<Reloadable> registry) {
        this.dataPath = dataPath;
        this.configLoader = configLoader;

        registry.register(this);
    }

    @Override
    public ChatMessages get() {
        if (chatMessages == null) load();

        return chatMessages;
    }

    @Override
    public void reload() {
        load();
    }

    private void load() {
        chatMessages = configLoader.load(dataPath.resolve("chat"), ChatMessages.class);
    }
}