package com.nuutrai.reactor.world;

import com.nuutrai.reactor.Reactor;
import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.player.PlayerData;
import com.nuutrai.reactor.util.FileUtils;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashSet;

import static com.nuutrai.reactor.Reactor.logger;

/**
 * TODO:
 * Make a world teleport command based on a player
 */
public class WorldManager {

    static Reactor plugin = Reactor.instance;
    static HashSet<Player> worlds = new HashSet<Player>();

    @SuppressWarnings({"ResultOfMethodCallIgnored", "CallToPrintStackTrace"})
    public static World createWorld(Player player) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        PlayerData pd = DataManager.get(player);
        pd.loadAllEntities();

        return world;
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
