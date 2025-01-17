package com.nuutrai.reactor;

import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public final class Reactor extends JavaPlugin {

    public static Reactor instance;
    public static boolean HALTTICK = false;
    public static Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;
        logger = this.getLogger();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
