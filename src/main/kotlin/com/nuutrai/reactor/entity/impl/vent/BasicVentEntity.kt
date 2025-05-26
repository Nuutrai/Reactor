package com.nuutrai.reactor.entity.impl.vent;

import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.entity.lang.VentEntity;
import com.nuutrai.reactor.util.MultiTypeMap;
import org.bukkit.Material;

import static com.nuutrai.reactor.util.ChangeMode.DECREASE;

public class BasicVentEntity extends VentEntity {

    public BasicVentEntity() {
        super("basic_vent", Material.IRON_BLOCK);
    }

    @Override
    public void tick(Sellable[] neighbours, MultiTypeMap params) {

        System.out.printf("Decreased health of %s from %s to %s%n", getPosition().toString(), getHealth(), getHealth() - getHeat());
        health(getHeat(), DECREASE);

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
