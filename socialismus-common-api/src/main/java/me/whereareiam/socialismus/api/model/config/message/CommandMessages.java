package me.whereareiam.socialismus.api.model.config.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CommandMessages {
    private String noPermission;
    private String executionError;
    private String invalidSyntax;

    private String invalidSyntaxBoolean;
    private String invalidSyntaxNumber;
    private String invalidSyntaxString;

    private Format format;
    private Pagination pagination;
    private HelpCommand helpCommand;
    private DebugCommand debugCommand;
    private ReloadCommand reloadCommand;

    @Getter
    @Setter
    @ToString
    public static class Format {
        private String format;
        private String argument;
        private String optionalArgument;
    }

    @Getter
    @Setter
    @ToString
    public static class Pagination {
        private boolean showPaginationIfOnePage;
        private String format;
        private boolean showPreviousEvenIfFirst;
        private String previousTagFormat;
        private boolean showNextEvenIfLast;
        private String nextTagFormat;
    }

    @Getter
    @Setter
    @ToString
    public static class HelpCommand {
        private List<String> format;
        private String commandFormat;
    }

    @Getter
    @Setter
    @ToString
    public static class DebugCommand {
        private List<String> format;
    }

    @Getter
    @Setter
    @ToString
    public static class ReloadCommand {
        private String reloading;
        private String reloaded;
        private String exception;
    }
}
