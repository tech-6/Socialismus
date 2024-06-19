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
import me.whereareiam.socialismus.api.type.chat.ChatTriggerType;
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
		List<Chat> chats = new ArrayList<>();
		try (Stream<Path> paths = Files.list(dataPath)) {
			List<Path> files = paths.toList();

			files = files.stream()
					.filter(file -> file.getFileName().toString().endsWith(".json"))
					.filter(file -> !file.getFileName().toString().equals("settings.json")
							&& !file.getFileName().toString().equals("messages.json")
					).toList();

			if (files.isEmpty()) createDefaultConfig();

			files.forEach(file -> {
				String fileName = file.getFileName().toString();

				ConfigLoader<ChatsConfig> configLoader = new ConfigLoader<>(dataPath);
				configLoader.load(ChatsConfig.class, "", fileName);

				chats.addAll(configLoader.getConfig().getChats());
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		loggingHelper.info("  Loaded " + chats.size() + " chats");

		return chats;
	}

	private void createDefaultConfig() {
		Chat chat = new Chat(
				"default",
				ChatType.GLOBAL,
				List.of(
						ChatTriggerType.CHAT,
						ChatTriggerType.COMMAND,
						ChatTriggerType.OTHER
				),
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