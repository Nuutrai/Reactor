package com.nuutrai.reactor.entity.impl.cell;

import com.nuutrai.reactor.entity.Pairable;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.entity.lang.CellEntity;
import com.nuutrai.reactor.util.MultiTypeMap;
import org.bukkit.Material;

public class DoubleUraniumCellEntity extends CellEntity implements Pairable {

    public DoubleUraniumCellEntity() {
        super("uranium_double", Material.EMERALD_BLOCK);
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
        return new DoubleUraniumCellEntity();
    }

}
