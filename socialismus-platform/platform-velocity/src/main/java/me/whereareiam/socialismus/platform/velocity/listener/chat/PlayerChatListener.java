package me.whereareiam.socialismus.platform.velocity.listener.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.common.chat.ChatCoordinator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

import java.util.Collection;
import java.util.stream.Collectors;

@Singleton
public class PlayerChatListener {
    private final ProxyServer proxyServer;
    private final ChatCoordinator chatCoordinator;

    @Inject
    public PlayerChatListener(ProxyServer proxyServer, ChatCoordinator chatCoordinator) {
        this.proxyServer = proxyServer;
        this.chatCoordinator = chatCoordinator;
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        Collection<Player> recipients = proxyServer.getAllPlayers();
        Component content = Component.text(event.getMessage());

        FormattedChatMessage formattedChatMessage = chatCoordinator.handleChatEvent(
                createChatMessage(player, recipients, content)
        );

        event.setResult(PlayerChatEvent.ChatResult.denied());
        notifyRecipients(formattedChatMessage);
    }

    private void notifyRecipients(FormattedChatMessage formattedChatMessage) {
        Component message = formattedChatMessage.getFormat().replaceText(createTextReplacementConfig(formattedChatMessage));

        Collection<Player> recipients = proxyServer.getAllPlayers().stream()
                .filter(player -> formattedChatMessage.getRecipients().contains(player.getUniqueId()))
                .toList();

        recipients.forEach(player -> player.sendMessage(message));
    }

    private ChatMessage createChatMessage(Player player, Collection<Player> recipients, Component content) {
        return new ChatMessage(
                new DummyPlayer(
                        player.getUsername(),
                        player.getUniqueId(),
                        player,
                        player.getCurrentServer().isPresent()
                                ? player.getCurrentServer().get().getServerInfo().getName()
                                : null,
                        player.getEffectiveLocale()
                ),
                recipients.stream()
                        .map(Player::getUniqueId)
                        .collect(Collectors.toSet()),
                content,
                null,
                false
        );
    }

    private TextReplacementConfig createTextReplacementConfig(ChatMessage chatMessage) {
        return TextReplacementConfig.builder()
                .matchLiteral("{message}")
                .replacement(chatMessage.getContent())
                .build();
    }
}
