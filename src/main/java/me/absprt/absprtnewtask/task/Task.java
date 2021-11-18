package me.absprt.absprtnewtask.task;

import lombok.Getter;
import me.absprt.absprtnewtask.configuration.FileConfiguration;

import java.util.UUID;

@Getter
public abstract class Task implements Runnable {
    private final UUID taskId = UUID.randomUUID();
    private FileConfiguration taskConfiguration;

    public abstract boolean canRun();
}
