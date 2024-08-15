package me.whereareiam.socialismus.platform.paper.listener.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.common.chat.ChatCoordinator;
import me.whereareiam.socialismus.common.chat.ChatMessageFactory;
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
    private final ChatMessageFactory chatMessageFactory;

    @Inject
    public PlayerChatListener(ChatCoordinator chatCoordinator, ChatMessageFactory chatMessageFactory) {
        this.chatCoordinator = chatCoordinator;
        this.chatMessageFactory = chatMessageFactory;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChatEvent(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Set<Audience> recipients = event.viewers();
        Component content = event.message();

        FormattedChatMessage formattedChatMessage = chatCoordinator.handleChatEvent(
                chatMessageFactory.createChatMessage(
                        player.getUniqueId(),
                        recipients.stream()
                                .filter(c -> !(c.getClass().getName().equals("com.destroystokyo.paper.console.TerminalConsoleCommandSender")))
                                .map(audience -> ((Player) audience).getUniqueId())
                                .collect(Collectors.toSet()),
                        content
                )
        );

        if (formattedChatMessage.isCancelled() || !formattedChatMessage.isVanillaSending()) {
            event.setCancelled(true);
            return;
        }

        event.viewers().clear();
        event.viewers().addAll(
                formattedChatMessage.getRecipients().stream()
                        .map(recipient -> player.getServer().getPlayer(recipient.getUniqueId()))
                        .collect(Collectors.toSet())
        );
        event.renderer(new SocialismusRenderer(formattedChatMessage));
    }
}
