package me.whereareiam.socialismus.common.base;

import com.google.inject.Injector;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import me.whereareiam.socialismus.common.CommonInjector;
import me.whereareiam.socialismus.common.Constants;
import me.whereareiam.socialismus.common.util.type.platform.PlatformType;
import me.whereareiam.socialismus.common.util.type.plugin.PluginType;

import java.util.ArrayList;
import java.util.List;

public class SocialismusBase {
	private Injector injector;

	public void onEnable() {
		injector = CommonInjector.getInjector();

		printWelcomeMessage();
	}

	public void onDisable() {

	}

	private void printWelcomeMessage() {
		LoggingHelper loggingHelper = injector.getInstance(LoggingHelper.class);
		List<String> content = new ArrayList<>();

		content.add(" ");
		content.add("  █▀ █▀▀   Socialismus v" + Constants.getVersion());
		content.add("  ▄█ █▄▄   Platform: "
				+ PlatformType.getType().toString()
				+ " [" + PluginType.getType().toString() + "]"
		);
		content.add(" ");

		content.forEach(loggingHelper::info);
	}
}
