package com.nuutrai.reactor.store;

import com.google.common.collect.Maps;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Map;

public class VentItem extends Buyable {
    public VentItem(String id, int cost, String name, String description, Material item) {
        super(id, cost, name, description, item);
    }

    private static Map<String, VentItem> CELLS = Maps.newHashMap();

    public static VentItem BASIC = new VentItem("basic_vent",
            50,
            "Basic Heat Vent",
            "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
            Material.IRON_INGOT);
    public static VentItem ADVANCED = new VentItem(
            "advanced_vent",
            12500,
            "Advanced Heat Vent",
            "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
            Material.IRON_INGOT);

    static {
        ArrayList<VentItem> vents = new ArrayList<>();
        vents.add(BASIC);
        vents.add(ADVANCED);

        for (VentItem vent : vents) {
            CELLS.put(vent.getId(), vent);
        }
    }
}
