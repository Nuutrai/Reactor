package com.nuutrai.reactor.entity;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.util.VecLoc;
import org.bukkit.Material;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class EntityHandler implements Serializable {

    private final HashMap<Integer, Sellable> entityMap = Maps.newHashMap();
    private final ArrayList<VecLoc> locations = new ArrayList<>();

    public void add(Sellable entity, VecLoc location) {
        entityMap.put(location.hashCode(), entity);
        locations.add(location);
    }

    public void remove(VecLoc location) {
        entityMap.remove(location.hashCode());
        locations.remove(location);
    }

    public void place() {
        for (VecLoc loc: getLocations()) {
            place(loc);
        }
    }

    public void place(VecLoc loc) {
        Material block = entityMap.get(loc.hashCode()).getBlock();
        loc.toLocation().getBlock().setType(block);
    }

    public Sellable getSellable(VecLoc loc) {
        return entityMap.get(loc.hashCode());
    }

    public HashMap<Integer, Sellable> getEntityMap() {
        return entityMap;
    }

    public ArrayList<VecLoc> getLocations() {
        return locations;
    }

    public void tick() {
        for (VecLoc location: locations) {
            entityMap.get(location.hashCode()).tick();
        }
    }

}
