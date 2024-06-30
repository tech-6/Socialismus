package me.whereareiam.socialismus.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.command.Command;
import me.whereareiam.socialismus.api.model.config.command.Commands;
import org.incendo.cloud.annotations.string.PatternReplacingStringProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class DynamicCommandProvider {
	private static final Pattern COMMAND_PATTERN = Pattern.compile("%command\\.(.*)");
	private static final Pattern PERMISSION_PATTERN = Pattern.compile("%permission\\.(.*)");
	private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("%description\\.(.*)");
	private static final Pattern USAGE_PATTERN = Pattern.compile("%usage\\.(.*)");

	private final Commands commands;

	@Inject
	public DynamicCommandProvider(Commands commands) {
		this.commands = commands;
	}

	public PatternReplacingStringProcessor getProcessor() {
		Map<Pattern, Function<Command, String>> patternFunctionMap = new HashMap<>();

		patternFunctionMap.put(COMMAND_PATTERN, command -> command.getUsage().replace("{command}", String.join("|", command.getAliases())));
		patternFunctionMap.put(PERMISSION_PATTERN, Command::getPermission);
		patternFunctionMap.put(DESCRIPTION_PATTERN, Command::getDescription);
		patternFunctionMap.put(USAGE_PATTERN, Command::getUsage);

		Function<MatchResult, String> replacementFunction = matchResult -> {
			String input = matchResult.group(0);

			for (Map.Entry<Pattern, Function<Command, String>> entry : patternFunctionMap.entrySet()) {
				Matcher matcher = entry.getKey().matcher(input);
				if (matcher.find()) {
					String commandName = matcher.group(1);
					Command command = commands.getCommands().get(commandName);

					if (command != null) {
						String result = entry.getValue().apply(command);
						return result != null && !result.isEmpty() ? result : "";
					}
				}
			}

			return input;
		};

		return new PatternReplacingStringProcessor(Pattern.compile(".*"), replacementFunction);
	}
}
