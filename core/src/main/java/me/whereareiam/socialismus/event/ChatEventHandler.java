package me.whereareiam.socialismus.event;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.chat.ChatService;
import me.whereareiam.socialismus.chat.message.ChatMessage;
import me.whereareiam.socialismus.chat.message.ChatMessageFactory;
import me.whereareiam.socialismus.config.setting.SettingsConfig;
import me.whereareiam.socialismus.feature.swapper.SwapperService;
import org.bukkit.entity.Player;

@Singleton
public class ChatEventHandler {
    private final ChatMessageFactory chatMessageFactory;
    private final SettingsConfig settingsConfig;

    private final SwapperService swapperService;
    private final ChatService chatService;

    @Inject
    public ChatEventHandler(ChatMessageFactory chatMessageFactory, SettingsConfig settingsConfig, ChatService chatService, SwapperService swapperService) {
        this.chatMessageFactory = chatMessageFactory;
        this.settingsConfig = settingsConfig;

        this.chatService = chatService;
        this.swapperService = swapperService;
    }

    public void handleChatEvent(Player player, String message) {
        ChatMessage chatMessage = chatMessageFactory.createChatMessage(player, message);

        if (settingsConfig.features.swapper) {
            chatMessage = swapperService.swapPlaceholders(chatMessage);
        }

        if (settingsConfig.features.chats && chatMessage.getChat() != null) {
            chatService.distributeMessage(chatMessage);
        }
    }
}

