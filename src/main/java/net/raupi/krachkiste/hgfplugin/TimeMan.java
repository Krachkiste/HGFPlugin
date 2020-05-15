package net.raupi.krachkiste.hgfplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TimeMan implements CommandExecutor, Listener {
    private ItemStack day;
    private ItemStack noon;
    private ItemStack night;
    private Inventory inv;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player && command.getName().equalsIgnoreCase("times")){
            openGui((Player) sender);
        }
        return false;
    }

    @EventHandler
    public void onInv(InventoryClickEvent e){
        if(e.getCurrentItem().equals(HGFPlugin.back)){
            ((Player) e.getWhoClicked()).performCommand("hgf");
        }
        if(e.getClickedInventory().equals(inv)){
            if (e.getCurrentItem().equals(day)){
                e.getWhoClicked().getWorld().setTime(23460);
            }
            if (e.getCurrentItem().equals(noon)){
                e.getWhoClicked().getWorld().setTime(6000);
            }
            if (e.getCurrentItem().equals(night)){
                e.getWhoClicked().getWorld().setTime(12040);
            }
            e.setCancelled(true);
        }
    }

    public void openGui(Player p) {

        inv = Bukkit.createInventory(p, 9, "HGF Zeiteinstellungen");

        day = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta day_mt = day.getItemMeta();
        day_mt.setDisplayName(ChatColor.GOLD + "Tag");
        day.setItemMeta(day_mt);

        inv.setItem(1, day);

        noon = new ItemStack(Material.SUNFLOWER);
        ItemMeta noon_mt = noon.getItemMeta();
        noon_mt.setDisplayName(ChatColor.WHITE + "Mittag");
        noon.setItemMeta(noon_mt);

        inv.setItem(3, noon);

        night = new ItemStack(Material.BLACK_CONCRETE);
        ItemMeta night_mt = night.getItemMeta();
        night_mt.setDisplayName(ChatColor.DARK_AQUA + "Nacht");
        night.setItemMeta(night_mt);

        inv.setItem(7, HGFPlugin.skull);
        inv.setItem(8, HGFPlugin.back);

        p.openInventory(inv);

        inv.setItem(5, night);

    }
}
