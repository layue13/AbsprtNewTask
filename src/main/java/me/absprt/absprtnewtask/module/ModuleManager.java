package me.absprt.absprtnewtask.module;

import me.absprt.absprtnewtask.AbsprtNewTask;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author AbstractPrinter
 */
public class ModuleManager {
    private static final Logger logger = LoggerFactory.getLogger(ModuleManager.class.getSimpleName());
    private final List<Module> moduleList = new ArrayList<>();
    private final ModuleLoader moduleLoader = new ModuleLoader();

    public void loadAllModule() {
        logger.info("load all module.");
        File dir = AbsprtNewTask.getModulesDir();
        Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(file -> file.getName().endsWith(".jar"))
                .forEach(file -> {
                    Module module = moduleLoader.loadModule(file);
                    this.registerModule(module);
//                    logger.info("register module: " + module.getModuleDescription().toString());
                });
    }

    public void enableAllModule() {
        moduleList.forEach(Module::onEnable);
    }

    public void disableAllModule() {
        moduleList.forEach(Module::onDisable);
    }

    public void registerModule(@NotNull Module module) {
        if (this.moduleList.contains(module)) {
            return;
        }
        this.moduleList.add(module);
    }

    public void enableModule(@NotNull Module module) {
        module.onEnable();
    }

    public void disableModule(@NotNull Module module) {
        module.onDisable();
    }
}
