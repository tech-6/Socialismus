package me.whereareiam.socialismus.integration.bstats;

import com.google.inject.ConfigurationException;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.output.integration.Integration;

@Singleton
public class bStatsIntegration implements Integration {
    private final Injector injector;
    private boolean registered;

    @Inject
    public bStatsIntegration(Injector injector, Registry<Integration> registry) {
        this.injector = injector;

        registry.register(this);
    }

    @Override
    public String getName() {
        return "bStats";
    }

    @Override
    public boolean isAvailable() {
        try {
            Metrics metrics = injector.getInstance(Metrics.class);

            if (!registered) {
                metrics.register();
                registered = true;
            }

            return true;
        } catch (ConfigurationException e) {
            return false;
        }
    }
}
