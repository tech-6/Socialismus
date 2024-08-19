package me.whereareiam.socialismus.platform.velocity.listener.chat;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.output.integration.Integration;
import me.whereareiam.socialismus.api.output.integration.SynchronizationIntegration;
import me.whereareiam.socialismus.api.output.listener.DynamicListener;
import me.whereareiam.socialismus.common.chat.ChatCoordinator;
import me.whereareiam.socialismus.common.chat.ChatMessageFactory;
import me.whereareiam.socialismus.shared.Constants;
import net.kyori.adventure.text.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class PlayerChatListener implements DynamicListener<PlayerChatEvent> {
    private final ProxyServer proxyServer;
    private final ChatCoordinator chatCoordinator;
    private final ChatMessageFactory chatMessageFactory;
    private final Provider<Set<Integration>> integrations;

    @Inject
    public PlayerChatListener(ProxyServer proxyServer, ChatCoordinator chatCoordinator, ChatMessageFactory chatMessageFactory, Provider<Set<Integration>> integrations) {
        this.proxyServer = proxyServer;
        this.chatCoordinator = chatCoordinator;
        this.chatMessageFactory = chatMessageFactory;
        this.integrations = integrations;
    }

    public void onEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        Collection<Player> recipients = proxyServer.getAllPlayers();
        Component content = Component.text(event.getMessage());

        ChatMessage chatMessage = chatMessageFactory.createChatMessage(
                player.getUniqueId(),
                recipients.stream().map(Player::getUniqueId).collect(Collectors.toSet()),
                content
        );

        if (integrations.get().stream().anyMatch(i -> i instanceof SynchronizationIntegration))
            synchronizeChatMessage(chatMessage);
        else
            chatCoordinator.handleChatEvent(chatMessage);

        event.setResult(PlayerChatEvent.ChatResult.denied());
    }

    private void synchronizeChatMessage(ChatMessage chatMessage) {
        try {
            String data = ChatMessage.serialize(chatMessage);

            integrations.get().stream()
                    .filter(i -> i instanceof SynchronizationIntegration)
                    .findFirst()
                    .map(i -> (SynchronizationIntegration) i)
                    .ifPresent(i -> i.sync(Constants.CHANNEL, data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
