package me.whereareiam.socialismus.common.chat.logic;

import me.whereareiam.socialismus.api.model.chat.InternalChat;
import me.whereareiam.socialismus.api.model.config.chat.Chat;

import java.util.List;
import java.util.stream.Collectors;

public class ChatConverter {
	public static List<InternalChat> convert(List<Chat> chats) {
		return chats.stream()
				.map(ChatConverter::convert)
				.collect(Collectors.toList());
	}

	public static InternalChat convert(Chat chat) {
		return InternalChat.builder()
				.symbol(Character.MIN_VALUE)
				.vanillaSending(true)
				.build();
	}
}
