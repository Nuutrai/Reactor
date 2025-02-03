package com.nuutrai.reactor;

import com.google.common.reflect.ClassPath;
import com.nuutrai.reactor.commands.RegisterCommands;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.entity.impl.cell.*;
import com.nuutrai.reactor.entity.impl.vent.*;
import com.nuutrai.reactor.item.Buyable;
import com.nuutrai.reactor.item.impl.cell.*;
import com.nuutrai.reactor.item.impl.vent.*;
import com.nuutrai.reactor.listeners.*;
import com.nuutrai.reactor.tick.Ticker;
import com.nuutrai.reactor.world.WorldManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

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

public final class Reactor extends JavaPlugin {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Reactor.class);
    public static Reactor instance;
    public Ticker ticker;
    public static boolean HALTTICK = false;
    public static Logger logger;
    public static File dataFolder;
    @SuppressWarnings({"UnstableApiUsage", "NullableProblems"})
    public static LifecycleEventManager<Plugin> manager;

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

        // We'll get this working
//        try {
//            registerListeners("com.nuutrai");
//        } catch (IOException e) {
//            logger.severe("Something went very wrong whilst loading events!");
//        }

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPlaceBlock(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPlaceEntity(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDrop(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSwapHand(), this);

        /* Buyables */

        Buyable.add(new AdvancedVentItem());
        Buyable.add(new BasicVentItem());

        Buyable.add(new DoubleUraniumCellItem());
        Buyable.add(new QuadUraniumCellItem());
        Buyable.add(new UraniumCellItem());

        /* Sellables */

        Sellable.add(new AdvancedVentEntity());
        Sellable.add(new BasicVentEntity());

        Sellable.add(new DoubleUraniumCellEntity());
        Sellable.add(new QuadUraniumCellEntity());
        Sellable.add(new UraniumCellEntity());

        /*          */

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
            } else {
                logger.info("no");
            }
        }
    }

}
