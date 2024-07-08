package me.whereareiam.socialismus.api.input;

public interface Registry<T> {
    void register(T integration);
}