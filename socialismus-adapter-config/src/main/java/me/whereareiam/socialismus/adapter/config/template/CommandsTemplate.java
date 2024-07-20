package me.whereareiam.socialismus.adapter.config.template;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.command.Command;
import me.whereareiam.socialismus.api.model.config.command.Commands;
import me.whereareiam.socialismus.api.output.DefaultConfig;

import java.util.List;

@Singleton
public class CommandsTemplate implements DefaultConfig<Commands> {
    @Override
    public Commands getDefault() {
        Commands commands = new Commands();

        // Default values
        Command main = new Command(
                List.of("socialismus", "social"),
                "",
                "Main command",
                "{command}",
                true
        );

        Command help = new Command(
                List.of("help"),
                "",
                "Help command",
                "{command} [page]",
                true
        );

        Command debug = new Command(
                List.of("debug"),
                "socialismus.admin",
                "Debug command",
                "{command}",
                true
        );

        Command reload = new Command(
                List.of("reload"),
                "socialismus.admin",
                "Reload command",
                "{command}",
                true
        );

        commands.getCommands().put("main", main);
        commands.getCommands().put("help", help);
        commands.getCommands().put("debug", debug);
        commands.getCommands().put("reload", reload);

        return commands;
    }
}
