package me.ByLien.WNBanka.Menu;

import me.ByLien.WNBanka.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class MenuEvent implements Listener {



    @EventHandler
    public void InvenClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        Inventory open = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();

        if (open == null) {
            return;
        }

        if (open.getName().equals(Main.menuisim)) {
            e.setCancelled(true);

            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(Main.item1)) {
                player.closeInventory();
                new CustomInventory().BankaParaCek(player);
            }

            if (item.getItemMeta().getDisplayName().equals(Main.item3)) {
                player.closeInventory();
                new CustomInventory().BankaParaYatir(player);
            }


        }

    }
}