package me.whereareiam.socialismus.api.output.module;

import lombok.Setter;
import me.whereareiam.socialismus.api.model.module.InternalModule;

import java.nio.file.Path;

@Setter
public abstract class SocialisticModule {
    protected InternalModule module;

    protected Path workingPath;

    public abstract void onLoad();

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void onUnload();
}