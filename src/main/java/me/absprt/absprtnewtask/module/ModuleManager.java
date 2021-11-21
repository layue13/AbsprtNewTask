package me.absprt.absprtnewtask.module;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class ModuleManager {
    private static final Logger logger = LoggerFactory.getLogger(ModuleManager.class.getSimpleName());
    private final File modulesDir = new File("modules");
    private final List<Module> moduleList = new ArrayList<>();
    private final ModuleLoader moduleLoader = new ModuleLoader();

    public ModuleManager() {
        createModuleDir();
    }

    public File getModulesDir() {
        return modulesDir;
    }

    private void createModuleDir() {
        if (this.modulesDir.exists()) {
            return;
        }
        logger.info("Create Module Directory: " + this.modulesDir.mkdirs());
    }

    public void loadAllModule() {
        File dir = this.modulesDir;
        Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(file -> file.getName().endsWith(".jar"))
                .forEach(file -> {
                    Module module = moduleLoader.loadModule(file);
                    this.registerModule(module);
                    logger.info("Load Module: " + module.getModuleDescription().toString());
                });
    }

    public Module getModule(@NotNull String className) {
        for (Module module : moduleList) {
            if (module.getClass().getName().equalsIgnoreCase(className)) {
                return module;
            }
        }
        return null;
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
        logger.info("Enable Module: " + module.getModuleDescription().getName());
        module.onEnable();
    }

    public void disableModule(@NotNull Module module) {
        logger.info("Disable Module: " + module.getModuleDescription().getName());
        module.onDisable();
    }
}
