package com.nuutrai.reactor.item.impl.cell

import com.nuutrai.reactor.item.Buyable
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material

class QuadUraniumCellItem : Buyable(
	"uranium_quad",
	50,
	"Quad Uranium Cell",
	"Acts as four uranium cells, but only takes up a single tile. Produces x power and x heat per pulse for x ticks",
	36.0,
	12,
	15.0,
	NamedTextColor.GREEN,
	Material.EMERALD
)
