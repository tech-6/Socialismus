package me.whereareiam.socialismus.command;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.output.command.CommandService;
import me.whereareiam.socialismus.command.management.CommandProcessor;

public class CommandModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CommandService.class).to(CommandProcessor.class);
    }
}
