package me.absprt.absprtnewtask.module;

import me.absprt.absprtnewtask.AbsprtNewTask;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleLoader {
    private static final Logger logger = LoggerFactory.getLogger(ModuleLoader.class.getSimpleName());
    private final Yaml yaml;

    public ModuleLoader() {
        this.yaml = new Yaml();
        this.yaml.setBeanAccess(BeanAccess.FIELD);
    }

    public ModuleDescription getModuleDescription(@NotNull File file) {
        try {
            JarFile jarFile = new JarFile(file);
            JarEntry jarEntry = jarFile.getJarEntry("module.yml");
            InputStream inputStream = jarFile.getInputStream(jarEntry);
            return yaml.loadAs(inputStream, ModuleDescription.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Module loadModule(@NotNull File file) {
        try {
            ModuleDescription moduleDescription = this.getModuleDescription(file);
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());
            Class<?> moduleMainClass = Class.forName(moduleDescription.getMainClass(), true, urlClassLoader);
            Module module = (Module) moduleMainClass.getConstructor().newInstance();

            Field moduleDescriptionField = Module.class.getDeclaredField("moduleDescription");
            moduleDescriptionField.setAccessible(true);
            moduleDescriptionField.set(module, moduleDescription);

            Field moduleManagerField = Module.class.getDeclaredField("moduleManager");
            moduleManagerField.setAccessible(true);
            moduleManagerField.set(module, AbsprtNewTask.getModuleManager());

            Field taskManagerField = Module.class.getDeclaredField("taskManager");
            taskManagerField.setAccessible(true);
            taskManagerField.set(module, AbsprtNewTask.getTaskManager());

            return module;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
