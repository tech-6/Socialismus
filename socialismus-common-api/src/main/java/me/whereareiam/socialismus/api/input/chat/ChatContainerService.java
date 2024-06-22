package me.whereareiam.socialismus.api.input.chat;

import me.whereareiam.socialismus.api.model.chat.InternalChat;
import me.whereareiam.socialismus.api.model.config.chat.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatContainerService {
	void addChat(InternalChat chat);

	void addChat(Chat chat);

	Optional<InternalChat> getChat(String name);

	List<InternalChat> getChat(char symbol);

	List<InternalChat> getChats();
}
