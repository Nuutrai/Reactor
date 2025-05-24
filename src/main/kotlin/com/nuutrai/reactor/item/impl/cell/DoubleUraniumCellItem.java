package com.nuutrai.reactor.item.impl.cell;

import com.nuutrai.reactor.item.Buyable;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

public class DoubleUraniumCellItem extends Buyable {

    public DoubleUraniumCellItem() {
        super(
                "uranium_double",
                25,
                "Double Uranium Cell",
                "Acts as two uranium cells, but only takes up a single tile. Produces x power and x heat per pulse for x ticks",
                8,
                4,
                15,
                NamedTextColor.GREEN,
                Material.EMERALD
        );
    }

}
