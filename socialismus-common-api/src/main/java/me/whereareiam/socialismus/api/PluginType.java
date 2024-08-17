package me.whereareiam.socialismus.api;

public enum PluginType {
    ALL, BUKKIT, PAPER, VELOCITY;

    public static PluginType getType() {
        if (isBukkitPlugin() && isPaperPlugin() && isVelocityPlugin()) return ALL;
        return getExactType();
    }

    public static PluginType getExactType() {
        if (isVelocityPlugin()) return VELOCITY;
        if (isPaperPlugin()) return PAPER;
        if (isBukkitPlugin()) return BUKKIT;

        throw new IllegalStateException("Unknown plugin type");
    }

    private static boolean isBukkitPlugin() {
        return isClassPresent("me.whereareiam.socialismus.platform.bukkit.BukkitSocialismus");
    }

    private static boolean isPaperPlugin() {
        return isClassPresent("me.whereareiam.socialismus.platform.paper.PaperSocialismus");
    }

    private static boolean isVelocityPlugin() {
        return isClassPresent("me.whereareiam.socialismus.platform.velocity.VelocitySocialismus");
    }

    private static boolean isClassPresent(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (NoClassDefFoundError e) {
            return true;
        }
    }
}
