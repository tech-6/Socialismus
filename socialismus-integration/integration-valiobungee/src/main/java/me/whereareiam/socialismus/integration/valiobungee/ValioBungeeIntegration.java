package me.whereareiam.socialismus.integration.valiobungee;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.imaginarycode.minecraft.redisbungee.RedisBungeeAPI;
import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.output.integration.Integration;
import me.whereareiam.socialismus.api.output.integration.SynchronizationIntegration;
import me.whereareiam.socialismus.api.output.listener.ListenerRegistrar;

import java.util.Optional;
import java.util.UUID;

@Singleton
public class ValioBungeeIntegration implements SynchronizationIntegration {
    @Inject
    public ValioBungeeIntegration(Registry<Integration> registry, Injector injector) {
        if (!isAvailable()) return;

        injector.getInstance(ListenerRegistrar.class).registerListener(
                PubSubMessageEvent.class,
                injector.getInstance(PubSubMessageListener.class)
        );

        registry.register(this);
    }

    @Override
    public String getName() {
        return "ValioBungee";
    }

    @Override
    public boolean isAvailable() {
        try {
            Class.forName("com.imaginarycode.minecraft.redisbungee.RedisBungeeAPI");

            return true;
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            return false;
        }
    }

    @Override
    public void sync(String channel, String content) {
        RedisBungeeAPI.getRedisBungeeApi().sendChannelMessage(channel, content);
    }

    @Override
    public Optional<String> getLocation(UUID uniqueId) {
        return Optional.ofNullable(RedisBungeeAPI.getRedisBungeeApi().getServerNameFor(uniqueId));
    }
}
