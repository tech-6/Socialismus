package me.whereareiam.socialismus.command;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.command.management.CommandProcessor;

public class CommandModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(CommandProcessor.class).asEagerSingleton();
	}
}
