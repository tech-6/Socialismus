package me.whereareiam.socialismus.api.output.listener;

public interface DynamicListener<T> {
    void onEvent(T event);
}
