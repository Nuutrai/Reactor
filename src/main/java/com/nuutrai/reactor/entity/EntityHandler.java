package com.nuutrai.reactor.entity;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.util.VecLoc;
import org.bukkit.Material;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class EntityHandler implements Serializable {

    private final HashMap<String, Sellable> entityMap = Maps.newHashMap();
    private final ArrayList<VecLoc> locations = new ArrayList<>();

    public void add(Sellable entity, VecLoc location) {
        entityMap.put(location.getId(), entity);
        locations.add(location);
    }

    public void remove(VecLoc location) {
        entityMap.remove(location.getId());
        locations.remove(location);
    }

    public void place() {
        for (VecLoc loc: getLocations()) {
            place(loc);
        }
    }

    public void place(VecLoc loc) {
        Material block = entityMap.get(loc.getId()).getBlock();
        loc.toLocation().getBlock().setType(block);
    }

    public Sellable getSellable(VecLoc loc) {
        return entityMap.get(loc.getId());
    }

    public HashMap<String, Sellable> getEntityMap() {
        return entityMap;
    }

    public ArrayList<VecLoc> getLocations() {
        return locations;
    }

    public void tick() {
        for (VecLoc location: locations) {
            entityMap.get(location.getId()).tick();
        }
    }

}
