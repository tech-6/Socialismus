package me.whereareiam.socialismus.command.executor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.output.command.CommandBase;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

@Singleton
@Command("%command.main")
public class HelpCommand implements CommandBase {
	private final CommandManager<?> commandManager;

	@Inject
	public HelpCommand(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	@Command("%command.help")
	@CommandDescription("%description.help")
	@Permission("%permission.help")
	public void onCommand(DummyPlayer dummyPlayer, @Argument(value = "page") boolean page) {
		// print each command info
		/*@Range(min = "1") @Default("1") @Argument(value = "page", description = "%argument.expected-number") int page*/
		System.out.println("Commands:" + commandManager.commands().size());
		commandManager.commands().forEach(command -> {
			System.out.println(command.rootComponent().aliases());
			System.out.println(command.rootComponent().description());
			System.out.println(command.nonFlagArguments());
		});
	}
}