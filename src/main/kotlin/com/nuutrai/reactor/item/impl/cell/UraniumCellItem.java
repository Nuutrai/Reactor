package com.nuutrai.reactor.item.impl.cell;

import com.nuutrai.reactor.item.Buyable;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class UraniumCellItem extends Buyable {

    public UraniumCellItem() {
        super(
                "uranium_single",
                10,
                "Uranium Cell",
                "Tier-1 power production. Produces 1 power and 1 heat per pulse for 15 ticks",
                1,
                1,
                15,
                NamedTextColor.GREEN,
                Material.EMERALD
        );
    }

}
