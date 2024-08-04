package me.whereareiam.socialismus.common;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.output.integration.Integration;

import java.util.Set;

@Singleton
public class IntegrationInitializer {
    @Inject
    public IntegrationInitializer(Set<Integration> integrations) {
        integrations.forEach(Integration::isAvailable);
    }
}
