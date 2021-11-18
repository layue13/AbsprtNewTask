package me.absprt.absprtnewtask.configuration;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Configuration {
    void set(@NotNull String path, @NotNull Object object);

    Object get(@NotNull String path);

    List<Object> getList(@NotNull String path);
}
