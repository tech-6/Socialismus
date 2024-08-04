package me.whereareiam.socialismus.integration.bstats.chart;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.output.PlatformInteractor;
import org.bstats.charts.CustomChart;
import org.bstats.charts.SimplePie;

@Singleton
public class ServerVersionChart implements Chart {
    private final PlatformInteractor interactor;

    @Inject
    public ServerVersionChart(PlatformInteractor interactor) {
        this.interactor = interactor;
    }

    public CustomChart getChart() {
        return new SimplePie("serverVersion", this::getData);
    }

    private String getData() {
        return interactor.getServerVersion().name();
    }
}
