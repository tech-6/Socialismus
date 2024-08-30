package me.whereareiam.socialismus.common.event;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.event.EventListener;
import me.whereareiam.socialismus.api.input.event.EventManager;
import me.whereareiam.socialismus.api.input.event.base.CancellableEvent;
import me.whereareiam.socialismus.api.input.event.base.Event;
import me.whereareiam.socialismus.api.input.event.base.EventOrder;
import me.whereareiam.socialismus.api.input.event.base.SocialisticEvent;
import me.whereareiam.socialismus.api.output.LoggingHelper;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class EventController implements EventManager {
    private final LoggingHelper loggingHelper;

    private final Map<Class<?>, List<RegisteredListener>> listeners = new HashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Inject
    public EventController(LoggingHelper loggingHelper) {
        this.loggingHelper = loggingHelper;
    }

    @Override
    public void register(EventListener listener) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(SocialisticEvent.class)) {
                Class<?> eventType = method.getParameterTypes()[0];
                EventOrder order = method.getAnnotation(SocialisticEvent.class).value();

                listeners.computeIfAbsent(eventType, k -> new ArrayList<>())
                        .add(new RegisteredListener(listener, method, order));
                listeners.get(eventType).sort(Comparator.comparing(RegisteredListener::getOrder));
            }
        }
    }

    @Override
    public <T extends Event> void registerListener(Class<T> event, Object listener, Method method, EventOrder order) {
        listeners.computeIfAbsent(event, k -> new ArrayList<>())
                .add(new RegisteredListener((EventListener) listener, method, order));
        listeners.get(event).sort(Comparator.comparing(RegisteredListener::getOrder));
    }

    @Override
    public void unregister(EventListener eventListener) {
        listeners.values().forEach(list -> list.removeIf(listener -> listener.getListener().equals(eventListener)));
    }

    @Override
    public void call(Event event) {
        List<RegisteredListener> eventListeners = listeners.get(event.getClass());
        if (eventListeners == null) return;

        for (RegisteredListener listener : eventListeners) {
            executor.submit(() -> {
                try {
                    listener.getMethod().invoke(listener.getListener(), event);
                } catch (Exception e) {
                    loggingHelper.severe("Failed to call event " + event.getClass().getSimpleName() + " for listener " + listener.getListener().getClass().getSimpleName());
                }
            });

            if (event instanceof CancellableEvent && ((CancellableEvent) event).isCancelled()) {
                loggingHelper.debug("Event " + event.getClass().getSimpleName() + " was cancelled");
                break;
            }
        }
    }
}
