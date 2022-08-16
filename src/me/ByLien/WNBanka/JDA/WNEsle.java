package me.ByLien.WNBanka.JDA;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.Listener;
import waterArchery.HesapEsle.HesapEsleMain;

import java.awt.*;
import java.util.Date;

public class WNEsle implements Listener {


    public void Report(String s, Color color) {
        try {
            Guild wn = HesapEsleMain.bot.getGuildById(470616221876748311L);
            TextChannel textChannel = wn.getTextChannelById(1008527956160815134L);
            textChannel.sendMessage(embed(s, color).build()).queue();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static EmbedBuilder embed(String mesaj, Color color) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(color);
        eb.setDescription(mesaj);
        eb.setTimestamp(new Date().toInstant());
        eb.setFooter("WinterNovus Bankası", "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/apple/325/atm-sign_1f3e7.png");
        return eb;
    }
}
