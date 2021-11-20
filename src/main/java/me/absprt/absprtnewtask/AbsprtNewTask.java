package me.absprt.absprtnewtask;

import me.absprt.absprtnewtask.module.ModuleManager;
import me.absprt.absprtnewtask.task.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbsprtNewTask {
    private static final Logger logger = LoggerFactory.getLogger(AbsprtNewTask.class.getSimpleName());
    private static final ModuleManager moduleManager = new ModuleManager();
    private static final TaskManager taskManager = new TaskManager();
    private static boolean running;

    public static Logger getLogger() {
        return logger;
    }

    public static ModuleManager getModuleManager() {
        return moduleManager;
    }

    public static TaskManager getTaskManager() {
        return taskManager;
    }

    public static void main(String[] args) throws InterruptedException {
        moduleManager.loadAllModule();
        moduleManager.enableAllModule();

        running = true;

        while (running) {
            Thread.sleep(1000);
            taskManager.runCanRunTasks();
        }

        moduleManager.disableAllModule();
    }
}
