package me.whereareiam.socialismus.feature.chat.requirement;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.feature.chat.Chat;
import org.bukkit.entity.Player;

@Singleton
public class WorldRequirement implements ChatRequirement {
    @Override
    public boolean checkRequirement(Player player, Chat chat) {
        return chat.worlds == null
                || chat.worlds.isEmpty()
                || chat.worlds.contains(player.getWorld().getName());
    }
}