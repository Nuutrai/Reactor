package com.nuutrai.reactor.entity.impl.vent;

import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.entity.lang.VentEntity;
import com.nuutrai.reactor.util.MultiTypeMap;
import org.bukkit.Material;

public class AdvancedVentEntity extends VentEntity {

    public AdvancedVentEntity() {
        super("advanced_vent", Material.IRON_BLOCK);
    }

    @Override
    public void tick(Sellable[] neighbours, MultiTypeMap params) {

    }

    @Override
    public double getSellAmount() {
        return 0;
    }

    @Override
    public void sell() {

    }

    @Override
    public Sellable clone() {
        return new AdvancedVentEntity();
    }
}
