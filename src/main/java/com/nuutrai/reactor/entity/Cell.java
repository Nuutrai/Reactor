package com.nuutrai.reactor.entity;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.player.Claim;
import com.nuutrai.reactor.store.Buyable;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

public class Cell extends Sellable {

    public Cell(Buyable type, Player player, Block position, Claim claim, Material block) {
        super(type, block);
    }

    public Cell(Buyable type, Material block) {
        super(type, block);
    }

    @Override
    public void tick() {
        MiniMessage.builder()
    }

    @Override
    public double getSellAmount() {
        return 0;
    }

    @Override
    public void sell() {

    }

    private static Map<String, Cell> CELLS = Maps.newHashMap();

    public static Cell URANIUM = new Cell(
            com.nuutrai.reactor.store.Cell.URANIUM,
            Material.EMERALD_BLOCK);

    public static Cell URANIUM_DOUBLE = new Cell(
            com.nuutrai.reactor.store.Cell.URANIUM_DOUBLE,
            Material.EMERALD_BLOCK
    );
    public static Cell URANIUM_QUAD = new Cell(
            com.nuutrai.reactor.store.Cell.URANIUM_QUAD,
            Material.EMERALD_BLOCK
    );

    static {
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(URANIUM);
        cells.add(URANIUM_DOUBLE);
        cells.add(URANIUM_QUAD);

        for (Cell cell : cells) {
            CELLS.put(cell.getType().getId(), cell);
        }
    }

}
