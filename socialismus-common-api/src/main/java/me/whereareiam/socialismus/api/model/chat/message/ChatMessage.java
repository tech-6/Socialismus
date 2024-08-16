package me.whereareiam.socialismus.api.model.chat.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.whereareiam.socialismus.api.model.chat.Chat;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import net.kyori.adventure.text.Component;

import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
public class ChatMessage {
    private final int id;
    private final DummyPlayer sender;
    private Set<DummyPlayer> recipients;

    private Component content;
    private Chat chat;
    private boolean cancelled;
    private boolean vanillaSending;
}
