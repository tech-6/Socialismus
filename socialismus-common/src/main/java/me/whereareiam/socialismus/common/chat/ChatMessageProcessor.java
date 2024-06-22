package me.whereareiam.socialismus.common.chat;

import com.google.inject.Singleton;
import lombok.Getter;
import me.whereareiam.socialismus.api.input.chat.ChatMessageWorker;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.ChatWorker;

import java.util.LinkedList;

@Getter
@Singleton
public class ChatMessageProcessor implements ChatMessageWorker {
	private final LinkedList<ChatWorker> chatWorkers = new LinkedList<>();

	public ChatMessage handleChatEvent(ChatMessage chatMessage) {
		for (ChatWorker chatWorker : chatWorkers) {
			chatMessage = chatWorker.getFunction().apply(chatMessage);

			if (chatMessage.isCancelled())
				break;
		}

		return chatMessage;
	}

	@Override
	public boolean removeChatWorker(ChatWorker chatWorker) {
		if (!chatWorker.isRemovable())
			return false;

		chatWorkers.remove(chatWorker);
		return true;
	}

	@Override
	public void addChatWorker(ChatWorker chatWorker) {
		if (chatWorkers.stream().anyMatch(worker -> worker.getPriority() == chatWorker.getPriority()))
			return;

		chatWorkers.add(chatWorker);
		chatWorkers.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));
	}
}
