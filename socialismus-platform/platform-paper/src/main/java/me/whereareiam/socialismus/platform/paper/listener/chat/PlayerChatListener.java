package me.whereareiam.socialismus.platform.paper.listener.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.common.chat.ChatMessageProcessor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class PlayerChatListener implements Listener {
	private final ChatMessageProcessor chatMessageProcessor;

	@Inject
	public PlayerChatListener(ChatMessageProcessor chatMessageProcessor) {
		this.chatMessageProcessor = chatMessageProcessor;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChatEvent(AsyncChatEvent event) {
		Player player = event.getPlayer();
		Set<Audience> recipients = event.viewers();
		Component content = event.message();

		ChatMessage chatMessage = chatMessageProcessor.handleChatEvent(
				createChatMessage(player, recipients, content)
		);

		if (chatMessage.isCancelled()) {
			event.setCancelled(true);
			return;
		}

		event.viewers().clear();
		event.viewers().addAll(
				chatMessage.getRecipients().stream()
						.map(uuid -> player.getServer().getPlayer(uuid))
						.collect(Collectors.toSet())
		);
		event.message(chatMessage.getContent());
	}

	private ChatMessage createChatMessage(Player player, Set<Audience> recipients, Component content) {
		return new ChatMessage(
				new DummyPlayer(player.getName(), player.getUniqueId(), player, player.getWorld().getName(), player.locale()),
				recipients.stream()
						.filter(audience -> audience instanceof Player)
						.map(audience -> (Player) audience)
						.map(Player::getUniqueId)
						.collect(Collectors.toSet()),
				content,
				null,
				false
		);
	}
}
