package com.nuutrai.reactor.store;

import com.google.common.collect.Maps;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Map;

public class Vent extends Buyable {
    public Vent(String id, int cost, String name, String description, Material item) {
        super(id, cost, name, description, item);
    }

    private static Map<String, Vent> CELLS = Maps.newHashMap();

    public static Vent BASIC = new Vent("basic_vent",
            50,
            "Basic Heat Vent",
            "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
            Material.IRON_INGOT);
    public static Vent ADVANCED = new Vent(
            "advanced_vent",
            12500,
            "Advanced Heat Vent",
            "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
            Material.IRON_INGOT);

    static {
        ArrayList<Vent> vents = new ArrayList<>();
        vents.add(BASIC);
        vents.add(ADVANCED);

        for (Vent vent : vents) {
            CELLS.put(vent.getId(), vent);
        }
    }
}
