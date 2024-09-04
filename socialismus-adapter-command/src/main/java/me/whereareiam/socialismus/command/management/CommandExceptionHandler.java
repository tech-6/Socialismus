package me.whereareiam.socialismus.command.management;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.input.serializer.SerializationService;
import me.whereareiam.socialismus.api.model.config.message.CommandMessages;
import me.whereareiam.socialismus.api.model.config.message.Messages;
import me.whereareiam.socialismus.api.model.player.DummyPlayer;
import me.whereareiam.socialismus.api.model.serializer.SerializerContent;
import me.whereareiam.socialismus.api.model.serializer.SerializerPlaceholder;
import me.whereareiam.socialismus.api.output.LoggingHelper;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.exception.*;
import org.incendo.cloud.exception.handling.ExceptionContext;
import org.incendo.cloud.exception.parsing.NumberParseException;
import org.incendo.cloud.minecraft.extras.caption.ComponentCaptionFormatter;
import org.incendo.cloud.parser.standard.BooleanParser;
import org.incendo.cloud.parser.standard.StringParser;

import java.util.List;

@Singleton
public class CommandExceptionHandler {
    private final LoggingHelper loggingHelper;
    private final SerializationService serializer;
    private final Provider<Messages> messages;

    @Inject
    public CommandExceptionHandler(LoggingHelper loggingHelper, SerializationService serializer, Provider<Messages> messages) {
        this.loggingHelper = loggingHelper;
        this.serializer = serializer;
        this.messages = messages;
    }

    public Component handleParseException(ComponentCaptionFormatter<DummyPlayer> formatter, ExceptionContext<DummyPlayer, ArgumentParseException> exception) {
        DummyPlayer player = exception.context().sender();
        CommandMessages commandMessages = messages.get().getCommands();

        if (exception.exception().getCause() instanceof BooleanParser.BooleanParseException e)
            return getFormattedMessage(player, commandMessages.getInvalidSyntaxBoolean(), e.input());

        if (exception.exception().getCause() instanceof NumberParseException e)
            return getFormattedMessage(player, commandMessages.getInvalidSyntaxNumber(), e.input());

        if (exception.exception().getCause() instanceof StringParser.StringParseException e)
            return getFormattedMessage(player, commandMessages.getInvalidSyntaxString(), e.input());

        return Component.text("Unknown parse exception occurred" + exception.exception().getCause().getMessage());
    }

    public Component handleInvalidCommandSenderException(ComponentCaptionFormatter<DummyPlayer> formatter, ExceptionContext<DummyPlayer, InvalidCommandSenderException> exception) {
        return Component.text("Unknown sender exception occurred");
    }

    public Component handleNoPermissionException(ComponentCaptionFormatter<DummyPlayer> formatter, ExceptionContext<DummyPlayer, NoPermissionException> exception) {
        return getFormattedMessage(exception.context().sender(), messages.get().getCommands().getNoPermission(), exception.exception().missingPermission().permissionString());
    }

    public Component handleInvalidSyntaxException(ComponentCaptionFormatter<DummyPlayer> formatter, ExceptionContext<DummyPlayer, InvalidSyntaxException> exception) {
        return getFormattedMessage(exception.context().sender(), messages.get().getCommands().getInvalidSyntax(), exception.exception().correctSyntax().replace("|", "/"));
    }

    public Component handleCommandExecutionException(ComponentCaptionFormatter<DummyPlayer> formatter, ExceptionContext<DummyPlayer, CommandExecutionException> exception) {
        exception.exception().printStackTrace();
        return getFormattedMessage(exception.context().sender(), messages.get().getCommands().getExecutionError(), exception.exception().getMessage());
    }

    private Component getFormattedMessage(DummyPlayer dummyPlayer, String message, String content) {
        return serializer.format(new SerializerContent(
                dummyPlayer,
                List.of(
                        new SerializerPlaceholder(
                                "{content}",
                                content
                        )
                ),
                message
        ));
    }
}
