package com.nuutrai.reactor;

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

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
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

        RegisterCommands.loadInventoryTest();

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
