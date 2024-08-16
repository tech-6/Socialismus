package me.whereareiam.socialismus.integration.bstats.chart;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.output.config.ConfigurationManager;
import org.bstats.charts.CustomChart;
import org.bstats.charts.SimplePie;

@Singleton
public class ConfigTypeChart implements Chart {
    private final ConfigurationManager configManager;

    @Inject
    public ConfigTypeChart(ConfigurationManager configManager) {
        this.configManager = configManager;
    }

    public CustomChart getChart() {
        return new SimplePie("configType", this::getData);
    }

    private String getData() {
        return configManager.getConfigurationType().name();
    }
}
