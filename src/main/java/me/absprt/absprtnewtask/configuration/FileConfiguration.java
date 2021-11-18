package me.absprt.absprtnewtask.configuration;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class FileConfiguration implements Configuration {
    public abstract void save(@NotNull File file);
    public abstract void save();

    public abstract void load(@NotNull File file);
}
