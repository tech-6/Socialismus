package me.whereareiam.socialismus.common.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.api.input.chat.ChatContainerService;
import me.whereareiam.socialismus.api.model.chat.InternalChat;
import me.whereareiam.socialismus.api.model.config.chat.Chat;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.common.chat.logic.ChatConverter;

import java.util.List;
import java.util.Optional;
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
	public Optional<InternalChat> getChat(String name) {
		return chats.get(name) == null
				? Optional.empty()
				: Optional.of(chats.get(name));
	}

	@Override
	public List<InternalChat> getChat(char symbol) {
		return chats.values().stream()
				.filter(chat -> chat.getSymbol() == symbol)
				.toList();
	}

	@Override
	public List<InternalChat> getChats() {
		return chats.values().stream().toList();
	}
}
