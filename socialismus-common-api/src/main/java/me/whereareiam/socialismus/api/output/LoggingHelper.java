package me.whereareiam.socialismus.api.output;

@SuppressWarnings("unused")
public interface LoggingHelper {
	void info(String message, Object... objects);

	void warn(String message, Object... objects);

	void severe(String message, Object... objects);

	void debug(String message, Object... objects);
}
