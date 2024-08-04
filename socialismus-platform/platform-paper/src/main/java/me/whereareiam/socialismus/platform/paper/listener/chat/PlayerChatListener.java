package me.whereareiam.socialismus.platform.paper.listener.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.common.chat.ChatCoordinator;
import me.whereareiam.socialismus.platform.paper.renderer.SocialismusRenderer;
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
    private final ChatCoordinator chatCoordinator;

    @Inject
    public PlayerChatListener(ChatCoordinator chatCoordinator) {
        this.chatCoordinator = chatCoordinator;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChatEvent(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Set<Audience> recipients = event.viewers();
        Component content = event.message();

        FormattedChatMessage formattedChatMessage = chatCoordinator.handleChatEvent(
                createChatMessage(player, recipients, content)
        );

        if (formattedChatMessage.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.viewers().clear();
        event.viewers().addAll(
                formattedChatMessage.getRecipients().stream()
                        .map(uuid -> player.getServer().getPlayer(uuid))
                        .collect(Collectors.toSet())
        );
        event.renderer(new SocialismusRenderer(formattedChatMessage));
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
                false,
                false
        );
    }
}
