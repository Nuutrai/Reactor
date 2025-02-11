package com.nuutrai.reactor.entity.impl.cell;

import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.entity.lang.CellEntity;
import com.nuutrai.reactor.util.MultiTypeMap;
import org.bukkit.Material;

import static com.nuutrai.reactor.Reactor.logger;

public class QuadUraniumCellEntity extends CellEntity {

    public QuadUraniumCellEntity() {
        super("uranium_quad", Material.EMERALD_BLOCK);
    }

    @Override
    public void tick(Sellable[] neighbours, MultiTypeMap params) {
        logger.info("Cell at " + this.getPosition().toLocation().getBlockX() + ", " + this.getPosition().toLocation().getBlockY() + ", " + this.getPosition().toLocation().getBlockZ() + " was ticked");
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
        return new QuadUraniumCellEntity();
    }
}
