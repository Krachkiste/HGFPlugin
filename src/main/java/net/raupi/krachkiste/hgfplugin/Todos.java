package net.raupi.krachkiste.hgfplugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Todos implements CommandExecutor, TabCompleter, Listener {

    public static String filedir;
    private ArrayList<String> todos  = new ArrayList<String>();
    private Inventory tds;

    private Todos(){}

    public Todos(String dir){
        filedir = dir;
        File f = new File(filedir + "\\HGFPlugin-Todos.dat");
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String ln = bfr.readLine();
            todos.add(ln);
            while(ln != null){
                ln = bfr.readLine();
                todos.add(ln);
            }
            bfr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player && command.getName().equalsIgnoreCase("todos")){
            onTodos((HumanEntity) sender);
        }

        if(sender instanceof Player && command.getName().equalsIgnoreCase("todoadd")){
            String td = "";
            for(int i = 0; i<= args.length-1; i++){
                td = td + " " + args[i];
            }
            td = td.substring(1);
            todos.add(td);
        }

        if(sender instanceof Player && command.getName().equalsIgnoreCase("todoremove")){
            String td = "";
            for(int i = 0; i<= args.length-1; i++){
                td = td + " " + args[i];
            }
            td = td.substring(1);
            Bukkit.broadcastMessage(ChatColor.GOLD + "[TODOManager@KrachkisteSERV]: " + sender.getName() + " hat das TODO \"" + td + "\" als erledigt gesetzt.");
            todos.remove(td);
            for(int i = 0; i <= Bukkit.getServer().getOnlinePlayers().size()-1; i++){
                Bukkit.getServer().getOnlinePlayers().toArray(new Player[]{})[i].playSound(Bukkit.getServer().getOnlinePlayers().toArray(new Player[]{})[i].getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 255, (float) 1);
            }
        }

        if(sender instanceof Player && command.getName().equalsIgnoreCase("altbau")){
            ((Player) sender).performCommand("warp Altbau");
        }

        return false;

    }

    @EventHandler
    public void onItem(InventoryClickEvent e){

        if(e.getCurrentItem().equals(HGFPlugin.back)){
            ((Player) e.getWhoClicked()).performCommand("hgf");
        }

        if(e.getClickedInventory().equals(tds)){
            e.setCancelled(true);
        }

    }

    public void onTodos(HumanEntity e){


        tds = Bukkit.createInventory(e, 27, "Todos");

        for(int i = 0;i <= todos.size()-1; i++){

            if(todos.get(i) == null || todos.get(i).equals("null") || todos.get(i).equals("")){
                continue;
            }

            ItemStack paper = new ItemStack(Material.MAGMA_CREAM);
            ItemMeta paper_meta = paper.getItemMeta();
            paper_meta.setDisplayName(ChatColor.UNDERLINE + todos.get(i).replaceFirst(" ", "> ").replace('ë', '\u00EB').replace('ä', '\u00E4').replace('ü', '\u00FC'));
            paper.setItemMeta(paper_meta);
            tds.addItem(paper);

        }

        tds.setItem(25, HGFPlugin.skull);
        tds.setItem(26, HGFPlugin.back);

        e.openInventory(tds);

    }

    @EventHandler
    public void onInventory(InventoryClickEvent e){

        if(e.getClickedInventory().equals(tds)){
            e.setCancelled(true);
        }

    }

    public void onSave(){

        try {

            PrintWriter pw = new PrintWriter(new FileWriter(new File(filedir + "\\HGFPlugin-Todos.dat")));

            for(int i = 0; i <= todos.size()-1; i++){

                pw.println(todos.get(i));

            }

            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(command.getName().equalsIgnoreCase("todoremove")){
            return todos;
        }
        return null;
    }
}
