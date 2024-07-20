package me.whereareiam.socialismus.api.input.registry;

public interface Registry<T> {
    void register(T integration);
}