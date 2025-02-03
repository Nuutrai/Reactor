package com.nuutrai.reactor;

import com.google.common.reflect.ClassPath;
import com.nuutrai.reactor.commands.RegisterCommands;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.item.Buyable;
import com.nuutrai.reactor.tick.Ticker;
import com.nuutrai.reactor.world.WorldManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

/**
 * TODO:
 * <p>
 * Add breaking cells and others
 * <p>
 * Fix throwing/dropping items
 * <p>
 * Add check for player's world before attempting place
 * <p>
 * Add check to make sure there aren't any entities in the player's place area
 * <p>
 * Add check for y level cause I don't want to do the 3d thing
 * <p>
 * Make everything work like..
 * <p>
 * Add Raytracing
 * <p>
 * Asynchronous entity ticking (REMEMBER NO BUKKIT API IN ASYNC)
 * <p>
 * Add list of all current entities (map with VecLoc) for easy player lookup
 * <p>
 * Look into making more items
 */

@SuppressWarnings({"", "unchecked"})
public final class Reactor extends JavaPlugin {

    public static Reactor instance;
    public static boolean HALTTICK = false;
    public static Logger logger;
    public static File dataFolder;
    @SuppressWarnings({"UnstableApiUsage", "NullableProblems"})
    public static LifecycleEventManager<Plugin> manager;
    public Ticker ticker;

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;
        logger = this.getLogger();
        dataFolder = this.getDataFolder();
        manager = Reactor.instance.getLifecycleManager();
        ticker = new Ticker();

        logger.info("Reactor startup initiated");

        ensureDataFolder();

        logger.info("Data folder: " + dataFolder);

        WorldManager.init();

        try {
            registerListeners("com.nuutrai.reactor.listeners");
        } catch (IOException e) {
            logger.severe("Something went horribly wrong whilst loading events!");
        }

        try {
            registerReactorElements("item", Buyable.class);
        } catch (IOException e) {
            logger.severe("Something went horribly wrong whilst loading buyables!");
        }

        try {
            registerReactorElements("entity", Sellable.class);
        } catch (IOException e) {
            logger.severe("Something went horribly wrong whilst loading sellables!");
        }

        try {
            RegisterCommands.load();
        } catch (InvocationTargetException | IllegalAccessException e) {
            logger.severe("Something went incredibly wrong whist loading commands!");
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, () -> {
            ticker.tick();
        }, 1, 0);

        WorldManager.purge();

        logger.info("Reactor startup complete");

    }

    @Override
    public void onDisable() {

        WorldManager.purge();

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void ensureDataFolder() {
        if (getDataFolder().exists())
            return;
        getDataFolder().mkdir();
    }

    private <T> void registerReactorElements(String subpackageName, Class<T> parentClass) throws IOException {
        ClassPath classPath = ClassPath.from(this.getClassLoader());

        String packageName = "com.nuutrai.reactor." + subpackageName;

        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(packageName)) {
            Class<?> clazz = classInfo.load();

            if (parentClass.isAssignableFrom(clazz) && !clazz.equals(parentClass)) {
                try {
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    T instance = (T) constructor.newInstance();

                    // Call the static add() method on the parent class
                    parentClass.getMethod("add", parentClass).invoke(null, instance);

                    logger.info("Registered " + parentClass.getSimpleName() + ": " + clazz.getSimpleName());
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                         InvocationTargetException e) {
                    logger.severe("Failed to register " + parentClass.getSimpleName() + ": " + clazz.getSimpleName());
                    e.printStackTrace();
                }
            }
        }
    }

    private void registerListeners(String packageName) throws IOException {
        // Get all classes in the specified package using Guava's ClassPath
        ClassPath classPath = ClassPath.from(this.getClassLoader());
        logger.info(classPath.getTopLevelClassesRecursive(packageName).toString());
        logger.info("" + classPath.getTopLevelClassesRecursive(packageName).size());
        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(packageName)) {
            Class<?> clazz = classInfo.load();

            // Check if the class is a subclass of Listener
            if (Listener.class.isAssignableFrom(clazz)) {
                try {
                    // Ensure the class has a no-arg constructor
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    Listener listener = (Listener) constructor.newInstance();

                    // Register the listener with Bukkit
                    getServer().getPluginManager().registerEvents(listener, this);
                    logger.info("Registered listener: " + clazz.getSimpleName());
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                         InvocationTargetException e) {
                    logger.severe("Failed to register listener: " + clazz.getSimpleName());
                    e.printStackTrace();
                }
            }
        }
    }
}
