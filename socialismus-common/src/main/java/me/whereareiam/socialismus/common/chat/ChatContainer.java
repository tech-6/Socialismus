package me.whereareiam.socialismus.common.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.api.input.chat.ChatContainerService;
import me.whereareiam.socialismus.api.model.chat.InternalChat;
import me.whereareiam.socialismus.api.model.config.chat.Chat;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.common.chat.logic.ChatConverter;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ChatContainer implements ChatContainerService {
	private final LoggingHelper loggingHelper;

	private final ConcurrentHashMap<String, InternalChat> chats = new ConcurrentHashMap<>();

	@Inject
	public ChatContainer(@Named("chats") List<Chat> chats, LoggingHelper loggingHelper) {
		this.loggingHelper = loggingHelper;

		chats.forEach(this::addChat);
	}

	@Override
	public void addChat(InternalChat chat) {
		if (chat.getId() == null || chat.getId().isEmpty()) {
			loggingHelper.debug("Chat " + chat.getId() + " has no id");
			return;
		}

		if (chats.containsKey(chat.getId())) {
			loggingHelper.debug("Chat " + chat.getId() + " already exists, but was tried to be added again");
			return;
		}

		this.chats.put(chat.getId(), chat);
	}

	@Override
	public void addChat(Chat chat) {
		addChat(ChatConverter.convert(chat));
	}

	@Override
	public boolean hasChat(String name) {
		return chats.containsKey(name);
	}

	@Override
	public boolean hasChat(char symbol) {
		return chats.values().stream().anyMatch(chat -> chat.getSymbol() == symbol);
	}

	@Override
	public Optional<InternalChat> getChat(String id) {
		return chats.get(id) == null
				? Optional.empty()
				: Optional.of(chats.get(id));
	}

	@Override
	public List<InternalChat> getChat(char symbol) {
		return chats.values().stream()
				.filter(chat -> chat.getSymbol() == symbol)
				.sorted((chat1, chat2) -> Integer.compare(chat2.getPriority(), chat1.getPriority()))
				.toList();
	}

	@Override
	public Set<InternalChat> getChats() {
		return new HashSet<>(chats.values());
	}
}
