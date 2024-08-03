package me.whereareiam.socialismus.api;

public enum AnsiColor {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    GRAY("\u001B[37;1m"),
    RED("\u001B[31m"),
    ORANGE("\u001B[38;5;208m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String color;

    AnsiColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.color;
    }
}
