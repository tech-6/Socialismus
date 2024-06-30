package me.whereareiam.socialismus.adapter.config.template;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.MiscellaneousSettings;
import me.whereareiam.socialismus.api.model.config.Settings;
import me.whereareiam.socialismus.api.output.DefaultConfig;
import me.whereareiam.socialismus.api.type.SerializationType;

@Singleton
public class SettingsTemplate implements DefaultConfig<Settings> {
	@Override
	public Settings getDefault() {
		Settings settings = new Settings();

		// Default values
		settings.setLevel(2);
		settings.setSerializer(SerializationType.MINIMESSAGE);

		MiscellaneousSettings misc = new MiscellaneousSettings();
		misc.setAllowLegacyParsing(false);
		misc.setAllowBrigadierCommands(true);

		settings.setMisc(misc);

		return settings;
	}
}
