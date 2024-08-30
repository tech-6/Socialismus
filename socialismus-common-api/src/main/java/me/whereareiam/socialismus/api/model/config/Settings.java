package me.whereareiam.socialismus.api.model.config;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.whereareiam.socialismus.api.model.Event;
import me.whereareiam.socialismus.api.type.SerializationType;

import java.util.Map;

@Getter
@Setter
@ToString
@Singleton
public class Settings {
    private int level;
    private SerializationType serializer;

    private Updater updater;
    private Miscellaneous misc;
    private Listeners listeners;

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
        private boolean disableJoinNotification;
        private boolean disableQuitNotification;
        private boolean allowLegacyParsing;
        private boolean allowBrigadierCommands;
        private boolean vanillaSending;
        private int commandsPerPage;
    }

    @Getter
    @Setter
    @ToString
    public static class Listeners {
        private Map<String, Event> events;
    }
}
