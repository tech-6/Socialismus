package me.whereareiam.socialismus.platform.bukkit.listener.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.ComponentUtil;
import me.whereareiam.socialismus.api.input.container.PlayerContainerService;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.common.chat.ChatCoordinator;
import me.whereareiam.socialismus.common.chat.ChatMessageFactory;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
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
    private final ChatCoordinator chatCoordinator;
    private final ChatMessageFactory chatMessageFactory;

    @Inject
    public PlayerChatListener(ChatCoordinator chatCoordinator, PlayerContainerService playerContainer, BukkitAudiences audiences, ChatMessageFactory chatMessageFactory) {
        this.chatCoordinator = chatCoordinator;
        this.chatMessageFactory = chatMessageFactory;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Set<Player> recipients = event.getRecipients();
        Component content = Component.text(event.getMessage());

        FormattedChatMessage formattedChatMessage = chatCoordinator.handleChatEvent(
                chatMessageFactory.createChatMessage(
                        player.getUniqueId(),
                        recipients.stream().map(Entity::getUniqueId).collect(Collectors.toSet()),
                        content
                )
        );

        if (formattedChatMessage.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.getRecipients().clear();
        event.getRecipients().addAll(
                formattedChatMessage.getRecipients().stream()
                        .map(recipient -> player.getServer().getPlayer(recipient.getUniqueId()))
                        .collect(Collectors.toSet())
        );

        event.setFormat(ComponentUtil.toString(formattedChatMessage.getFormat(), true).replace("{message}", "%2$s"));
        event.setMessage(ComponentUtil.toString(formattedChatMessage.getContent(), true));
    }
}
