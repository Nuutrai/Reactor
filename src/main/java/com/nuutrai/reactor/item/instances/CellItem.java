package com.nuutrai.reactor.item.instances;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.item.Cell;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public enum CellItem {

    URANIUM(new Cell("uranium_single",
            10,
            "Uranium Cell",
            "Tier-1 power production. Produces 1 power and 1 heat per pulse for 15 ticks",
            Material.EMERALD)),
    URANIUM_DOUBLE(new Cell(
            "uranium_double",
            25,
            "Double Uranium Cell",
            "Acts as two uranium cells, but only takes up a single tile. Produces x power and x heat per pulse for x ticks",
            Material.EMERALD)),
    URANIUM_QUAD(new Cell(
            "uranium_quad",
            25,
            "Quad Uranium Cell",
            "Acts as four uranium cells, but only takes up a single tile. Produces x power and x heat per pulse for x ticks",
            Material.EMERALD)),
    ;

    final Cell cellInstance;
    private static final Map<String, CellItem> BY_ID = Maps.newHashMap();
    final String key;

    CellItem(Cell cellInstance) {
        this.cellInstance = cellInstance;
        this.key = cellInstance.getId();
    }

    static {
        for (CellItem cell : values()) {
            BY_ID.put(cell.key, cell);
        }
    }

    public static @Nullable CellItem getMaterial(String id) {
        return BY_ID.get(id);
    }

}
