package com.nuutrai.reactor.entity;

import java.io.Serializable;
import java.util.HashSet;

public class EntityHandler implements Serializable {

    private HashSet<Sellable> entities = new HashSet<>();

    public void add(Sellable entity) {
        entities.add(entity);
    }

    public void remove(Sellable entity) {
        entities.remove(entity);
    }

    public void tick() {
        for (Sellable entity: entities) {
            entity.tick();
        }
    }

}
