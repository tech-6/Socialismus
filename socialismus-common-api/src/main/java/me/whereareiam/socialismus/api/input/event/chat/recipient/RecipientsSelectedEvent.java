package me.whereareiam.socialismus.api.input.event.chat.recipient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.whereareiam.socialismus.api.input.event.base.CancellableEvent;
import me.whereareiam.socialismus.api.input.event.base.Event;
import me.whereareiam.socialismus.api.model.chat.message.ChatMessage;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class RecipientsSelectedEvent implements Event, CancellableEvent {
    private final ChatMessage chatMessage;
    private final List<DummyPlayer> oldRecipients;
    private boolean cancelled;
}
