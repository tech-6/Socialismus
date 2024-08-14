package me.whereareiam.socialismus.adapter.config.template;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.Settings;
import me.whereareiam.socialismus.api.output.DefaultConfig;
import me.whereareiam.socialismus.api.type.SerializationType;

@Singleton
public class SettingsTemplate implements DefaultConfig<Settings> {
    @Override
    public Settings getDefault() {
        Settings settings = new Settings();

        // Default values
        settings.setLevel(2);
        settings.setSerializer(SerializationType.MINIMESSAGE);

        Settings.Miscellaneous misc = new Settings.Miscellaneous();
        misc.setDisableJoinNotification(true);
        misc.setDisableQuitNotification(true);
        misc.setVanillaSending(true);
        misc.setAllowLegacyParsing(false);
        misc.setAllowBrigadierCommands(true);
        misc.setCommandsPerPage(7);

        settings.setMisc(misc);

        Settings.Updater updater = new Settings.Updater();
        updater.setCheckForUpdates(true);
        updater.setWarnAboutUpdates(true);
        updater.setWarnAboutDevBuilds(true);
        updater.setInterval(1);

        settings.setUpdater(updater);

        return settings;
    }
}
