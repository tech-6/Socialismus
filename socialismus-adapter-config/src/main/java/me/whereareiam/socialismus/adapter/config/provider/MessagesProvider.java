package me.whereareiam.socialismus.adapter.config.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.management.ConfigLoader;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.Registry;
import me.whereareiam.socialismus.api.model.config.message.Messages;

import java.nio.file.Path;

public class MessagesProvider implements Provider<Messages>, Reloadable {
    private final Path dataPath;
    private final ConfigLoader configLoader;
    private Messages messages;

    @Inject
    public MessagesProvider(@Named("dataPath") Path dataPath, ConfigLoader configLoader, Registry<Reloadable> registry) {
        this.dataPath = dataPath;
        this.configLoader = configLoader;

        registry.register(this);
    }

    @Override
    public Messages get() {
        if (messages == null) load();

        return messages;
    }

    @Override
    public void reload() {
        load();
    }

    private void load() {
        messages = configLoader.load(dataPath.resolve("messages"), Messages.class);
    }
}