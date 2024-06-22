package me.whereareiam.socialismus.common.chat.logic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.chat.ChatContainerService;
import me.whereareiam.socialismus.api.input.chat.ChatMessageWorker;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.ChatWorker;
import me.whereareiam.socialismus.common.util.ComponentUtil;

@Singleton
public class ChatSelector {
	private final ChatContainerService containerService;

	@Inject
	public ChatSelector(ChatContainerService containerService, ChatMessageWorker chatMessageWorker) {
		this.containerService = containerService;

		chatMessageWorker.addChatWorker(new ChatWorker(this::selectChat, 0, true, false));
	}

	public ChatMessage selectChat(ChatMessage chatMessage) {
		char symbol = ComponentUtil.toString(chatMessage.getContent()).charAt(0);


		return chatMessage;
	}
}
