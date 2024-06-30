package me.whereareiam.socialismus.command.executor;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import org.incendo.cloud.annotation.specifier.Range;
import org.incendo.cloud.annotations.*;

@Singleton
@Command("%command.main")
public class HelpCommand implements CommandBase {
	@Command("%command.help")
	@CommandDescription("%description.help")
	@Permission("%permission.help")
	public void onCommand(DummyPlayer dummyPlayer, @Range(min = "1") @Default("1") @Argument(value = "page", description = "%argument.expected-number") int page) {
		
	}
}