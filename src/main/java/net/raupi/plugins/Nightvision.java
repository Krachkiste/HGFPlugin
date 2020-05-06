package net.raupi.plugins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Nightvision implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try {

            Player player = Bukkit.getPlayer(args[0]);
            if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            } else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 255, true));
            }

        } catch (NullPointerException|ArrayIndexOutOfBoundsException e){
            if(!(sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + "Bitte Spielernamen eingeben!");
            } else if(sender instanceof Player){
                Player player = (Player) sender;
                if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                } else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 255, true));
                }
            }
        }

        return false;
    }

}
