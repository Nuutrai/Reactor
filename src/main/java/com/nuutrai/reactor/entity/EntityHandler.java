package com.nuutrai.reactor.entity;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.util.VecLoc;
import org.bukkit.Material;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static com.nuutrai.reactor.Reactor.logger;

public class EntityHandler implements Serializable {

    private final HashMap<VecLoc, Sellable> entityMap = Maps.newHashMap();
    private final ArrayList<VecLoc> locations = new ArrayList<>();

    public void add(Sellable entity, VecLoc location) {
        entity.setEntityHandler(this);
        entityMap.put(location, entity);
        locations.add(location);
    }

    public void remove(VecLoc location) {
        entityMap.remove(location);
        locations.remove(location);
    }

    public void place() {
        for (VecLoc loc: getLocations()) {
            place(loc);
        }
    }

    public void place(VecLoc loc) {
        Material block = entityMap.get(loc).getBlock();
        loc.toLocation().getBlock().setType(block);
    }

    public Sellable get(VecLoc loc) {
        return entityMap.get(loc);
    }

    public HashMap<VecLoc, Sellable> getEntityMap() {
        return entityMap;
    }

    public ArrayList<VecLoc> getLocations() {
        return locations;
    }

    public void tick() {
        for (VecLoc location: locations) {
            entityMap.get(location).tick();
        }
    }

}
