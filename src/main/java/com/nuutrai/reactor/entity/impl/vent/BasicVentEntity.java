package com.nuutrai.reactor.entity.impl.vent;

import com.nuutrai.reactor.entity.Sellable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BasicVentEntity extends VentEntity {

    public BasicVentEntity() {
        super("basic_vent", Material.IRON_BLOCK);
    }

    @Override
    public void tick() {

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
        return new BasicVentEntity();
    }
}
