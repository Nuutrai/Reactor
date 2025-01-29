package com.nuutrai.reactor.item.impl.cell;

import com.nuutrai.reactor.item.Buyable;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

public class QuadUraniumCellItem extends Buyable {

    public QuadUraniumCellItem() {
        super(
                "uranium_quad",
                50,
                "Uranium Cell",
                "Acts as four uranium cells, but only takes up a single tile. Produces x power and x heat per pulse for x ticks",
                NamedTextColor.GREEN,
                Material.EMERALD
        );
    }

}
