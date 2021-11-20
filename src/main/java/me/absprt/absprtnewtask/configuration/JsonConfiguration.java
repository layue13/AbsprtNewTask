package me.absprt.absprtnewtask.configuration;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConfiguration extends FileConfiguration {
    private static final Gson gson = new Gson();
    private Map<String, Object> data;
    private File loadFile;

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

    @Override
    public void save(@NotNull File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            gson.toJson(data, new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * save change to loadFile.
     */
    @Override
    public void save() {
        this.save(loadFile);
    }

    @Override
    public void load(@NotNull File file) {
        try {
            this.data = gson.fromJson(new FileReader(file), Map.class);
            if (data == null) {
                data = new HashMap<>();
            }
            this.loadFile = file;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonConfiguration loadConfiguration(File file) {
        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        jsonConfiguration.load(file);
        return jsonConfiguration;
    }
}
