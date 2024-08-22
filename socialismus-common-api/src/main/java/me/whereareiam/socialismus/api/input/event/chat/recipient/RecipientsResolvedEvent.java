package me.whereareiam.socialismus.api.input.event.chat.recipient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.whereareiam.socialismus.api.input.event.base.CancellableEvent;
import me.whereareiam.socialismus.api.input.event.base.Event;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class RecipientsResolvedEvent implements Event, CancellableEvent {
    private final ChatMessage chatMessage;
    private boolean cancelled;
}
