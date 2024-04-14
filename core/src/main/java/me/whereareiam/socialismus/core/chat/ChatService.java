package me.whereareiam.socialismus.core.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.event.chat.BeforeChatSendMessageEvent;
import me.whereareiam.socialismus.api.model.chat.Chat;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.core.config.message.MessagesConfig;
import me.whereareiam.socialismus.core.util.LoggerUtil;
import me.whereareiam.socialismus.core.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Singleton
public class ChatService {
	private final LoggerUtil loggerUtil;
	private final MessageUtil messageUtil;
	private final MessagesConfig messages;

	private final ChatRequirementValidator chatRequirementValidator;
	private final ChatBroadcaster chatBroadcaster;

	@Inject
	public ChatService(LoggerUtil loggerUtil, MessageUtil messageUtil, MessagesConfig messages,
	                   ChatRequirementValidator chatRequirementValidator, ChatBroadcaster chatBroadcaster) {
		this.loggerUtil = loggerUtil;
		this.messageUtil = messageUtil;
		this.messages = messages;

		this.chatRequirementValidator = chatRequirementValidator;
		this.chatBroadcaster = chatBroadcaster;

		loggerUtil.trace("Initializing class: " + this);
	}

	public void distributeMessage(ChatMessage chatMessage) {
		loggerUtil.trace("Distributing ChatMessage: " + chatMessage);

		Player sender = chatMessage.getSender();
		Chat chat = chatMessage.getChat();

		if (!chatRequirementValidator.validatePlayer(chatMessage)) {
			chatMessage.setCancelled(true);
			loggerUtil.trace(sender.getName() + " didn't met requirements");

			return;
		}

		if (chatMessage.getRecipients().isEmpty()) chatMessage.setRecipients(Bukkit.getOnlinePlayers());
		chatMessage.setRecipients(chatRequirementValidator.validatePlayers(chatMessage));

		BeforeChatSendMessageEvent event = new BeforeChatSendMessageEvent(chatMessage);
		Bukkit.getPluginManager().callEvent(event);

		chatMessage = event.getChatMessage();
		if (event.isCancelled()) return;

		if (chat.requirements.recipient.radius != -1 && chatMessage.getRecipients().size() == 1) {
			String noOnlinePlayers = messages.chat.noOnlinePlayers;
			if (noOnlinePlayers != null) {
				messageUtil.sendMessage(sender, noOnlinePlayers);
				chatMessage.setCancelled(true);

				return;
			}

			String noNearbyPlayers = messages.chat.noNearbyPlayers;
			if (noNearbyPlayers != null) {
				messageUtil.sendMessage(sender, noNearbyPlayers);
				chatMessage.setCancelled(true);

				return;
			}
		}

		chatBroadcaster.broadcastMessage(chatMessage);
	}
}
