package com.nuutrai.reactor.entity.impl.cell;

import com.nuutrai.reactor.entity.Sellable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static com.nuutrai.reactor.Reactor.logger;

public class DoubleUraniumCellEntity extends Sellable {

    public DoubleUraniumCellEntity(Player player, Location position) {
        super("uranium_double", Material.EMERALD_BLOCK, player, position);
    }

    public DoubleUraniumCellEntity() {
        super("uranium_double", Material.EMERALD_BLOCK);
    }

    @Override
    public void tick() {
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
        return new DoubleUraniumCellEntity();
    }

}
