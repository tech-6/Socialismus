package me.whereareiam.socialismus.platform.bukkit.listener;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.output.ListenerRegistrar;
import me.whereareiam.socialismus.platform.bukkit.listener.activity.PlayerWorldChangeListener;
import me.whereareiam.socialismus.platform.bukkit.listener.chat.PlayerChatListener;
import me.whereareiam.socialismus.platform.bukkit.listener.connection.PlayerJoinListener;
import me.whereareiam.socialismus.platform.bukkit.listener.connection.PlayerQuitListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.stream.Stream;

@Singleton
public class BukkitListenerRegistrar implements ListenerRegistrar {
    private final Injector injector;
    private final Plugin plugin;
    private final PluginManager pluginManager;

    @Inject
    public BukkitListenerRegistrar(Injector injector, Plugin plugin, PluginManager pluginManager) {
        this.injector = injector;
        this.plugin = plugin;
        this.pluginManager = pluginManager;
    }

    @Override
    public void registerListeners() {
        Stream.of(
                injector.getInstance(PlayerChatListener.class),
                injector.getInstance(PlayerWorldChangeListener.class),
                injector.getInstance(PlayerQuitListener.class),
                injector.getInstance(PlayerJoinListener.class)
        ).forEach(this::registerListener);
    }

    @Override
    public void registerListener(Object listener) {
        pluginManager.registerEvents((Listener) listener, plugin);
    }
}
