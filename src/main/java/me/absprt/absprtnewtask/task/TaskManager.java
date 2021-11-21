package me.absprt.absprtnewtask.task;

import lombok.SneakyThrows;
import me.absprt.absprtnewtask.configuration.FileConfiguration;
import me.absprt.absprtnewtask.configuration.JsonConfiguration;
import me.absprt.absprtnewtask.configuration.YamlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TaskManager {
    private static final Logger logger = LoggerFactory.getLogger(TaskManager.class.getSimpleName());
    private final List<Class<? extends Task>> taskClasses = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private final File taskDir = new File("tasks");

    public TaskManager() {
        this.createTaskDir();
    }

    public void registerTaskClass(Class<? extends Task> taskClass) {
        this.taskClasses.add(taskClass);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void runCanRunTasks() {
        tasks.stream().parallel()
                .filter(Task::canRun)
                .forEach(task -> {
                    logger.info(String.format("Run Task %s", task.getTaskId()));
                    logger.info(String.format("Task Type: %s", task.getClass().getName()));
                    task.run();
                });
    }

    public File getTaskDir() {
        return taskDir;
    }

    private void createTaskDir() {
        if (this.taskDir.exists()) {
            return;
        }
        logger.info("Create Task Directory: " + this.taskDir.mkdirs());
    }

    public void loadLocalTasks() {
        logger.info("Loading local tasks.");
        Arrays.stream(Objects.requireNonNull(this.taskDir.listFiles()))
                .filter(file -> file.getName().endsWith(".yaml") || file.getName().endsWith(".yml") || file.getName().endsWith(".json"))
                .forEach(file -> {
                    FileConfiguration fileConfiguration = null;
                    if (file.getName().endsWith(".yml") || file.getName().endsWith(".yaml")) {
                        fileConfiguration = YamlConfiguration.loadConfiguration(file);
                    }
                    if (file.getName().endsWith(".json")) {
                        fileConfiguration = JsonConfiguration.loadConfiguration(file);
                    }
                    String taskClass = String.valueOf(Objects.requireNonNull(fileConfiguration).get("task_class"));
                    for (Class<? extends Task> aClass : taskClasses) {
                        if (aClass.getName().equalsIgnoreCase(taskClass)) {
                            this.addTask(this.loadTask(aClass, fileConfiguration));
                        }
                    }
                });
    }

    @SneakyThrows
    public Task loadTask(Class<? extends Task> taskClass, FileConfiguration configuration) {
        Task task = taskClass.getConstructor().newInstance();
        Field taskConfigurationField = Task.class.getDeclaredField("taskConfiguration");
        taskConfigurationField.setAccessible(true);
        taskConfigurationField.set(task, configuration);
        return task;
    }
}
