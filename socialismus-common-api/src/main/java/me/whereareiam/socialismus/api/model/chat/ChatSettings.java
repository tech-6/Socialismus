package me.whereareiam.socialismus.api.model.chat;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Singleton
public class ChatSettings {
    private boolean notifyNoChat;
    private boolean notifyNoFormat;
    private boolean notifyNoPlayers;
    private boolean notifyNoNearbyPlayers;

    private FallbackChatSettings fallback;
    private ChatHistorySettings history;

    @Getter
    @Setter
    @ToString
    public static class FallbackChatSettings {
        private boolean enabled;
        private String chatId;
    }

    @Getter
    @Setter
    @ToString
    public static class ChatHistorySettings {
        private int fillerSize;
        private int historySize;
        private String permission;
        private String bypassPermission;
    }
}
