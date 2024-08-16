package me.whereareiam.socialismus.adapter.config.template;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.PlatformType;
import me.whereareiam.socialismus.api.model.config.Settings;
import me.whereareiam.socialismus.api.output.DefaultConfig;
import me.whereareiam.socialismus.api.type.EventPriority;
import me.whereareiam.socialismus.api.type.SerializationType;

import java.util.HashMap;
import java.util.Map;

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

        Settings.Listeners listeners = new Settings.Listeners();
        if (PlatformType.isProxy())
            listeners.setPriorities(getPrioritiesForProxy());

        if (PlatformType.isAtLeast(PlatformType.PAPER))
            listeners.setPriorities(getPrioritiesForPaper());
        else listeners.setPriorities(getPrioritiesForBukkit());

        settings.setListeners(listeners);

        return settings;
    }

    private Map<String, EventPriority> getPrioritiesForBukkit() {
        Map<String, EventPriority> priorities = new HashMap<>();

        priorities.put("org.bukkit.event.player.PlayerJoinEvent", EventPriority.LOWEST);
        priorities.put("org.bukkit.event.player.PlayerQuitEvent", EventPriority.LOWEST);
        priorities.put("org.bukkit.event.player.PlayerChangedWorldEvent", EventPriority.LOWEST);
        priorities.put("org.bukkit.event.player.AsyncPlayerChatEvent", EventPriority.LOWEST);

        return priorities;
    }

    private Map<String, EventPriority> getPrioritiesForPaper() {
        Map<String, EventPriority> priorities = new HashMap<>();

        priorities.put("org.bukkit.event.player.PlayerJoinEvent", EventPriority.LOWEST);
        priorities.put("org.bukkit.event.player.PlayerQuitEvent", EventPriority.LOWEST);
        priorities.put("org.bukkit.event.player.PlayerChangedWorldEvent", EventPriority.LOWEST);
        priorities.put("io.papermc.paper.event.player.AsyncChatEvent", EventPriority.LOWEST);

        return priorities;
    }

    private Map<String, EventPriority> getPrioritiesForProxy() {
        Map<String, EventPriority> priorities = new HashMap<>();

        priorities.put("com.velocitypowered.api.event.player.ServerConnectedEvent", EventPriority.LOWEST);
        priorities.put("com.velocitypowered.api.event.connection.LoginEvent", EventPriority.LOWEST);
        priorities.put("com.velocitypowered.api.event.connection.DisconnectEvent", EventPriority.LOWEST);
        priorities.put("com.velocitypowered.api.event.player.PlayerChatEvent", EventPriority.LOWEST);

        return priorities;
    }
}
