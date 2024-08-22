package me.whereareiam.socialismus.api.input.event.base;

public interface CancellableEvent {
    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
