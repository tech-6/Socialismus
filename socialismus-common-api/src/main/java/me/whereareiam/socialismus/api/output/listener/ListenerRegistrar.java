package me.whereareiam.socialismus.api.output.listener;

public interface ListenerRegistrar {
    void registerListeners();

    <T> void registerListener(Class<T> eventClass, DynamicListener<T> listener);
}
