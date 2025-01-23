package com.nuutrai.reactor.item.instances;

import com.nuutrai.reactor.item.Vent;
import org.bukkit.Material;

public enum VentItem {

    BASIC(new Vent("basic_vent",
            50,
            "Basic Heat VentItem",
            "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
            Material.IRON_INGOT)),
    ADVANCED(new Vent(
            "advanced_vent",
            12500,
            "Advanced Heat VentItem",
            "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
            Material.IRON_INGOT)),
    ;

    final Vent ventInstance;

    VentItem (Vent ventInstance) {
        this.ventInstance = ventInstance;
    }

}
