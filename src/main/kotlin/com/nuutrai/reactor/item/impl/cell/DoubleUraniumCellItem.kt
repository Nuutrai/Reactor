package com.nuutrai.reactor.item.impl.cell

import com.nuutrai.reactor.item.Buyable
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material

class DoubleUraniumCellItem : Buyable(
	"uranium_double",
	25,
	"Double Uranium Cell",
	"Acts as two uranium cells, but only takes up a single tile. Produces x power and x heat per pulse for x ticks",
	8.0,
	4,
	15.0,
	NamedTextColor.GREEN,
	Material.EMERALD
)
