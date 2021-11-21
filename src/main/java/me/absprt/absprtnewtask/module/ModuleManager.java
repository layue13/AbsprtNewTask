package me.absprt.absprtnewtask.module;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModuleManager {
    private final Logger logger = LoggerFactory.getLogger(ModuleManager.class.getSimpleName());
    private final File modulesDir = new File("modules");
    private final List<Module> moduleList = new ArrayList<>();
    private final ModuleLoader moduleLoader = new ModuleLoader();

    public ModuleManager() {
        createModuleDir();
    }

    public Logger getLogger() {
        return logger;
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
        Arrays.stream(Objects.requireNonNull(this.modulesDir.listFiles()))
                .filter(file -> file.getName().endsWith(".jar"))
                .forEach(file -> {
                    Module module = moduleLoader.loadModule(file);
                    logger.info("Load Module: " + module.getModuleDescription().toString());
                });
    }

    public Module getModule(@NotNull String moduleName) {
        for (Module module : moduleList) {
            if (module.getModuleDescription().getName().equals(moduleName)) {
                return module;
            }
        }
        return null;
    }

    @SneakyThrows
    public void enableModule(@NotNull Module module) {
        if (module.isEnabled()) {
            return;
        }
        if (module.getModuleDescription().getDepends() != null) {
            for (String depend : module.getModuleDescription().getDepends()) {
                if (this.getModule(depend) == null) {
                    throw new RuntimeException("depend module not found: " + depend);
                }
                if (!this.getModule(depend).isEnabled()) {
                    this.enableModule(this.getModule(depend));
                }
            }
        }
        logger.info("Enable Module: " + module.getModuleDescription().getName());
        module.onEnable();
        Field enabledField = Module.class.getDeclaredField("enabled");
        enabledField.setAccessible(true);
        enabledField.set(module, true);
    }

    public void disableModule(@NotNull Module module) {
        logger.info("Disable Module: " + module.getModuleDescription().getName());
        module.onDisable();
    }
}
