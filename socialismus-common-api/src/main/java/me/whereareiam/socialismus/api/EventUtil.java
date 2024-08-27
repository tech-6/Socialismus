package me.whereareiam.socialismus.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.event.EventManager;
import me.whereareiam.socialismus.api.input.event.base.CancellableEvent;
import me.whereareiam.socialismus.api.input.event.base.Event;

@Singleton
public class EventUtil {
    private static EventManager eventManager;

    @Inject
    public EventUtil(EventManager eventManager) {
        EventUtil.eventManager = eventManager;
    }

    public static boolean callEvent(Event event, Runnable callback) {
        EventUtil.eventManager.call(event);

        if (event instanceof CancellableEvent cancellableEvent && cancellableEvent.isCancelled())
            return false;

        callback.run();
        return true;
    }
}
