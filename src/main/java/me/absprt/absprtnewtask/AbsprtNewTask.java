package me.absprt.absprtnewtask;

import me.absprt.absprtnewtask.module.ModuleManager;
import me.absprt.absprtnewtask.task.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class AbsprtNewTask {
    private static final Logger logger = LoggerFactory.getLogger(AbsprtNewTask.class.getSimpleName());

    private static final ModuleManager moduleManager = new ModuleManager();
    private static final TaskManager taskManager = new TaskManager();

    private static final File modulesDir = new File("modules");
    private static final File taskDir = new File("tasks");

    private static boolean running;

    public static TaskManager getTaskManager() {
        return taskManager;
    }

    public static ModuleManager getModuleManager() {
        return moduleManager;
    }

    public static File getModulesDir() {
        return modulesDir;
    }

    public static File getTaskDir() {
        return taskDir;
    }

    public static Logger getLogger() {
        return logger;
    }

    private static void createModuleDir() {
        if (modulesDir.exists()) {
            return;
        }
        logger.info("create module dir: " + modulesDir.mkdirs());
    }

    private static void createTaskDir() {
        if (taskDir.exists()) {
            return;
        }
        logger.info("create task dir: " + taskDir.mkdirs());
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        createModuleDir();
        createTaskDir();

        moduleManager.loadAllModule();
        moduleManager.enableAllModule();

        running = true;

        while (running) {
            taskManager.runTasks();
            Thread.sleep(1000);
        }

        moduleManager.disableAllModule();
    }
}
