package me.whereareiam.socialismus.api.output.integration;

import java.util.Optional;
import java.util.UUID;

public interface SynchronizationIntegration extends Integration {
    void sync(String channel, String content);

    Optional<String> getLocation(UUID uniqueId);
}
