package me.whereareiam.socialismus.adapter.config.provider.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.ChatsConfig;
import me.whereareiam.socialismus.adapter.config.ConfigLoader;
import me.whereareiam.socialismus.api.model.config.chat.Chat;
import me.whereareiam.socialismus.api.model.config.chat.ChatFormat;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.api.type.chat.ChatType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Singleton
public class ChatsProvider implements Provider<List<Chat>> {
	private final Path dataPath;
	private final LoggingHelper loggingHelper;

	@Inject
	public ChatsProvider(@Named("chatPath") Path dataPath, LoggingHelper loggingHelper) {
		this.dataPath = dataPath;
		this.loggingHelper = loggingHelper;
	}

	@Override
	public List<Chat> get() {
		List<Chat> chats;
		List<Path> chatFiles = getChatFiles();

		if (chatFiles.isEmpty()) {
			createDefaultConfig();
			chatFiles = getChatFiles();
		}

		chats = loadChatsFromFiles(chatFiles);
		loggingHelper.info("  Loaded " + chats.size() + " chat" + (chats.size() == 1 ? "" : "s"));

		return chats;
	}

	private List<Path> getChatFiles() {
		try (Stream<Path> paths = Files.list(dataPath)) {
			return paths.filter(file -> file.getFileName().toString().endsWith(".json"))
					.filter(file -> !file.getFileName().toString().equals("settings.json")
							&& !file.getFileName().toString().equals("messages.json"))
					.toList();
		} catch (IOException e) {
			loggingHelper.severe("Error getting chat files", e);

			return new ArrayList<>();
		}
	}

	private List<Chat> loadChatsFromFiles(List<Path> files) {
		List<Chat> chats = new ArrayList<>();
		for (Path file : files) {
			ConfigLoader<ChatsConfig> configLoader = new ConfigLoader<>(dataPath);
			configLoader.load(ChatsConfig.class, "", file.getFileName().toString());

			chats.addAll(configLoader.getConfig().getChats().stream()
					.filter(Chat::isEnabled)
					.toList());
		}
		return chats;
	}

	private void createDefaultConfig() {
		Chat chat = new Chat(
				"default",
				0,
				true,
				ChatType.GLOBAL,
				List.of(new ChatFormat(
						"<gray>{playerName} -> <white>{message}",
						""
				))
		);

		ChatsConfig chatsConfig = new ChatsConfig();
		chatsConfig.getChats().add(chat);

		ConfigLoader<ChatsConfig> configLoader = new ConfigLoader<>(dataPath);
		configLoader.setConfig(chatsConfig);
		configLoader.save("", "default.json");
		configLoader.load(ChatsConfig.class, "", "default.json");
	}
}