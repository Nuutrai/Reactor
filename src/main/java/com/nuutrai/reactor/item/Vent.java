package com.nuutrai.reactor.item;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.Reactor;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Map;

public class Vent extends Buyable {
    public Vent(String id, int cost, String name, String description, NamedTextColor colour, Material item) {
        super(id, cost, name, description, colour, item);
    }

    private static final Map<String, Vent> VENTS = Maps.newHashMap();

    public static Vent BASIC = new Vent("basic_vent",
            50,
            "Basic Heat Vent",
            "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
            NamedTextColor.GRAY,
            Material.IRON_INGOT);
    public static Vent ADVANCED = new Vent(
            "advanced_vent",
            12500,
            "Advanced Heat Vent",
            "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
            NamedTextColor.GRAY,
            Material.IRON_INGOT);

    static {
        ArrayList<Vent> vents = new ArrayList<>();
        vents.add(BASIC);
        vents.add(ADVANCED);

        for (Vent vent : vents) {
            VENTS.put(vent.getId(), vent);
        }
    }
}
