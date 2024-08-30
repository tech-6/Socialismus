package me.whereareiam.socialismus.api.input.event;

import me.whereareiam.socialismus.api.input.event.base.Event;
import me.whereareiam.socialismus.api.input.event.base.EventOrder;

import java.lang.reflect.Method;

public interface EventManager {
    void register(EventListener eventListener);

    <T extends Event> void registerListener(Class<T> event, Object listener, Method method, EventOrder order);

    void unregister(EventListener eventListener);

    void call(Event event);
}
