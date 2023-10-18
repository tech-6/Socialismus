package me.whereareiam.socialismus.integration.protocollib.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.inject.Singleton;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.UUID;

@Singleton
public class PacketSilverfish {
    public PacketContainer createPacketSilverfishEntity(int id, Location location) {

        return new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY) {{
            getModifier().writeDefaults();
            getIntegers().write(0, id);

            getEntityTypeModifier().write(0, EntityType.SILVERFISH);

            getDoubles().write(0, location.getX());
            getDoubles().write(1, location.getY());
            getDoubles().write(2, location.getZ());
            getUUIDs().write(0, UUID.randomUUID());
        }};
    }
}
