package me.ByLien.WNBanka.Menu;

import me.ByLien.WNBanka.Banka.Bankaislemleri;
import me.ByLien.WNBanka.Database.SaveAndLoad;
import me.ByLien.WNBanka.AnvilAPI.AnvilGUI;
import me.ByLien.WNBanka.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomInventory implements Listener {

    public void BankaMenu(Player player) {

        if (!SaveAndLoad.bankahesabi.containsKey(player)) SaveAndLoad.bankahesabi.put(player, 0);
        List<String> hesaplore = new ArrayList<>(Main.item2_lore);
        hesaplore.replaceAll(e -> e.replace("%bakiye%", virgul(SaveAndLoad.bankahesabi.get(player))));


        Inventory onay = Main.plugin.getServer().createInventory(null, 45, "§fWinter§3Novus §0Banka");

        ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName(" ");
        empty.setItemMeta(emptyMeta);


        ItemStack cek = new ItemStack(Material.EMERALD);
        ItemMeta cek_meta = cek.getItemMeta();
        cek_meta.setDisplayName(Main.item1);
        cek_meta.setLore(Main.item1_lore);
        cek.setItemMeta(cek_meta);


        ItemStack hesap = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta hesap_meta = hesap.getItemMeta();
        hesap_meta.setDisplayName(Main.item2);
        hesap_meta.setLore(hesaplore);
        hesap.setItemMeta(hesap_meta);

        ItemStack yatir = new ItemStack(Material.EMPTY_MAP);
        ItemMeta yatir_meta = yatir.getItemMeta();
        yatir_meta.setDisplayName(Main.item3);
        yatir_meta.setLore(Main.item3_lore);
        yatir.setItemMeta(yatir_meta);


        for (int i = 0; i < 45; i++) {
            if (i != 20 && i != 22 && i != 24)
                onay.setItem(i, empty);
        }
        onay.setItem(20, cek);
        onay.setItem(22, hesap);
        onay.setItem(24, yatir);

        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            player.openInventory(onay);
        }, 2L);
    }

    public void BankaParaYatir(Player p) {

        AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler() {
            @Override
            public void onAnvilClick(AnvilGUI.AnvilClickEvent e) {
                if (e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                    e.setWillClose(true);
                    e.setWillDestroy(true);
                    try {
                        int miktar = Integer.parseInt(e.getName());
                        new Bankaislemleri().ParaYatir(p, miktar);
                    } catch (Exception ex) {
                        p.sendMessage("§f『§fW§3N§f』§cMiktar sadece sayı değeri olabilir.");
                        Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
                            BankaMenu(p);
                        }, 10L);
                    }
                } else {
                    e.setWillClose(true);
                    e.setWillDestroy(true);
                    Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
                        BankaMenu(p);
                    }, 5L);
                }
            }

        });

        ItemStack i = new ItemStack(Material.EMPTY_MAP);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName("Miktarı girin.");
        i.setItemMeta(im);

        gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, i);

        gui.open();
    }

    public void BankaParaCek(Player p) {

        AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler() {
            @Override
            public void onAnvilClick(AnvilGUI.AnvilClickEvent e) {
                if (e.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                    e.setWillClose(true);
                    e.setWillDestroy(true);
                    try {
                        int miktar = Integer.parseInt(e.getName());
                        new Bankaislemleri().ParaCek(p, miktar);
                    } catch (Exception ex) {
                        p.sendMessage("§f『§fW§3N§f』§cMiktar sadece sayı değeri olabilir.");
                        Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
                            BankaMenu(p);
                        }, 10L);
                    }
                } else {
                    e.setWillClose(true);
                    e.setWillDestroy(true);
                }
            }
        });

        ItemStack i = new ItemStack(Material.EMERALD);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName("Miktarı girin.");
        i.setItemMeta(im);

        gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, i);

        gui.open();
    }

    public String virgul(int s) {
        return (NumberFormat.getNumberInstance(Locale.US).format(s).replace(",", "."));
    }
}
