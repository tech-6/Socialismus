package me.whereareiam.socialismus.common.chat.logic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.ComponentUtil;
import me.whereareiam.socialismus.api.input.WorkerProcessor;
import me.whereareiam.socialismus.api.input.chat.ChatContainerService;
import me.whereareiam.socialismus.api.model.Worker;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.InternalChat;
import me.whereareiam.socialismus.api.model.config.chat.ChatMessages;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.common.serializer.Serializer;
import net.kyori.adventure.text.TextReplacementConfig;

@Singleton
public class ChatSelector {
	private final ChatContainerService containerService;
	private final LoggingHelper loggingHelper;

	// Configs
	private final ChatMessages chatMessages;

	// Communication
	private final Serializer serializer;

	@Inject
	public ChatSelector(ChatContainerService containerService, WorkerProcessor<ChatMessage> workerProcessor,
	                    LoggingHelper loggingHelper, ChatMessages chatMessages, Serializer serializer) {
		this.containerService = containerService;
		this.loggingHelper = loggingHelper;
		this.chatMessages = chatMessages;
		this.serializer = serializer;

		workerProcessor.addWorker(new Worker<>(this::selectChat, 0, true, false));
	}

	public ChatMessage selectChat(ChatMessage chatMessage) {
		loggingHelper.debug("Selecting chat for user " + chatMessage.getSender().getUsername());
		char symbol = selectSymbol(chatMessage);

		InternalChat chat = containerService.getChat(symbol).getFirst();
		if (chat == null) {
			notifyAboutAbsentChat(chatMessage);
			return chatMessage;
		}

		loggingHelper.debug("Selected chat: " + chat);

		return chatMessage;
	}

	private char selectSymbol(ChatMessage chatMessage) {
		char symbol = ComponentUtil.toPlain(chatMessage.getContent()).charAt(0);

		if (!containerService.hasChat(symbol)) return Character.MIN_VALUE;

		chatMessage.setContent(chatMessage.getContent().replaceText(
				TextReplacementConfig.builder()
						.matchLiteral(String.valueOf(symbol))
						.replacement("")
						.once()
						.build()
		));

		loggingHelper.debug("Selected chat symbol: " + symbol);

		return symbol;
	}

	private void notifyAboutAbsentChat(ChatMessage chatMessage) {
		chatMessage.getSender().getAudience().sendMessage(
				serializer.format(chatMessage.getSender(), chatMessages.getNoChatMatch())
		);
	}
}
