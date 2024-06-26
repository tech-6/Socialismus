package me.whereareiam.socialismus.adapter.config.provider.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.ConfigLoader;
import me.whereareiam.socialismus.adapter.config.ConfigManager;
import me.whereareiam.socialismus.adapter.config.dynamic.ChatsConfig;
import me.whereareiam.socialismus.api.model.config.chat.Chat;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.type.ConfigurationType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Singleton
public class ChatsProvider implements Provider<List<Chat>> {
	private final Path dataPath;
	private final LoggingHelper loggingHelper;
	private final ConfigLoader configLoader;
	private final ConfigurationType configurationType;

	@Inject
	public ChatsProvider(@Named("chatPath") Path dataPath, LoggingHelper loggingHelper,
	                     ConfigLoader configLoader, ConfigManager configManager) {
		this.dataPath = dataPath;
		this.loggingHelper = loggingHelper;
		this.configLoader = configLoader;
		this.configurationType = configManager.getConfigurationType();
	}

	@Override
	public List<Chat> get() {
		List<Chat> chats = new ArrayList<>();

		try (Stream<Path> paths = Files.list(dataPath)) {
			paths.filter(path -> path.getFileName().endsWith(configurationType.getExtension())).forEach(path -> {
				String fileName = path.getFileName().toString().replace(configurationType.getExtension(), "");

				if (Files.isDirectory(path)
						|| fileName.startsWith("messages")
						|| fileName.startsWith("settings")
						|| fileName.isEmpty()
				) return;

				chats.addAll(addChatsFromConfig(path.getParent().resolve(fileName)));
			});
		} catch (IOException e) {
			loggingHelper.severe("Failed to load chat configurations", e);

			return Collections.emptyList();
		}

		if (chats.isEmpty()) chats.addAll(addChatsFromConfig(dataPath.resolve("default")));

		chats.removeIf(chat -> chats.stream().anyMatch(c -> c != chat && c.getId().equals(chat.getId())));
		loggingHelper.info("  Loaded " + chats.size() + " chat" + (chats.size() == 1 ? "" : "s"));

		return chats;
	}

	private List<Chat> addChatsFromConfig(Path path) {
		ChatsConfig chatsConfig = configLoader.load(path, ChatsConfig.class);
		return chatsConfig.getChats().stream()
				.filter(Chat::isEnabled)
				.toList();
	}
}