package me.whereareiam.socialismus.integration.valiobungee;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.output.integration.Integration;

@Singleton
public class ValioBungeeIntegration implements Integration {
    private final Registry<Integration> registry;

    public ValioBungeeIntegration(Registry<Integration> registry) {
        this.registry = registry;
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
    public void register() {
        if (!isAvailable()) return;

        registry.register(this);
    }
}
