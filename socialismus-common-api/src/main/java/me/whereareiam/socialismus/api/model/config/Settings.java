package me.whereareiam.socialismus.api.model.config;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.whereareiam.socialismus.api.type.SerializationType;

@Getter
@Setter
@ToString
@Singleton
public class Settings {
    private int level;
    private SerializationType serializer;

    private Updater updater;
    private Miscellaneous misc;

    @Getter
    @Setter
    @ToString
    public static class Updater {
        private boolean checkForUpdates;
        private boolean warnAboutUpdates;
        private boolean warnAboutDevBuilds;
        private int interval;
    }

    @Getter
    @Setter
    @ToString
    public static class Miscellaneous {
        private boolean vanillaSending;
        private boolean allowLegacyParsing;
        private boolean allowBrigadierCommands;
        private int commandsPerPage;
    }
}
