package com.nuutrai.reactor;

import com.nuutrai.reactor.listeners.PlayerJoin;
import com.nuutrai.reactor.listeners.PlayerLeave;
import com.nuutrai.reactor.listeners.PlayerPlaceBlock;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.logging.Logger;

@TODO
/**
 * Change Claim player handling to org.bukkit.entity.Player if it works
 */

public final class Reactor extends JavaPlugin {

    public static Reactor instance;
    public static boolean HALTTICK = false;
    public static Logger logger;
    public static File dataFolder;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;
        logger = this.getLogger();
        dataFolder = this.getDataFolder();

        ensureDataFolder();

        logger.info("Data folder: " + dataFolder);

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPlaceBlock(), this);

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
