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
    private boolean notifyNoPlayers;
    private boolean notifyNoNearbyPlayers;

    private FallbackChatSettings fallback;

    @Getter
    @Setter
    @ToString
    public static class FallbackChatSettings {
        private boolean enabled;
        private String chatId;
    }
}
