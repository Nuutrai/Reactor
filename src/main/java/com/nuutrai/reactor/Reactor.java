package com.nuutrai.reactor;

import com.nuutrai.reactor.commands.RegisterCommands;
import com.nuutrai.reactor.listeners.PlayerJoin;
import com.nuutrai.reactor.listeners.PlayerLeave;
import com.nuutrai.reactor.listeners.PlayerPlaceBlock;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

/**
 * TODO:
 * <p>
 * Change Claim player handling to org.bukkit.entity.Player if it works
 */

public final class Reactor extends JavaPlugin {

    public static Reactor instance;
    public static boolean HALTTICK = false;
    public static Logger logger;
    public static File dataFolder;
    public static NamespacedKey key;
    @SuppressWarnings({"UnstableApiUsage", "NullableProblems"})
    public static LifecycleEventManager<Plugin> manager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;
        logger = this.getLogger();
        dataFolder = this.getDataFolder();
        key = new NamespacedKey(this, "reactor-pdc");
        manager = Reactor.instance.getLifecycleManager();

        logger.info("Reactor startup initiated");

        ensureDataFolder();

        logger.info("Data folder: " + dataFolder);

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPlaceBlock(), this);

        RegisterCommands.loadInventoryTest();

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
