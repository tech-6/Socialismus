package me.whereareiam.socialismus.platform.paper;

import com.google.inject.Inject;
import lombok.Setter;
import me.whereareiam.socialismus.api.model.config.settings.Settings;
import me.whereareiam.socialismus.api.output.LoggingHelper;

import java.util.logging.Logger;

public class PaperLoggingHelper implements LoggingHelper {
	@Setter
	private static Logger logger;
	private final Settings settings;

	@Inject
	public PaperLoggingHelper(Settings settings) {
		this.settings = settings;
	}

	@Override
	public void info(String message, Object... objects) {
		if (settings.getLevel() >= 2)
			logger.info(String.format(message, objects));
	}

	@Override
	public void warn(String message, Object... objects) {
		if (settings.getLevel() >= 1)
			logger.warning(String.format(message, objects));
	}

	@Override
	public void severe(String message, Object... objects) {
		if (settings.getLevel() >= 0)
			logger.severe(String.format(message, objects));
	}

	@Override
	public void debug(String message, Object... objects) {
		if (settings.getLevel() >= 3)
			logger.info(String.format(message, objects));
	}
}
