package com.nuutrai.reactor.item.impl.vent;

import com.nuutrai.reactor.item.Buyable;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

public class BasicVentItem extends Buyable {

    public BasicVentItem() {
        super(
                "basic_vent",
                50,
                "Basic Heat Vent",
                "Lowers the heat of itself by x per tick. Holds a maximum of x heat",
                NamedTextColor.GRAY,
                Material.IRON_INGOT
        );
    }

}
