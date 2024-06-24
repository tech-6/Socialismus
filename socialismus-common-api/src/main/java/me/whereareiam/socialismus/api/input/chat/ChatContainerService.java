package me.whereareiam.socialismus.api.input.chat;

import me.whereareiam.socialismus.api.model.chat.InternalChat;
import me.whereareiam.socialismus.api.model.config.chat.Chat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatContainerService {
	void addChat(InternalChat chat);

	void addChat(Chat chat);

	boolean hasChat(String name);

	boolean hasChat(char symbol);

	Optional<InternalChat> getChat(String id);

	List<InternalChat> getChat(char symbol);

	Set<InternalChat> getChats();
}
