package me.whereareiam.socialismus.common.listener;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;

@Singleton
public class ListenerHandler {
	public ChatMessage handleChatEvent(ChatMessage chatMessage) {
		

		return chatMessage;
	}
}
