package com.nuutrai.reactor.entity.impl.vent;

import com.nuutrai.reactor.entity.Sellable;
import org.bukkit.Material;

public abstract class VentEntity extends Sellable {
    public VentEntity(String id, Material block) {
        super(id, block);
    }
}
