package me.whereareiam.socialismus.api.input;

import me.whereareiam.socialismus.api.model.chat.ChatWorker;

import java.util.LinkedList;

public interface ChatMessageWorker {
	LinkedList<ChatWorker> getChatWorkers();

	boolean removeChatWorker(ChatWorker chatWorker);

	void addChatWorker(ChatWorker chatWorker);
}
