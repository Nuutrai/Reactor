package com.nuutrai.reactor;

import com.nuutrai.reactor.commands.RegisterCommands;
import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.entity.Cell;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.entity.Vent;
import com.nuutrai.reactor.listeners.*;
import com.nuutrai.reactor.player.PlayerData;
import com.nuutrai.reactor.world.WorldManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.logging.Logger;

/**
 * TODO:
 * <p>
 * Add Raytracing
 * <p>
 * Add breaking cells and others
 * <p>
 * Make everything work like..
 * <p>
 * Use FireFlow method of Cell/Vents (Because this (L70) shit is annoying)
 * <p>
 * Asynchronous entity ticking (REMEMBER NO BUKKIT API IN ASYNC)
 * <p>
 * Add list of all current entities (map with VecLoc) for easy player lookup
 * <p>
 * Look into making more items
 */

public final class Reactor extends JavaPlugin {

    public static Reactor instance;
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

        logger.info("Reactor startup initiated");

        ensureDataFolder();

        logger.info("Data folder: " + dataFolder);

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPlaceBlock(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPlaceEntity(), this);

        Sellable.addSellable(Cell.URANIUM);
        Sellable.addSellable(Cell.URANIUM_DOUBLE);
        Sellable.addSellable(Cell.URANIUM_QUAD);
        Sellable.addSellable(Vent.BASIC);
        Sellable.addSellable(Vent.ADVANCED);

        RegisterCommands.loadInventoryTest();

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            logger.info("Tick!");
            for (Player p: getServer().getOnlinePlayers()) {
                PlayerData pd = DataManager.get(p);

                DataManager.get(p).tick();
            }
        }, 60, 60);

        WorldManager.purge();

//        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
//            for (Player player: Bukkit.getServer().getOnlinePlayers()) {
//                Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
//                   player.entityHandler
//                });
//            }
//        });

        logger.info("Reactor startup complete");

    }

    @Override
    public void onDisable() {

        WorldManager.purge();

    }

    @Override
    public @Nullable ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        return super.getDefaultWorldGenerator(worldName, id);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void ensureDataFolder() {
        if (getDataFolder().exists())
            return;
        getDataFolder().mkdir();
    }
}
