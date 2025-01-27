package com.nuutrai.reactor.entity;

import com.google.common.collect.Maps;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class EntityHandler implements Serializable {

    private final HashMap<Location, Sellable> entityMap = Maps.newHashMap();
    private final ArrayList<Location> locations = new ArrayList<>();

    public void add(Sellable entity, Location location) {
        entityMap.put(location, entity);
        locations.add(location);
    }

    public void remove(Location location) {
        entityMap.remove(location);
        locations.remove(location);
    }

    public Sellable getSellable(Location loc) {
        return entityMap.get(loc);
    }

    public HashMap<Location, Sellable> getEntityMap() {
        return entityMap;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void tick() {
        for (Location location: locations) {
            entityMap.get(location).tick();
        }
    }

}
