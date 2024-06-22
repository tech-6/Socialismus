package me.whereareiam.socialismus.api.output.platform;

import me.whereareiam.socialismus.api.model.DummyPlayer;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

public interface PlatformMessenger {
	void sendMessage(DummyPlayer dummyPlayer, Component component);

	void sendActionBar(DummyPlayer dummyPlayer, Component component);

	void sendBossBar(DummyPlayer dummyPlayer, BossBar bossBar);

	void sendTitle(DummyPlayer dummyPlayer, Title title);

	void broadcastMessage(Component component);
}
