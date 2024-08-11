package me.whereareiam.socialismus.api.type.chat;

public enum ChatType {
    LOCAL,
    GLOBAL,
    CUSTOM;

    public boolean isLocal() {
        return this == LOCAL;
    }

    public boolean isGlobal() {
        return this == GLOBAL;
    }
}
