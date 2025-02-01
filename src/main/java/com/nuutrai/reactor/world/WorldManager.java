package com.nuutrai.reactor.world;

import com.nuutrai.reactor.Reactor;
import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.PlayerData;
import com.nuutrai.reactor.util.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.nuutrai.reactor.Reactor.logger;

/**
 * TODO:
 * Make a world teleport command based on a player
 */
public class WorldManager {

    private static final Logger log = LoggerFactory.getLogger(WorldManager.class);
    static Reactor plugin = Reactor.instance;
    static HashSet<Player> worlds = new HashSet<>();

    public static World createWorld(Player player) {

        String worldName = player.getUniqueId().toString();

        File worldFolder = new File(plugin.getServer().getWorldContainer(), worldName);
        if (worldFolder.exists()) {
            FileUtils.deleteFolder(worldFolder);
        }

        ExecutorService executor = Executors.newCachedThreadPool();

        Future<Boolean> future = executor.submit(() -> {
            logger.info("1");
            return cloneWorld("init", player.getUniqueId().toString());
        });

        try {
            if (!future.get()) {
                logger.info("2");
                logger.severe("Womp womp");
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        logger.info("3");
        WorldCreator c = new WorldCreator(worldName);
        c.seed(0);
        c.generator(new VoidGenerator(Reactor.instance));

        World world = null;
        try {
            world = c.createWorld();
            worlds.add(player);
            PlayerData pd = DataManager.get(player);
            pd.loadAllEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return world;

    }

    @SuppressWarnings({"CallToPrintStackTrace"})
    public static World createWorld1(Player player) {

        String worldName = player.getUniqueId().toString();

        File worldFolder = new File(plugin.getServer().getWorldContainer(), worldName);
        if (worldFolder.exists()) {
            FileUtils.deleteFolder(worldFolder);
        }

        WorldCreator c = new WorldCreator(worldName);
        c.seed(0);
        c.generator(new VoidGenerator(Reactor.instance));

        World world = null;
        try {
            world = c.createWorld();
            worlds.add(player);
            PlayerData pd = DataManager.get(player);
            pd.loadAllEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return world;
    }

    public static World init() {

        String worldName = "init";

        File worldFolder = new File(plugin.getServer().getWorldContainer(), worldName);
        if (worldFolder.exists()) {
            return null;
        }

        WorldCreator c = new WorldCreator(worldName);
        c.seed(0);
        c.generator(new VoidGenerator(Reactor.instance));

        World world = null;
        try {
            world = c.createWorld();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return world;
    }

    public static boolean cloneWorld(String oldName, String newName) {

        final File oldWorldFile = new File(plugin.getServer().getWorldContainer(), oldName);
        final File newWorldFile = new File(plugin.getServer().getWorldContainer(), newName);
        final List<String> ignoreFiles = new ArrayList<>(Arrays.asList("session.lock", "uid.dat"));

        if (newWorldFile.exists()) {
            logger.warning("Folder for new world '" + newName + "' already exists");
            return false;
        }

        logger.info("Copying files for world '" + oldName + "'");
        if (!FileUtils.copyFolder(oldWorldFile, newWorldFile, ignoreFiles)) {
            logger.warning("Failed to copy files for world '" + newName + "', see the log info");
            return false;
        }

        if (newWorldFile.exists()) {
            logger.info("Succeeded at copying files");
            return true;
        }

        return false;
    }

    public static boolean deleteWorld(Player player) {
        String worldName = player.getUniqueId().toString();
        World world = plugin.getServer().getWorld(worldName);
        if (world == null) {
            return false;
        }

        plugin.getServer().unloadWorld(worldName, false);

        try {
            File worldFile = world.getWorldFolder();
            logger.finer("deleteWorld(): worldFile: " + worldFile.getAbsolutePath());
            FileUtils.deleteFolder(worldFile);
            logger.info(String.format("World '%s' was DELETED.", worldName));
            worlds.remove(player);
            return true;
        } catch (Throwable e) {
            logger.info("Whoa, not sure what happened here!");
            return false;
        }
    }

    public static void purge() {
        for (Player world: worlds) {
            deleteWorld(world);
        }
    }

}
