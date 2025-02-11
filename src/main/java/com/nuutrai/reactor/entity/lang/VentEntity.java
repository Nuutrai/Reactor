package com.nuutrai.reactor.entity.lang;

import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.util.MultiTypeMap;
import org.bukkit.Material;

import java.util.ArrayList;

public abstract class VentEntity extends Sellable {
    public VentEntity(String id, Material block) {
        super(id, block);
    }

    @Override
    public void tick() {
        tick((Sellable[]) new ArrayList<Sellable>().toArray(), new MultiTypeMap());
    }
}
