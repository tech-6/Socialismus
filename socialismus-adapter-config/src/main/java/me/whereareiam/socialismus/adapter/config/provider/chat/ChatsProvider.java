package me.whereareiam.socialismus.adapter.config.provider.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.adapter.config.ChatsConfig;
import me.whereareiam.socialismus.adapter.config.ConfigLoader;
import me.whereareiam.socialismus.api.model.config.chat.Chat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Singleton
public class ChatsProvider implements Provider<List<Chat>> {
	private final Path dataPath;

	@Inject
	public ChatsProvider(@Named("chatPath") Path dataPath) {
		this.dataPath = dataPath;
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

			if (files.isEmpty()) createDefaultConfig(files);

			files.forEach(file -> {
				String fileName = file.getFileName().toString();
				ConfigLoader<ChatsConfig> configLoader = new ConfigLoader<>(file);
				configLoader.load(ChatsConfig.class, "", fileName);
				chats.addAll(configLoader.getConfig().getChats());
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return chats;
	}

	private void createDefaultConfig(List<Path> files) {

	}
}