package me.whereareiam.socialismus.api.input.event;

import me.whereareiam.socialismus.api.input.event.base.Event;

public interface EventManager {
    void register(EventListener eventListener);

    void unregister(EventListener eventListener);

    void call(Event event);
}
