package me.ByLien.WNBanka;

import me.ByLien.WNBanka.Database.SaveAndLoad;
import me.ByLien.WNBanka.Menu.CustomInventory;
import me.ByLien.WNBanka.Menu.MenuEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Main extends JavaPlugin implements Listener {


    public static Main plugin;

    public static String menuisim;
    public static String item1;
    public static List<String> item1_lore;
    public static String item2;
    public static List<String> item2_lore;
    public static String item3;
    public static List<String> item3_lore;

    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        FolderCheck();
        loadConfig();
        Reload();
    }


    public void registerEvents() {
        getServer().getPluginManager().registerEvents(plugin, plugin);
        getServer().getPluginManager().registerEvents(new MenuEvent(), plugin);
    }

    public void registerCommands() {
        getCommand("banka").setExecutor(plugin);
        getCommand("wnbankareload").setExecutor(plugin);
        getCommand("bankabak").setExecutor(plugin);
    }

    public void loadConfig() {
        reloadConfig();
        menuisim = plugin.getConfig().getString("gui.name").replace("&", "§");
        item1 = plugin.getConfig().getString("gui.paracek_name").replace("&", "§");
        item1_lore = plugin.getConfig().getStringList("gui.paracek_lore");
        item1_lore.replaceAll(e -> e.replace("&", "§"));
        item2 = plugin.getConfig().getString("gui.hesap_name").replace("&", "§");
        item2_lore = plugin.getConfig().getStringList("gui.hesap_lore");
        item2_lore.replaceAll(e -> e.replace("&", "§"));
        item3 = plugin.getConfig().getString("gui.parayatir_name").replace("&", "§");
        item3_lore = plugin.getConfig().getStringList("gui.parayatir_lore");
        item3_lore.replaceAll(e -> e.replace("&", "§"));
    }

    public void FolderCheck() {
        File o1 = new File(plugin.getDataFolder()+"/database");
        if(!(o1.exists())){
            o1.mkdir();
        }
    }

    public void Reload() {
        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                File f = new File(Main.plugin.getDataFolder() + "/database/" + p.getName() + ".yml");
                if (f.exists()) {
                    new SaveAndLoad().VeriYukle(p);
                }
            }
        }, 20L);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Player p = e.getPlayer();
            if (!p.isOnline())
                return;
            File f = new File(Main.plugin.getDataFolder() + "/database/" + p.getName() + ".yml");
            if (f.exists()) {
                new SaveAndLoad().VeriYukle(p);
            }
        }, 15L);
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (s.equals("banka")) {
            if (sender instanceof Player) {
                Player p = ((Player) sender).getPlayer();
                new CustomInventory().BankaMenu(p);
            }
        }
        if (s.equals("bankabak")) {
            if (sender instanceof Player) {
                Player p = ((Player) sender).getPlayer();
                if (!p.isOp())
                    return false;
                if (args.length != 1) {
                    p.sendMessage(" ");
                    p.sendMessage("§fWinter§3Novus §6Banka Sistemi");
                    p.sendMessage("§bOyuncunun banka hesabına bakmak için:\n§a/bankabak oyuncuismi");
                    p.sendMessage(" ");
                } else {
                    Player oyuncu = Bukkit.getServer().getPlayer(args[0]);
                    if (oyuncu.isOnline()) {
                        if (SaveAndLoad.bankahesabi.containsKey(oyuncu)) {
                            p.sendMessage("§a " + oyuncu.getName() + " §bisimli oyuncunun banka hesabındaki para:\n§a"
                                    + virgul(SaveAndLoad.bankahesabi.get(oyuncu)) + " TL");
                        } else {
                            p.sendMessage("§cBu oyuncunun banka hesabı henüz oluşturulmamış.");
                        }
                    } else {
                        p.sendMessage("§a " + oyuncu.getName() + " §bisimli oyuncunun banka hesabındaki para:\n§a"
                                + virgul(Integer.parseInt(new SaveAndLoad().AdminVeriOku(args[0]))) + " TL");
                    }
                }
            }
        }
        if (s.equals("wnbankareload")) {
            if (sender instanceof Player) {
                Player p = ((Player) sender).getPlayer();
                if (!p.isOp())
                    return false;
                loadConfig();
                p.sendMessage("§aAyarlar yeniden yüklendi.");
            } else {
                loadConfig();
                sender.sendMessage("§aAyarlar yeniden yüklendi.");
            }
        }
    return false;
    }

    public String virgul(int s) {
        return (NumberFormat.getNumberInstance(Locale.US).format(s).replace(",", "."));
    }
}
