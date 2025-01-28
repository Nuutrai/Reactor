package com.nuutrai.reactor.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static com.nuutrai.reactor.Reactor.logger;

public class VecLoc {

    public int x;
    public int y;
    public int z;
    public UUID world;

    public VecLoc(int x, int y, int z, UUID world) {
        this.world = world;
        this.z = z;
        this.y = y;
        this.x = x;
    }

    public VecLoc(Location location, UUID world) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.world = world;
    }

    public Location toLocation() {
        try {
            return new Location(getWorld(), x, y, z);
        } catch (NullPointerException e) {
            logger.severe("BUILD FALLBACK DUMB DUMB");
        }
        return new Location(Bukkit.getWorlds().getFirst(), x, y, z);
    }

    public Location toLocation(UUID world) {
        return new Location(getWorld(world), x, y, z);
    }

    public void setWorld(UUID world) {
        this.world = world;
    }

    public World getWorld() {
        return getWorld(world);
    }

    public static World getWorld(UUID world) {
        return Bukkit.getWorld(world);
    }
}
