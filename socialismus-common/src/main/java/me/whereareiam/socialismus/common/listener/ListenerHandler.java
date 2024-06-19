package me.whereareiam.socialismus.common.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.common.chat.ChatSelector;

@Singleton
public class ListenerHandler {
	private final ChatSelector chatSelector;

	@Inject
	public ListenerHandler(ChatSelector chatSelector) {
		this.chatSelector = chatSelector;
	}

	public ChatMessage handleChatEvent(ChatMessage chatMessage) {

		return chatMessage;
	}
}
