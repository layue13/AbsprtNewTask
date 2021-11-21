package me.absprt.absprtnewtask.task;

import lombok.Getter;
import me.absprt.absprtnewtask.configuration.FileConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Getter
public abstract class Task implements Runnable {
    private final UUID taskId = UUID.randomUUID();
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private FileConfiguration taskConfiguration;

    public abstract boolean canRun();
}
