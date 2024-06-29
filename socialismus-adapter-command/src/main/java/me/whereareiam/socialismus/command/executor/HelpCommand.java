package me.whereareiam.socialismus.command.executor;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import org.incendo.cloud.annotations.Command;

@Singleton
@Command("%command.main")
public class HelpCommand implements CommandBase {
	@Command("%command.help")
	public void onCommand(DummyPlayer dummyPlayer) {
		System.out.println(dummyPlayer);
		System.out.println("HelpCommand executed");
	}
}