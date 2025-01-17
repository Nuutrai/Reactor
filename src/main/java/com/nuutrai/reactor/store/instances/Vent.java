package com.nuutrai.reactor.store.instances;

import com.nuutrai.reactor.store.VentItem;
import org.bukkit.Material;

public enum Vent {

    BASIC(new VentItem("basic_vent",
            50,
            "Basic Heat Vent",
            "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
            Material.IRON_INGOT)),
    ADVANCED(new VentItem(
            "advanced_vent",
            12500,
            "Advanced Heat Vent",
            "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
            Material.IRON_INGOT)),
    ;

    final VentItem ventInstance;

    Vent (VentItem ventInstance) {
        this.ventInstance = ventInstance;
    }

}
