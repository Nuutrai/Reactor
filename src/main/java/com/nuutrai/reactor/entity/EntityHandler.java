package com.nuutrai.reactor.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class EntityHandler implements Serializable {

    private ArrayList<Sellable> entities = new ArrayList<>();

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
