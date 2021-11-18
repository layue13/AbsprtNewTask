package me.absprt.absprtnewtask.module;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author AbstractPrinter
 */
@Getter
public abstract class Module {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private ModuleDescription moduleDescription;

    public abstract void onEnable();

    public abstract void onDisable();
}
