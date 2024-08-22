package me.whereareiam.socialismus.api.input.event.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.whereareiam.socialismus.api.input.event.base.CancellableEvent;
import me.whereareiam.socialismus.api.input.event.base.Event;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DummyPlayerRemovedEvent implements Event, CancellableEvent {
    private final DummyPlayer dummyPlayer;
    private boolean cancelled;
}
