package me.whereareiam.socialismus.platform.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.integration.bstats.Metrics;
import me.whereareiam.socialismus.integration.bstats.chart.*;

import java.util.stream.Stream;

@Singleton
public class VelocityMetrics implements Metrics {
    private final org.bstats.velocity.Metrics metrics;
    private final Injector injector;

    @Inject
    public VelocityMetrics(org.bstats.velocity.Metrics metrics, Injector injector) {
        this.metrics = metrics;
        this.injector = injector;
    }

    @Override
    public void register() {
        Stream.of(
                injector.getInstance(PluginTypeChart.class),
                injector.getInstance(PluginVersionChart.class),
                injector.getInstance(ChatCountChart.class),
                injector.getInstance(ModulesChart.class),
                injector.getInstance(IntegrationsChart.class),
                injector.getInstance(ConfigTypeChart.class)
        ).map(Chart::getChart).forEach(metrics::addCustomChart);
    }
}