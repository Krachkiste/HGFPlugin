package net.raupi.krachkiste.hgfplugin;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EffectCMD implements CommandExecutor, Listener {

    public ArrayList<Player> night = new ArrayList<Player>();

    public ArrayList<Player> jump = new ArrayList<Player>();

    public ArrayList<Player> strength = new ArrayList<Player>();

    public ArrayList<Player> speed = new ArrayList<Player>();

    private Inventory inv;
    private ItemStack jmp;
    private ItemStack sp;
    private ItemStack st;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player && command.getName().equalsIgnoreCase("nv")){

            night.add((Player) sender);
            ((Player) sender).addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 255, false));

        }

        if(sender instanceof Player && command.getName().equalsIgnoreCase("jm")){

            jump.add((Player) sender);
            ((Player) sender).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 255, false));

        }

        if(sender instanceof Player && command.getName().equalsIgnoreCase("strength")){

            strength.add((Player) sender);

        }

        if(sender instanceof Player && command.getName().equalsIgnoreCase("speed")){

            speed.add((Player) sender);

        }

        return false;

    }

    public void onEffects(Player p){

        inv = Bukkit.createInventory(p, 27, "HGF Effects");

        jmp = new ItemStack(Material.RABBIT_FOOT);
        ItemMeta jmp_mt = jmp.getItemMeta();
        jmp_mt.setDisplayName("Jump Boost");
        ArrayList<String> lore = new ArrayList<String>();
        lore.clear();
        lore.add("HGF Effect");
        jmp_mt.setLore(lore);
        jmp.setItemMeta(jmp_mt);

        sp = new ItemStack(Material.SUGAR);
        ItemMeta sp_mt = sp.getItemMeta();
        sp_mt.setDisplayName("Speed");
        lore.clear();
        lore.add("HGF Effect");
        sp_mt.setLore(lore);
        sp.setItemMeta(sp_mt);

        st = new ItemStack(Material.IRON_SWORD);
        ItemMeta st_mt = sp.getItemMeta();
        st_mt.setDisplayName("Strength");
        lore.clear();
        lore.add("HGF Effect");
        st_mt.setLore(lore);
        st.setItemMeta(st_mt);

        inv.setItem(10, jmp);
        inv.setItem(12, sp);
        inv.setItem(14, st);
        inv.setItem(25, HGFPlugin.skull);
        inv.setItem(26, HGFPlugin.back);

        p.openInventory(inv);

    }

    public void setNightvision(Player entity) {

        entity.performCommand("nv");

    }

    @EventHandler
    public void onInventory(InventoryClickEvent e){

        if(e.getCurrentItem().equals(HGFPlugin.back)){
            ((Player) e.getWhoClicked()).performCommand("hgf");
        }

        if(e.getClickedInventory().equals(inv)){

            if(e.getCurrentItem().equals(jmp)){

                jump.add((Player) e.getWhoClicked());
                e.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 255, false));

            }

            if(e.getCurrentItem().equals(st)){

                strength.add((Player) e.getWhoClicked());
                ((Player) e.getWhoClicked()).performCommand("effect give " + e.getWhoClicked().getName() + " minecraft:strength 999999 255 false");

            }

            if(e.getCurrentItem().equals(sp)){

                speed.add((Player) e.getWhoClicked());
                e.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 3, false));

            }

            e.setCancelled(true);

        };

    }

    public Thread getEffectCheckingThread(){

        Thread th = new Thread() {
            @Override
            public void run() {

                Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[] {});

                for (int i = 0; i<= players.length-1; i++) {

                    if(speed.contains(players[i])){
                        if(!players[i].hasPotionEffect(PotionEffectType.SPEED)){
                            players[i].addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 3, false));
                        }
                    }

                    if(night.contains(players[i])){
                        if(!players[i].hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                            players[i].addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 255, false));
                        }
                    }

                    if(jump.contains(players[i])){
                        if(!players[i].hasPotionEffect(PotionEffectType.JUMP)){
                            players[i].addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 255, false));
                        }
                    }

                    if(strength.contains(players[i])){
                        if(true){
                            players[i].performCommand("effect give " + players[i].getName() + " minecraft:strength 999999 255 false");
                        }
                    }

                }

            }
        };

        return th;

    }

}
