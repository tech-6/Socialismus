package me.whereareiam.socialismus.platform.bukkit.listener.chat;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.DummyPlayer;
import me.whereareiam.socialismus.api.model.chat.ChatMessage;
import me.whereareiam.socialismus.common.listener.ListenerHandler;
import me.whereareiam.socialismus.common.util.ComponentUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class PlayerChatListener implements Listener {
	private final ListenerHandler listenerHandler;

	public PlayerChatListener(ListenerHandler listenerHandler) {
		this.listenerHandler = listenerHandler;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		Set<Player> recipients = event.getRecipients();
		Component content = Component.text(event.getMessage());

		ChatMessage chatMessage = listenerHandler.handleChatEvent(
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
				new DummyPlayer(player.getName(), player.getUniqueId()),
				recipients.stream().map(Entity::getUniqueId).collect(Collectors.toSet()),
				content,
				null,
				false
		);
	}
}
