package me.whereareiam.socialismus.api.model.chat;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Singleton
public class ChatMessages {
    private String noChatMatch;
    private String noPlayers;
    private String noFallbackChat;
    private String noNearbyPlayers;
}
