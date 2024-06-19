package me.whereareiam.socialismus.common.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.InternalChat;
import me.whereareiam.socialismus.api.model.config.chat.Chat;
import me.whereareiam.socialismus.common.util.ComponentUtil;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ChatSelector {
	private final List<InternalChat> chats = new ArrayList<>();

	@Inject
	public ChatSelector(@Named("chats") List<Chat> chats) {
		chats.forEach(chat -> {
			InternalChat internalChat = InternalChat.builder()
					.symbol(Character.MIN_VALUE)
					.vanillaSending(true)
					.build();

			this.chats.add(internalChat);
		});
	}

	public ChatMessage selectChat(ChatMessage chatMessage) {
		char symbol = ComponentUtil.toString(chatMessage.getContent()).charAt(0);


		return chatMessage;
	}
}
