package me.whereareiam.socialismus.common.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import lombok.Getter;
import me.whereareiam.socialismus.api.input.chat.ChatContainerService;
import me.whereareiam.socialismus.api.model.chat.InternalChat;
import me.whereareiam.socialismus.api.model.config.chat.Chat;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.common.chat.logic.ChatConverter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Singleton
public class ChatContainer implements ChatContainerService {
	private final LoggingHelper loggingHelper;

	private final List<InternalChat> chats = new ArrayList<>();

	@Inject
	public ChatContainer(@Named("chats") List<Chat> chats, LoggingHelper loggingHelper) {
		this.loggingHelper = loggingHelper;

		chats.forEach(this::addChat);
	}

	@Override
	public void addChat(InternalChat chat) {
		if (chats.stream().anyMatch(c -> c.equals(chat))) {
			loggingHelper.debug("Chat " + chat.getId() + " already exists, but was tried to be added again");
			return;
		}

		this.chats.add(chat);
	}

	@Override
	public void addChat(Chat chat) {
		addChat(ChatConverter.convert(chat));
	}

	@Override
	public List<InternalChat> getChat(String name) {
		return chats.stream().filter(chat -> chat.getId().equals(name)).toList();
	}

	@Override
	public List<InternalChat> getChat(char symbol) {
		return chats.stream().filter(chat -> chat.getSymbol() == symbol).toList();
	}
}
