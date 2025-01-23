package com.nuutrai.reactor.item;

import com.google.common.collect.Maps;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Map;

public class Cell extends Buyable {

    public Cell(String id, int cost, String name, String description, Material item) {
        super(id, cost, name, description, item);
    }

    private static Map<String, Cell> CELLS = Maps.newHashMap();

    public static Cell URANIUM = new Cell("uranium_single",
        10,
        "Uranium CellItem",
        "Tier-1 power production. Produces 1 power and 1 heat per pulse for 15 ticks",
         Material.EMERALD);
    public static Cell URANIUM_DOUBLE = new Cell(
        "uranium_double",
        25,
       "Double Uranium CellItem",
       "Acts as two uranium cells, but only takes up a single tile. Produces x power and x heat per pulse for x ticks",
       Material.EMERALD
    );
    public static Cell URANIUM_QUAD = new Cell(
        "uranium_quad",
        50,
         "Quad Uranium CellItem",
         "Acts as four uranium cells, but only takes up a single tile. Produces x power and x heat per pulse for x ticks",
         Material.EMERALD
    );

    static {
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(URANIUM);
        cells.add(URANIUM_DOUBLE);
        cells.add(URANIUM_QUAD);

        for (Cell cell : cells) {
            CELLS.put(cell.getId(), cell);
        }
    }

}