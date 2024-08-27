package me.whereareiam.socialismus.api.input.event.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.whereareiam.socialismus.api.input.event.base.CancellableEvent;
import me.whereareiam.socialismus.api.input.event.base.Event;
import me.whereareiam.socialismus.api.model.chat.Chat;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class ChatResolvedEvent implements Event, CancellableEvent {
    private final ChatMessage chatMessage;
    private Chat chat;
    private boolean cancelled;
}
