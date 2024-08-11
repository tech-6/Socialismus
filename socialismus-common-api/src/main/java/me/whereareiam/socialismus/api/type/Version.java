package me.whereareiam.socialismus.api.type;

import java.util.Arrays;
import java.util.Comparator;

public enum Version {
    UNSUPPORTED,

    V_1_16,
    V_1_16_1,
    V_1_16_2,
    V_1_16_3,
    V_1_16_4,
    V_1_16_5,
    V_1_17,
    V_1_17_1,
    V_1_18,
    V_1_18_1,
    V_1_18_2,
    V_1_19,
    V_1_19_1,
    V_1_19_2,
    V_1_19_3,
    V_1_19_4,
    V_1_20,
    V_1_20_1,
    V_1_20_2,
    V_1_20_3,
    V_1_20_4,
    V_1_20_5,
    V_1_20_6,
    V_1_21,
    V_1_21_1;

    public static Version of(String version) {
        if (version == null || version.isEmpty()) return Version.UNSUPPORTED;

        String normalizedVersion = version.split("[\\s-]")[0].replace(".", "_");

        try {
            return Version.valueOf("V_" + normalizedVersion);
        } catch (IllegalArgumentException e) {
            return Version.UNSUPPORTED;
        }
    }

    public static Version getLatest() {
        return Arrays.stream(Version.values())
                .filter(version -> version != UNSUPPORTED)
                .max(Comparator.comparing(Enum::name))
                .orElse(UNSUPPORTED);
    }

    public static boolean isLowerThan(Version version1, Version version2) {
        return version1.ordinal() < version2.ordinal();
    }

    public static boolean isHigherThan(Version version1, Version version2) {
        return version1.ordinal() > version2.ordinal();
    }

    public boolean isAtLeast(Version version) {
        return !isLowerThan(this, version);
    }
}