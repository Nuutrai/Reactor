package com.nuutrai.reactor.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.nuutrai.reactor.Reactor.logger;

public class VecLoc implements Serializable {

    private int x;
    private int y;
    private int z;
    private UUID world;

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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setWorld(UUID world) {
        this.world = world;
    }

    public World getWorld() {
        return getWorld(world);
    }

    public UUID getUUID() {
        return world;
    }

    public List<VecLoc> getNeighbours() {
        ArrayList<VecLoc> locs = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            locs.add(
                    switch (i) {
                        case 0: yield new VecLoc(x, y, z+1, world);
                        case 1: yield new VecLoc(x, y, z-1, world);
                        case 2: yield new VecLoc(x+1, y, z, world);
                        case 3: yield new VecLoc(x-1, y, z, world);
                        default:
                            throw new IllegalStateException("Unexpected value: " + i);
                    }
            );
        }

        return locs;

    }

    public static World getWorld(UUID world) {
        return Bukkit.getWorld(world.toString());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        VecLoc vecLoc = (VecLoc) object;
        return x == vecLoc.x && y == vecLoc.y && z == vecLoc.z && Objects.equals(world, vecLoc.world);
    }

//    public String getId() {
//        return String.valueOf(hashCode());
//    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int hashCode() {
        return Objects.hash(x, y, z, world);
    }
}
