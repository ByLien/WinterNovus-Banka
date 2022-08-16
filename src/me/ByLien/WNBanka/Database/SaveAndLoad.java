package me.ByLien.WNBanka.Database;

import me.ByLien.WNBanka.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;

public class SaveAndLoad {


    public static HashMap<Player, Integer> bankahesabi = new HashMap<>();


    public void VeriKaydet(Player p) {
        try {
            if (!bankahesabi.containsKey(p))
                return;
            File f = new File(Main.plugin.getDataFolder() + "/database/" + p.getName() + ".yml");
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
            yml.set("Banka Hesabı", bankahesabi.get(p));
            yml.save(f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void VeriYukle(Player p) {
        File f = new File(Main.plugin.getDataFolder() + "/database/" + p.getName() + ".yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
        bankahesabi.put(p, yml.getInt("Banka Hesabı", 0));
    }

    public String AdminVeriOku(String playerName) {
        File f = new File(Main.plugin.getDataFolder() + "/database/" + playerName + ".yml");
        if (f.exists()) {
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
            return String.valueOf(yml.getInt("Banka Hesabı"));
        } else {
            return "0";
        }
    }


}
