package me.whereareiam.socialismus.command;

import com.google.inject.AbstractModule;

public class CommandModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(CommandProcessor.class).asEagerSingleton();
	}
}
