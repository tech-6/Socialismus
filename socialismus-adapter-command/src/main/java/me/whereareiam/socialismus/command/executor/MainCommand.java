package me.whereareiam.socialismus.command.executor;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

@Singleton
public class MainCommand implements CommandBase {
	@Command("%command.main")
	@CommandDescription("%description.main")
	@Permission("%permission.main")
	public void onCommand(DummyPlayer dummyPlayer) {
		System.out.println("Hello, World!");
	}
}
