package me.absprt.absprtnewtask.module;

import lombok.Getter;

/**
 * @author AbstractPrinter
 */
@Getter
public class ModuleDescription {
    private String name;
    private String author;
    private String version;
    private String mainClass;

    public ModuleDescription() {
    }
}
