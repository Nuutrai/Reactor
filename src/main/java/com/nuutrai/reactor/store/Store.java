package com.nuutrai.reactor.store;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.item.Cell;
import com.nuutrai.reactor.item.Vent;
import com.nuutrai.reactor.player.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.nuutrai.reactor.Reactor.logger;

public class Store {

   public static void setup(Player player) {

       Inventory inventory = player.getInventory();

       ArrayList<ItemStack> items = getItems(player);

       for (int i = 0; i < items.size() - 1; i++) {
           inventory.setItem(i, items.get(i));
       }
   }

   public static ArrayList<ItemStack> getItems(Player player) {
       ArrayList<ItemStack> items = new ArrayList<>();

       for (int i = 0; i < 3; i++) {
           items.add(getBackground());
       }

       ItemStack power = new ItemStack(Material.WIND_CHARGE);
       power.getItemMeta().displayName(Component.text("Power", NamedTextColor.AQUA));
       items.add(power);

       PlayerData playerData = DataManager.get(player);

       logger.info(playerData.selection.toString());

       items.add(playerData.selection);
//       items.add(ItemStack.of(Material.BARRIER));
       ItemStack heat = new ItemStack(Material.BLAZE_POWDER);
       heat.getItemMeta().displayName(Component.text("Heat", NamedTextColor.RED));
       items.add(heat);

       for (int i = 0; i < 3; i++) {
           items.add(getBackground());
       }

       /*
         Type-s-Stage
        */

       ItemStack cs1 = new ItemStack(Cell.URANIUM.getItem());
       ItemStack cs2 = new ItemStack(Cell.URANIUM_DOUBLE.getItem());
       ItemStack cs3 = new ItemStack(Cell.URANIUM_DOUBLE.getItem());

       items.add(cs1);
       items.add(cs2);
       items.add(cs3);

       for (int i = 0; i < 4; i++) {
           items.add(getBackground());
       }

       ItemStack vs1 = new ItemStack(Vent.BASIC.getItem());
       ItemStack vs2 = new ItemStack(Vent.ADVANCED.getItem());

       items.add(vs1);
       items.add(vs2);

       for (int i = 0; i < 18; i++) {
           items.add(getBackground());
       }

       for (int i = 0; i < 4; i++) {
           items.add(ItemStack.of(Material.AIR));
       }

       ItemStack pause;
       if (playerData.isPaused()) {
           pause = new ItemStack(Material.FIREWORK_STAR);
       } else {
           pause = new ItemStack(Material.FIRE_CHARGE);
       }

       pause.getItemMeta().displayName(Component.text("Pause", NamedTextColor.RED));
       items.add(pause);

       return items;
   }

   private static ItemStack getBackground() {
       ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
       item.getItemMeta().setHideTooltip(true);
       return item;
   }

}
