package com.nuutrai.reactor;

import com.nuutrai.reactor.commands.RegisterCommands;
import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.listeners.*;
import com.nuutrai.reactor.player.PlayerData;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

/**
 * TODO:
 * <p>
 * Remove Claims
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

        RegisterCommands.loadInventoryTest();

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            logger.info("Tick!");
            for (Player p: getServer().getOnlinePlayers()) {
                PlayerData pd = DataManager.get(p);

                DataManager.get(p).tick();
            }
        }, 60, 60);

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
        // Plugin shutdown logic
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void ensureDataFolder() {
        if (getDataFolder().exists())
            return;
        getDataFolder().mkdir();
    }
}
