package com.nuutrai.reactor;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.logging.Logger;

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

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
