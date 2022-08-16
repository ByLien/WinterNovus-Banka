package me.ByLien.WNBanka.Banka;

import me.ByLien.WNBanka.Database.SaveAndLoad;
import me.ByLien.WNBanka.JDA.WNEsle;
import me.ByLien.WNBanka.Main;
import me.ByLien.WNBanka.Menu.CustomInventory;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class Bankaislemleri {

    public static Economy econ = null;

    public void ParaYatir(Player p, int miktar) {
        if (miktar >= 0) {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            econ = rsp.getProvider();
            if (econ.getBalance(p) >= miktar) {
                econ.withdrawPlayer(p, miktar);
                SaveAndLoad.bankahesabi.put(p, SaveAndLoad.bankahesabi.get(p) + miktar);
                new SaveAndLoad().VeriKaydet(p);
                p.sendMessage("§f『§fW§3N§f』§6Banka hesabına §b" + virgul(miktar) + " TL §6yatırıldı.");
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1F,1.2F);
                new WNEsle().Report(p.getName()+" isimli oyuncu banka hesabına "+virgul(miktar)+" TL yatırdı.\nYeni banka bakiyesi: "+virgul(SaveAndLoad.bankahesabi.get(p))+" TL", Color.ORANGE);
                Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
                    new CustomInventory().BankaMenu(p);
                }, 10L);
            } else {
                p.sendMessage("§f『§fW§3N§f』§cBu miktarda bakiyen bulunmuyor.");
            }
        } else {
            p.sendMessage("§f『§fW§3N§f』§cEksi değer giremezsin.");
        }
    }


    public void ParaCek(Player p, int miktar) {
        if (miktar >= 0) {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            econ = rsp.getProvider();
            if (SaveAndLoad.bankahesabi.get(p) >= miktar) {
                econ.depositPlayer(p, miktar);
                SaveAndLoad.bankahesabi.put(p, SaveAndLoad.bankahesabi.get(p) - miktar);
                new SaveAndLoad().VeriKaydet(p);
                p.sendMessage("§f『§fW§3N§f』§6Banka hesabından §b" + virgul(miktar) + " TL §6çekildi.");
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1F,1.2F);
                new WNEsle().Report(p.getName()+" isimli oyuncu banka hesabından "+virgul(miktar)+" TL çekti.\nYeni banka bakiyesi: "+virgul(SaveAndLoad.bankahesabi.get(p))+" TL", Color.GREEN);
                Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, () -> {
                    new CustomInventory().BankaMenu(p);
                }, 10L);
            } else {
                p.sendMessage("§f『§fW§3N§f』§cBankanda bu miktarda bakiye bulunmuyor.");
            }
        } else {
            p.sendMessage("§f『§fW§3N§f』§cEksi değer giremezsin.");
        }
    }

    public String virgul(int s) {
        return (NumberFormat.getNumberInstance(Locale.US).format(s).replace(",", "."));
    }
}
