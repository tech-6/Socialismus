package me.whereareiam.socialismus.common;

import lombok.Getter;

public class Constants {
    @Getter
    private static final int bStatsBukkitId = 19855;
    @Getter
    private static final int bStatsVelocityId = 22720;

    @Getter
    private static final String version = "@version@".toUpperCase();

    @Getter
    private static final String guiceVersion = "@guiceVersion@";
    @Getter
    private static final String jacksonVersion = "@jacksonVersion@";
    @Getter
    private static final String adventureBukkitVersion = "@adventureBukkitVersion@";

    @Getter
    private static final String cloudVersion = "@cloudVersion@";
    @Getter
    private static final String cloudPaperVersion = "@cloudPaperVersion@";
    @Getter
    private static final String cloudVelocityVersion = "@cloudVelocityVersion@";
    @Getter
    private static final String cloudMinecraftExtrasVersion = "@cloudMinecraftExtrasVersion@";
}
