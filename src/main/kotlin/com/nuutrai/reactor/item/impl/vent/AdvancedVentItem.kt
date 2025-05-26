package com.nuutrai.reactor.item.impl.vent

import com.nuutrai.reactor.item.Buyable
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material

class AdvancedVentItem : Buyable(
	"advanced_vent",
	12500,
	"Advanced Heat Vent",
	"Lowers the heat of itself by x per tick. Holds a maximum of x heat",
	300.0,
	0,
	3000.0,
	NamedTextColor.GRAY,
	Material.IRON_INGOT
)
