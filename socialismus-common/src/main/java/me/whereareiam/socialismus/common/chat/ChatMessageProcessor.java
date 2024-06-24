package me.whereareiam.socialismus.common.chat;

import com.google.inject.Singleton;
import lombok.Getter;
import me.whereareiam.socialismus.api.input.chat.ChatMessageWorker;
import me.whereareiam.socialismus.api.model.Worker;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;

import java.util.LinkedList;

@Getter
@Singleton
public class ChatMessageProcessor implements ChatMessageWorker {
	private final LinkedList<Worker<ChatMessage>> workers = new LinkedList<>();

	public ChatMessage handleChatEvent(ChatMessage chatMessage) {
		for (Worker<ChatMessage> chatWorker : workers) {
			chatMessage = chatWorker.getFunction().apply(chatMessage);

			if (chatMessage.isCancelled())
				break;
		}

		return chatMessage;
	}


	@Override
	public boolean removeWorker(Worker<ChatMessage> worker) {
		if (!worker.isRemovable())
			return false;

		workers.remove(worker);
		return true;
	}

	@Override
	public void addWorker(Worker<ChatMessage> worker) {
		if (workers.stream().anyMatch(w -> w.getPriority() == worker.getPriority()))
			return;

		workers.add(worker);
		workers.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));
	}
}
