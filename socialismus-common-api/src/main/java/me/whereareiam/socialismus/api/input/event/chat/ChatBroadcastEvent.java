package me.whereareiam.socialismus.api.input.event.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.whereareiam.socialismus.api.input.event.base.CancellableEvent;
import me.whereareiam.socialismus.api.input.event.base.Event;
import me.whereareiam.socialismus.api.model.chat.message.FormattedChatMessage;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ChatBroadcastEvent implements Event, CancellableEvent {
    private final FormattedChatMessage chatMessage;
    private boolean cancelled;
}
