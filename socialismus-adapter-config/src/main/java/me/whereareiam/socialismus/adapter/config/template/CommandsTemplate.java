package me.whereareiam.socialismus.adapter.config.template;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.CommandEntity;
import me.whereareiam.socialismus.api.model.config.Commands;
import me.whereareiam.socialismus.api.output.DefaultConfig;

import java.util.List;

@Singleton
public class CommandsTemplate implements DefaultConfig<Commands> {
    @Override
    public Commands getDefault() {
        Commands commands = new Commands();

        // Default values
        CommandEntity main = CommandEntity.builder()
                .aliases(List.of("socialismus", "social"))
                .permission("")
                .description("Main command")
                .usage("{alias}")
                .enabled(true)
                .build();

        CommandEntity help = CommandEntity.builder()
                .aliases(List.of("help"))
                .permission("")
                .description("Help command")
                .usage("{command} {alias} [page]")
                .enabled(true)
                .build();

        CommandEntity debug = CommandEntity.builder()
                .aliases(List.of("debug"))
                .permission("socialismus.admin")
                .description("Debug command")
                .usage("{command} {alias}")
                .enabled(true)
                .build();

        CommandEntity reload = CommandEntity.builder()
                .aliases(List.of("reload"))
                .permission("socialismus.admin")
                .description("Reload command")
                .usage("{command} {alias}")
                .enabled(true)
                .build();

        CommandEntity clear = CommandEntity.builder()
                .aliases(List.of("clear", "clearchat"))
                .permission("socialismus.admin")
                .description("Clear command")
                .usage("{command} {alias} [context]")
                .enabled(true)
                .build();

        commands.getCommands().put("main", main);
        commands.getCommands().put("help", help);
        commands.getCommands().put("debug", debug);
        commands.getCommands().put("reload", reload);
        commands.getCommands().put("clear", clear);

        return commands;
    }
}
