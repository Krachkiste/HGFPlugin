package net.raupi.krachkiste.hgfplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Kits implements CommandExecutor, Listener {

    public ItemStack kit1;
    public ItemStack kit2;
    public ItemStack kit3;
    private Inventory inv;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player && command.getName().equalsIgnoreCase("baukit")){

            openKits((Player) sender);

        }

        return false;

    }

    @EventHandler
    public void onInventory(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();

        if(event.getClickedInventory().equals(HGFPlugin.inv)){
            return;
        }

        if(event.getCurrentItem().equals(HGFPlugin.back)){
            ((Player) event.getWhoClicked()).performCommand("hgf");
        }

        if(event.getClickedInventory().equals(inv)){

            if(event.getCurrentItem().equals(kit1)){

                player.getInventory().setItem(0, new ItemStack(Material.BRICKS));
                player.getInventory().setItem(1, new ItemStack(Material.SMOOTH_SANDSTONE));
                player.getInventory().setItem(2, new ItemStack(Material.GLASS));
                player.getInventory().setItem(3, new ItemStack(Material.OAK_PLANKS));
                player.getInventory().setItem(4, new ItemStack(Material.WHITE_CONCRETE));
                player.getInventory().setItem(5, new ItemStack(Material.STONE_BRICKS));
                player.getInventory().setItem(6, new ItemStack(Material.STONE_BRICK_SLAB));
                player.getInventory().setItem(7, new ItemStack(Material.POLISHED_ANDESITE));
                player.getInventory().setItem(8, new ItemStack(Material.STONE));

            }

            if(event.getCurrentItem().equals(kit2)){

                player.getInventory().setItem(0, new ItemStack(Material.OAK_LOG));
                player.getInventory().setItem(1, new ItemStack(Material.COARSE_DIRT));
                player.getInventory().setItem(2, new ItemStack(Material.GRASS_BLOCK));
                player.getInventory().setItem(3, new ItemStack(Material.PODZOL));
                player.getInventory().setItem(4, new ItemStack(Material.SPRUCE_TRAPDOOR));
                player.getInventory().setItem(5, new ItemStack(Material.GRAVEL));
                player.getInventory().setItem(6, new ItemStack(Material.SPRUCE_SLAB));
                player.getInventory().setItem(7, new ItemStack(Material.OAK_WOOD));
                player.getInventory().setItem(8, new ItemStack(Material.OAK_LEAVES));

            }

            if(event.getCurrentItem().equals(kit3)){

                player.getInventory().setItem(0, new ItemStack(Material.GLOWSTONE));
                player.getInventory().setItem(1, new ItemStack(Material.NETHER_BRICK_FENCE));
                player.getInventory().setItem(2, new ItemStack(Material.GLASS_PANE));
                player.getInventory().setItem(3, new ItemStack(Material.LIGHT_GRAY_CARPET));
                player.getInventory().setItem(4, new ItemStack(Material.STONE_BUTTON));
                player.getInventory().setItem(5, new ItemStack(Material.BLACK_CONCRETE));
                player.getInventory().setItem(6, new ItemStack(Material.REDSTONE_BLOCK));
                player.getInventory().setItem(7, new ItemStack(Material.ITEM_FRAME));
                player.getInventory().setItem(8, new ItemStack(Material.POLISHED_ANDESITE));

            }

            event.setCancelled(true);

        }

    }

    public void openKits(HumanEntity entity) {

        inv = Bukkit.createInventory(entity, 27, "HGF Bau Kits");

        kit1 = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta mt1 = kit1.getItemMeta();
        mt1.setDisplayName("Kit 1");
        ArrayList<String> lore1 = new ArrayList<String>();
        lore1.add("Baukit 1 - Altbau Baukit");
        mt1.setLore(lore1);
        kit1.setItemMeta(mt1);

        kit2 = new ItemStack(Material.COBBLESTONE);
        ItemMeta mt2 = kit2.getItemMeta();
        mt2.setDisplayName("Kit 2");
        ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add("Baukit 2 - ");
        mt2.setLore(lore2);
        kit2.setItemMeta(mt2);

        kit3 = new ItemStack(Material.SMOOTH_STONE);
        ItemMeta mt3 = kit3.getItemMeta();
        mt3.setDisplayName("Kit 3");
        ArrayList<String> lore3 = new ArrayList<String>();
        lore3.add("Baukit 3 - Auto Baukit");
        mt3.setLore(lore3);
        kit3.setItemMeta(mt3);

        inv.setItem(11, kit1);
        inv.setItem(13, kit2);
        inv.setItem(15, kit3);
        inv.setItem(25, HGFPlugin.skull);
        inv.setItem(26, HGFPlugin.back);

        entity.openInventory(inv);

    }

}
