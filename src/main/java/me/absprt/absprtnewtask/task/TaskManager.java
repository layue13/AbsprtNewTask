package me.absprt.absprtnewtask.task;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static final Logger logger = LoggerFactory.getLogger(TaskManager.class.getSimpleName());
    private final List<Task> tasks = new ArrayList<>();

    public void putTask(@NotNull Task task) {
        tasks.add(task);
    }

    public void runTasks() {
        tasks.stream()
                .parallel()
                .filter(Task::canRun)
                .forEach(task -> {
                    logger.info(task.getTaskId().toString() + "running...");
                    task.run();
                });
    }
}
