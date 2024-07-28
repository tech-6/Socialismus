package me.whereareiam.socialismus.api.output.module;

import me.whereareiam.socialismus.api.model.module.InternalModule;

import java.nio.file.Path;

public abstract class SocialisticModule {
    protected InternalModule module;
    protected Path workingDirectory;

    public abstract void onLoad();

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void onUnload();
}