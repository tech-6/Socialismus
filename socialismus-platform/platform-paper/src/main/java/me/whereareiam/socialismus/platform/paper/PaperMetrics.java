package me.whereareiam.socialismus.platform.paper;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.integration.bstats.Metrics;
import me.whereareiam.socialismus.integration.bstats.chart.*;

import java.util.stream.Stream;

@Singleton
public class PaperMetrics implements Metrics {
    private final org.bstats.bukkit.Metrics metrics;
    private final Injector injector;

    @Inject
    public PaperMetrics(org.bstats.bukkit.Metrics metrics, Injector injector) {
        this.metrics = metrics;
        this.injector = injector;
    }

    @Override
    public void register() {
        Stream.of(
                injector.getInstance(PlatformTypeChart.class),
                injector.getInstance(PluginTypeChart.class),
                injector.getInstance(PluginVersionChart.class),
                injector.getInstance(ServerVersionChart.class),
                injector.getInstance(ChatCountChart.class),
                injector.getInstance(ModulesChart.class),
                injector.getInstance(IntegrationsChart.class)

        ).map(Chart::getChart).forEach(metrics::addCustomChart);
    }
}
