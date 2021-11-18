package me.absprt.absprtnewtask.configuration;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlConfiguration extends FileConfiguration {
    private static final Yaml yaml = new Yaml();
    private Map<String, Object> data;
    private File loadFile;

    public static YamlConfiguration loadConfiguration(@NotNull File file) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.load(file);
        return yamlConfiguration;
    }

    @Override
    public void set(@NotNull String path, @NotNull Object object) {
        this.data.put(path, object);
    }

    @Override
    public Object get(@NotNull String path) {
        return this.data.get(path);
    }

    @Override
    public List<Object> getList(@NotNull String path) {
        return (List<Object>) this.data.get(path);
    }

    @SneakyThrows
    @Override
    public void save(@NotNull File file) {
        if (!file.exists()) {
            file.createNewFile();
        }
        yaml.dump(data, new PrintWriter(file));
    }

    @Override
    public void save() {
        this.save(this.loadFile);
    }

    @SneakyThrows
    @Override
    public void load(@NotNull File file) {
        this.data = yaml.load(new FileInputStream(file));
        if (this.data == null) {
            this.data = new HashMap<>();
        }
        this.loadFile = file;
    }
}
