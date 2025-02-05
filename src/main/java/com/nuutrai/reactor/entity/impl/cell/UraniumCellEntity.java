package com.nuutrai.reactor.entity.impl.cell;

import com.nuutrai.reactor.entity.Sellable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static com.nuutrai.reactor.Reactor.logger;

public class UraniumCellEntity extends CellEntity {

    public UraniumCellEntity() {
        super("uranium_single", Material.EMERALD_BLOCK);
    }

    @Override
    public void tick(Sellable[] neighbours, double heatPerNeighbour) {
        for (Sellable neighbour: neighbours) {

        }
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
        return new UraniumCellEntity();
    }


}
