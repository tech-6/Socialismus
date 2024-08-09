package me.whereareiam.socialismus.platform.bukkit.listener.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.ComponentUtil;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.common.chat.ChatCoordinator;
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
    private final ChatCoordinator chatCoordinator;
    private final BukkitAudiences audiences;

    @Inject
    public PlayerChatListener(ChatCoordinator chatCoordinator, BukkitAudiences audiences) {
        this.chatCoordinator = chatCoordinator;
        this.audiences = audiences;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Set<Player> recipients = event.getRecipients();
        Component content = Component.text(event.getMessage());

        FormattedChatMessage formattedChatMessage = chatCoordinator.handleChatEvent(
                createChatMessage(player, recipients, content)
        );

        if (formattedChatMessage.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.getRecipients().clear();
        event.getRecipients().addAll(
                formattedChatMessage.getRecipients().stream()
                        .map(uuid -> player.getServer().getPlayer(uuid))
                        .collect(Collectors.toSet())
        );

        event.setFormat(ComponentUtil.toString(formattedChatMessage.getFormat()).replace("{message}", "%2$s"));
        event.setMessage(ComponentUtil.toString(formattedChatMessage.getContent()));
    }

    private ChatMessage createChatMessage(Player player, Set<Player> recipients, Component content) {
        return new ChatMessage(
                new DummyPlayer(player.getName(), player.getUniqueId(), audiences.player(player), player.getWorld().getName(), Locale.of(player.getLocale())),
                recipients.stream().map(Entity::getUniqueId).collect(Collectors.toSet()),
                content,
                null,
                false,
                false
        );
    }
}
