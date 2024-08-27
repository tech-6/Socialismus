package me.whereareiam.socialismus.api.input.event.plugin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.whereareiam.socialismus.api.input.event.base.CancellableEvent;
import me.whereareiam.socialismus.api.input.event.base.Event;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PluginReloadedEvent implements Event, CancellableEvent {
    private boolean cancelled;
}
