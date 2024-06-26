package me.whereareiam.socialismus.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.name.Names;
import me.whereareiam.socialismus.adapter.config.dynamic.ChatsConfig;
import me.whereareiam.socialismus.adapter.config.provider.MessagesProvider;
import me.whereareiam.socialismus.adapter.config.provider.SettingsProvider;
import me.whereareiam.socialismus.adapter.config.provider.chat.ChatMessagesProvider;
import me.whereareiam.socialismus.adapter.config.provider.chat.ChatSettingsProvider;
import me.whereareiam.socialismus.adapter.config.provider.chat.ChatsProvider;
import me.whereareiam.socialismus.adapter.config.template.MessagesTemplate;
import me.whereareiam.socialismus.adapter.config.template.SettingsTemplate;
import me.whereareiam.socialismus.adapter.config.template.chat.ChatMessagesTemplate;
import me.whereareiam.socialismus.adapter.config.template.chat.ChatSettingsTemplate;
import me.whereareiam.socialismus.adapter.config.template.chat.ChatTemplate;
import me.whereareiam.socialismus.api.model.config.Messages;
import me.whereareiam.socialismus.api.model.config.Settings;
import me.whereareiam.socialismus.api.model.config.chat.Chat;
import me.whereareiam.socialismus.api.model.config.chat.ChatMessages;
import me.whereareiam.socialismus.api.model.config.chat.ChatSettings;
import me.whereareiam.socialismus.api.output.DefaultConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigBinder extends AbstractModule {
	private final Path dataPath;
	private final Path chatPath;

	public ConfigBinder(Path dataPath) {
		this.dataPath = dataPath;
		this.chatPath = dataPath.resolve("chats");
	}

	@Override
	protected void configure() {
		bind(Path.class).annotatedWith(Names.named("dataPath")).toInstance(dataPath);
		bind(Path.class).annotatedWith(Names.named("chatPath")).toInstance(chatPath);
		createDirectories();

		bind(ObjectMapper.class).toProvider(ConfigManager.class);
		MapBinder<Class<?>, DefaultConfig<?>> mapbinder = MapBinder.newMapBinder(binder(), new TypeLiteral<>() {
		}, new TypeLiteral<>() {
		});
		addTemplates(mapbinder);

		bind(Settings.class).toProvider(SettingsProvider.class);
		bind(Messages.class).toProvider(MessagesProvider.class);

		bind(new TypeLiteral<List<Chat>>() {
		}).annotatedWith(Names.named("chats")).toProvider(ChatsProvider.class);
		bind(ChatSettings.class).toProvider(ChatSettingsProvider.class);
		bind(ChatMessages.class).toProvider(ChatMessagesProvider.class);
	}

	private void addTemplates(MapBinder<Class<?>, DefaultConfig<?>> mapbinder) {
		mapbinder.addBinding(Settings.class).to(SettingsTemplate.class);
		mapbinder.addBinding(Messages.class).to(MessagesTemplate.class);
		mapbinder.addBinding(ChatMessages.class).to(ChatMessagesTemplate.class);
		mapbinder.addBinding(ChatSettings.class).to(ChatSettingsTemplate.class);
		mapbinder.addBinding(ChatsConfig.class).to(ChatTemplate.class);
	}

	private void createDirectories() {
		try {
			Files.createDirectories(dataPath);
			Files.createDirectories(chatPath);
		} catch (IOException e) {
			throw new RuntimeException("Failed to create directories", e);
		}
	}
}