package me.whereareiam.socialismus;

import me.whereareiam.socialismus.platform.PlatformMessageSender;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class PaperMessageSender extends PlatformMessageSender {
    @Override
    public void sendMessage(Player player, Component message) {
        player.sendMessage(message);
    }
}


