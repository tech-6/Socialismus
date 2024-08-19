package me.whereareiam.socialismus.common;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.PluginInteractor;

@Singleton
public class CommonPluginInteractor implements PluginInteractor {
    @Override
    public String getPluginVersion() {
        return Constants.VERSION;
    }
}
