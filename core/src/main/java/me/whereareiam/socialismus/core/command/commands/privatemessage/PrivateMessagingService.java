package me.whereareiam.socialismus.core.command.commands.privatemessage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.core.Scheduler;
import me.whereareiam.socialismus.core.util.MessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Singleton
public class PrivateMessagingService {
	private final MessageUtil messageUtil;
	private final Map<Player, Player> lastSenderMap = new HashMap<>();

	@Inject
	public PrivateMessagingService(Scheduler scheduler, MessageUtil messageUtil) {
		this.messageUtil = messageUtil;

		scheduler.scheduleAtFixedRate(lastSenderMap::clear, 0, 10, TimeUnit.MINUTES, java.util.Optional.empty());
	}

	public void sendPrivateMessage(Player sender, Player recipient, Component message) {
		lastSenderMap.put(recipient, sender);

		messageUtil.sendMessage(sender, message);
		messageUtil.sendMessage(recipient, message);
	}

	public Player getLastSender(Player player) {
		return lastSenderMap.get(player);
	}

	public void setSender(Player player, Player sender) {
		lastSenderMap.put(player, sender);
	}
}

