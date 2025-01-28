package com.nuutrai.reactor.entity;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.item.Buyable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

public class Vent extends Sellable {

    public static Vent BASIC = new Vent(
            com.nuutrai.reactor.item.Vent.BASIC,
            Material.IRON_BLOCK);
    public static Vent ADVANCED = new Vent(
            com.nuutrai.reactor.item.Vent.ADVANCED,
            Material.IRON_BLOCK
    );

    static {
        ArrayList<Vent> vents = new ArrayList<>();
        vents.add(BASIC);
        vents.add(ADVANCED);

        for (Vent vent : vents) {
            SELLABLES.put(vent.getType().getId(), vent);
        }
    }

    public Vent(Buyable type, Player player, Location position, Material block) {
        super(type, player, position, block);
    }

    public Vent(Buyable type, Material block) {
        super(type, block);
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
    public Vent clone() {
        return new Vent(this.getType(), this.getBlock());
    }

}
