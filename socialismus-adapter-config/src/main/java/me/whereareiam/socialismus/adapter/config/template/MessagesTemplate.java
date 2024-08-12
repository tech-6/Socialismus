package me.whereareiam.socialismus.adapter.config.template;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.model.config.message.CommandMessages;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.output.DefaultConfig;

import java.util.List;
import java.util.Map;

@Singleton
public class MessagesTemplate implements DefaultConfig<Messages> {
    @Override
    public Messages getDefault() {
        Messages messages = new Messages();

        // Default values
        messages.setPrefix("<gold>ꜱᴏᴄɪᴀʟɪꜱᴍᴜꜱ <dark_gray>| ");
        CommandMessages commandMessages = new CommandMessages();
        commandMessages.setNoPermission("{prefix}<white>You don't have \"<gray>{content}</gray>\" permission to use this command.</white>");
        commandMessages.setInvalidSyntax("{prefix}<white>Invalid syntax, please use:</white> <yellow>{content}</yellow>");
        commandMessages.setExecutionError("{prefix}<white>An error occurred while executing the command:</white> <gray>{content}</gray>");

        commandMessages.setInvalidSyntaxBoolean("{prefix}<white>You tried to use <gray>{content}</gray> as a boolean, but it's not a valid value, please use <green>true</green> or <red>false</red>.</white>");
        commandMessages.setInvalidSyntaxNumber("{prefix}<white>You tried to use <gray>{content}</gray> as a number, but it's not a valid value, please use a valid number.</white>");
        commandMessages.setInvalidSyntaxString("{prefix}<white>You tried to use <gray>{content}</gray> as a string, but it's not a valid value, please use a valid string.</white>");

        commandMessages.setArguments(Map.of(
                "id", "ID",
                "bool", "true/false",
                "page", "Page"
        ));

        CommandMessages.Format format = new CommandMessages.Format();
        format.setFormat("<gray>{command}</gray>");
        format.setArgument("<gray>[{argument}]</gray>");
        format.setOptionalArgument("<gray>({argument})</gray>");

        commandMessages.setFormat(format);

        CommandMessages.Pagination pagination = new CommandMessages.Pagination();
        pagination.setShowPaginationIfOnePage(false);
        pagination.setFormat("\n {previous}<white>Pagination</white> <gray>[{current}/{max}]</gray>{next} \n");
        pagination.setShowPreviousEvenIfFirst(false);
        pagination.setPreviousTagFormat("<red><click:run_command:social help {previousPage}>«</red> ");
        pagination.setShowNextEvenIfLast(false);
        pagination.setNextTagFormat(" <green><click:run_command:social help {nextPage}>»</green>");

        commandMessages.setPagination(pagination);

        CommandMessages.HelpCommand helpCommand = new CommandMessages.HelpCommand();
        helpCommand.setFormat(List.of(" ", "<gold><bold> Socialismus</bold> <white>CommandEntity help", " ", "{commands}", "{pagination}"));
        helpCommand.setCommandFormat(" <yellow>/{command}{arguments}</yellow> <dark_gray>- <white>{description}");
        helpCommand.setNoCommands("  <red>No commands found</red>");

        commandMessages.setHelpCommand(helpCommand);

        CommandMessages.DebugCommand debugCommand = new CommandMessages.DebugCommand();
        debugCommand.setFormat(List.of(
                " ",
                "<gold><bold> Socialismus</bold> <white>Debug",
                "<white>  Server version: <gray>{serverVersion}</gray>",
                "<white>  Plugin version: <gray>{pluginVersion}</gray>",
                " ",
                "<white>  Server platform: <aqua>{serverPlatform}</aqua>",
                "<white>  Plugin platform: <aqua>{pluginPlatform}</aqua>",
                " ",
                "<white>  Java version: <yellow>{javaVersion}</yellow>",
                "<white>  Operating system: <yellow>{os}</yellow>",
                " "
        ));

        commandMessages.setDebugCommand(debugCommand);

        CommandMessages.ReloadCommand reloadCommand = new CommandMessages.ReloadCommand();
        reloadCommand.setReloading("{prefix}<white>Reloading configuration, some features may still require a server restart...</white>");
        reloadCommand.setReloaded("{prefix}<white>Configuration reloaded <green>successfully</green>!</white>");
        reloadCommand.setException("{prefix}<white>An error occurred while reloading the configuration:</white> <gray>{exception}</gray>");

        commandMessages.setReloadCommand(reloadCommand);

        messages.setCommands(commandMessages);

        return messages;
    }
}
