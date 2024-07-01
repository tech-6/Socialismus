package me.whereareiam.socialismus.adapter.config.template;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.message.CommandMessages;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.output.DefaultConfig;

@Singleton
public class MessagesTemplate implements DefaultConfig<Messages> {
	@Override
	public Messages getDefault() {
		Messages messages = new Messages();

		// Default values
		CommandMessages commandMessages = new CommandMessages();
		commandMessages.setNoPermission("<gold>ꜱᴏᴄɪᴀʟɪꜱᴍᴜꜱ <dark_gray>| <white>You don't have \"<gray>{content}</gray>\" permission to use this command.</white>");
		commandMessages.setInvalidSyntax("<gold>ꜱᴏᴄɪᴀʟɪꜱᴍᴜꜱ <dark_gray>| <white>Invalid syntax, please use:</white> <yellow>{content}</yellow>");
		commandMessages.setExecutionError("<gold>ꜱᴏᴄɪᴀʟɪꜱᴍᴜꜱ <dark_gray>| <white>An error occurred while executing the command:</white> <gray>{content}</gray>");

		commandMessages.setInvalidSyntaxBoolean("<gold>ꜱᴏᴄɪᴀʟɪꜱᴍᴜꜱ <dark_gray>| <white>You tried to use <gray>{content}</gray> as a boolean, but it's not a valid value, please use <green>true</green> or <red>false</red>.</white>");
		commandMessages.setInvalidSyntaxNumber("<gold>ꜱᴏᴄɪᴀʟɪꜱᴍᴜꜱ <dark_gray>| <white>You tried to use <gray>{content}</gray> as a number, but it's not a valid value, please use a valid number.</white>");
		commandMessages.setInvalidSyntaxString("<gold>ꜱᴏᴄɪᴀʟɪꜱᴍᴜꜱ <dark_gray>| <white>You tried to use <gray>{content}</gray> as a string, but it's not a valid value, please use a valid string.</white>");

		messages.setCommands(commandMessages);

		return messages;
	}
}
