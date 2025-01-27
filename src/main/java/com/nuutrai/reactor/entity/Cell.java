package com.nuutrai.reactor.entity;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.item.Buyable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

import static com.nuutrai.reactor.Reactor.logger;

public class Cell extends Sellable {

    public static Cell URANIUM = new Cell(
            com.nuutrai.reactor.item.Cell.URANIUM,
            Material.EMERALD_BLOCK);
    public static Cell URANIUM_DOUBLE = new Cell(
            com.nuutrai.reactor.item.Cell.URANIUM_DOUBLE,
            Material.EMERALD_BLOCK
    );
    public static Cell URANIUM_QUAD = new Cell(
            com.nuutrai.reactor.item.Cell.URANIUM_QUAD,
            Material.EMERALD_BLOCK
    );
    private static final Map<String, Cell> CELLS = Maps.newHashMap();

    static {
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(URANIUM);
        cells.add(URANIUM_DOUBLE);
        cells.add(URANIUM_QUAD);

        for (Cell cell : cells) {
            CELLS.put(cell.getType().getId(), cell);
        }
    }

    public Cell(Buyable type, Player player, Location position, Material block) {
        super(type, player, position, block);
    }

    public Cell(Buyable type, Material block) {
        super(type, block);
    }

    public static Cell get(String id) {
        return CELLS.get(id);
    }

    @Override
    public void tick() {
        logger.info("Cell at " + this.getPosition().getBlockX() + ", " + this.getPosition().getBlockY() + ", " + this.getPosition().getBlockZ() + " was ticked");
    }

    @Override
    public double getSellAmount() {
        return 0;
    }

    @Override
    public void sell() {

    }

    @Override
    public Cell clone() {
        return new Cell(this.getType(), this.getBlock());
    }

}
