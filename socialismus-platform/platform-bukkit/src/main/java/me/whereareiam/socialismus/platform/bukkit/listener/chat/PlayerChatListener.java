package me.whereareiam.socialismus.platform.bukkit.listener.chat;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.ComponentUtil;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.common.chat.ChatMessageProcessor;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class PlayerChatListener implements Listener {
	private final ChatMessageProcessor chatMessageProcessor;
	private final BukkitAudiences audiences;

	public PlayerChatListener(ChatMessageProcessor chatMessageProcessor, BukkitAudiences audiences) {
		this.chatMessageProcessor = chatMessageProcessor;
		this.audiences = audiences;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		Set<Player> recipients = event.getRecipients();
		Component content = Component.text(event.getMessage());

		ChatMessage chatMessage = chatMessageProcessor.handleChatEvent(
				createChatMessage(player, recipients, content)
		);

		if (chatMessage.isCancelled()) {
			event.setCancelled(true);
			return;
		}

		event.getRecipients().clear();
		event.getRecipients().addAll(
				chatMessage.getRecipients().stream()
						.map(uuid -> player.getServer().getPlayer(uuid))
						.collect(Collectors.toSet())
		);
		event.setMessage(ComponentUtil.toString(chatMessage.getContent()));
	}

	private ChatMessage createChatMessage(Player player, Set<Player> recipients, Component content) {
		return new ChatMessage(
				new DummyPlayer(player.getName(), player.getUniqueId(), audiences.player(player), player.getWorld().getName(), Locale.of(player.getLocale())),
				recipients.stream().map(Entity::getUniqueId).collect(Collectors.toSet()),
				content,
				null,
				false
		);
	}
}
