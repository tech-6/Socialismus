package me.whereareiam.socialismus.platform.velocity.listener.chat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.api.output.listener.DynamicListener;
import me.whereareiam.socialismus.common.chat.ChatCoordinator;
import me.whereareiam.socialismus.common.chat.ChatMessageFactory;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Singleton
public class PlayerChatListener implements DynamicListener<PlayerChatEvent> {
    private final ProxyServer proxyServer;
    private final ChatCoordinator chatCoordinator;
    private final ChatMessageFactory chatMessageFactory;

    @Inject
    public PlayerChatListener(ProxyServer proxyServer, ChatCoordinator chatCoordinator, ChatMessageFactory chatMessageFactory) {
        this.proxyServer = proxyServer;
        this.chatCoordinator = chatCoordinator;
        this.chatMessageFactory = chatMessageFactory;
    }

    public void onEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        Collection<Player> recipients = proxyServer.getAllPlayers();
        Component content = Component.text(event.getMessage());

        chatCoordinator.handleChatEvent(
                chatMessageFactory.createChatMessage(
                        player.getUniqueId(),
                        recipients.stream().map(Player::getUniqueId).collect(Collectors.toSet()),
                        content
                )
        );

        event.setResult(PlayerChatEvent.ChatResult.denied());
    }
}
