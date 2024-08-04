package me.whereareiam.socialismus.platform.velocity.listener;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.EventManager;
import me.whereareiam.socialismus.api.output.ListenerRegistrar;
import me.whereareiam.socialismus.platform.velocity.VelocitySocialismus;
import me.whereareiam.socialismus.platform.velocity.listener.chat.PlayerChatListener;

import java.util.stream.Stream;

@Singleton
public class VelocityListenerRegistrar implements ListenerRegistrar {
    private final Injector injector;
    private final VelocitySocialismus plugin;
    private final EventManager eventManager;

    @Inject
    public VelocityListenerRegistrar(Injector injector, VelocitySocialismus plugin, EventManager eventManager) {
        this.injector = injector;
        this.plugin = plugin;
        this.eventManager = eventManager;
    }

    @Override
    public void registerListeners() {
        Stream.of(
                injector.getInstance(PlayerChatListener.class)
        ).forEach(this::registerListener);
    }

    @Override
    public void registerListener(Object listener) {
        eventManager.register(plugin, listener);
    }
}
