package me.absprt.absprtnewtask.module;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author AbstractPrinter
 */
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

            Field field = Module.class.getDeclaredField("moduleDescription");
            field.setAccessible(true);
            field.set(module, moduleDescription);

            return module;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
