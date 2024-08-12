package me.whereareiam.socialismus.api.output.integration;

public interface Integration {
    String getName();

    boolean isAvailable();

    void register();
}
