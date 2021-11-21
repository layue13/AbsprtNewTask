package me.absprt.absprtnewtask.module;

import lombok.Getter;
import me.absprt.absprtnewtask.task.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public abstract class Module {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private ModuleDescription moduleDescription;
    private ModuleManager moduleManager;
    private TaskManager taskManager;
    private boolean enabled;

    public abstract void onEnable();

    public abstract void onDisable();
}
