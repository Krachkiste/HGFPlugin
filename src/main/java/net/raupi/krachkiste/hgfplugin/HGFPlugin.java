package net.raupi.krachkiste.hgfplugin;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.UUID;

public final class HGFPlugin extends JavaPlugin implements Listener {

    public static Inventory inv;
    private ItemStack regeln;
    private ItemStack todo_itm;
    private ItemStack web;
    private ItemStack end;
    private ItemStack tp;
    private ItemStack nv;
    private ItemStack baukit;
    private ItemStack einst;
    public static ItemStack skull;
    private ItemStack home;
    private ItemStack page2;
    private ItemStack close;
    public static ItemStack back;
    private EffectCMD effectMan = new EffectCMD();
    private TimeMan timeMan = new TimeMan();
    private Kits kits = new Kits();
    private Settings sett = new Settings();
    private Todos todocmd;
    private Inventory inv2;
    private ItemStack page1;
    private ItemStack page3;
    private Inventory inv3;
    private ItemStack page2_;
    private ItemStack effects;
    private ItemStack time;

    static{
        skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta mt8 = (SkullMeta) skull.getItemMeta();
        mt8.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString("16cc60d9-094c-49a4-8e07-47d581369360")));
        mt8.setDisplayName(ChatColor.LIGHT_PURPLE + "Krachkiste");
        ArrayList<String> ary1 = new ArrayList<String>();
        ary1.add("Plugin Creator");
        ary1.add("#hgf-plugin");
        mt8.setLore(ary1);
        skull.setItemMeta(mt8);

        back = new ItemStack(Material.TIPPED_ARROW);
        ItemMeta back_mt = back.getItemMeta();
        back_mt.setDisplayName("Zurück zum HGF Menü");
        ArrayList<String> bck = new ArrayList<String>();
        bck.add("Zurück zum HGF-Haupmenü");
        back_mt.setLore(bck);
        back.setItemMeta(back_mt);
        back.setItemMeta(back_mt);
    }

    public String getFileString(){
        return this.getDataFolder().getParentFile().getAbsolutePath();
    }

    @Override
    public void onEnable() {

        todocmd = new Todos(getFileString());

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(kits, this);
        getServer().getPluginManager().registerEvents(todocmd, this);
        getServer().getPluginManager().registerEvents(effectMan, this);
        getServer().getPluginManager().registerEvents(timeMan, this);
        getCommand("effect").setExecutor(effectMan);
        getCommand("settings").setExecutor(sett);
        getCommand("todos").setExecutor(todocmd);
        getCommand("todoadd").setExecutor(todocmd);
        getCommand("todoremove").setExecutor(todocmd);
        getCommand("todoremove").setTabCompleter(todocmd);
        getCommand("baukit").setExecutor(kits);
        getCommand("times").setExecutor(timeMan);

        skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta mt8 = (SkullMeta) skull.getItemMeta();
        mt8.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString("16cc60d9-094c-49a4-8e07-47d581369360")));
        mt8.setDisplayName(ChatColor.LIGHT_PURPLE + "Krachkiste");
        ArrayList<String> ary1 = new ArrayList<String>();
        ary1.add("Plugin Creator");
        ary1.add("#hgf-plugin");
        mt8.setLore(ary1);
        skull.setItemMeta(mt8);

        back = new ItemStack(Material.TIPPED_ARROW);
        ItemMeta back_mt = back.getItemMeta();
        back_mt.setDisplayName("Zurück zum HGF Menü");
        ArrayList<String> bck = new ArrayList<String>();
        bck.add("Zurück zum HGF-Haupmenü");
        back_mt.setLore(bck);
        back.setItemMeta(back_mt);
        back.setItemMeta(back_mt);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, effectMan.getEffectCheckingThread(), 0, Long.MAX_VALUE);

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("hgf")){

            if(sender instanceof Player){

                ((Player) sender).openInventory(inv);

            }

        }

        return false;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();
        player.sendTitle(ChatColor.GREEN + "Season 2 eröffnet", ChatColor.GRAY + "für mehr Infos in #announcments sehen!", 35, 60, 35);

        inv = Bukkit.createInventory(player, 54, "HGF Plugin");

        regeln = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta mt1 = regeln.getItemMeta();
        mt1.setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_RED + "Bau-Regeln");
        ArrayList<String> ar1= new ArrayList<String>();
        ar1.add("Regeln");
        mt1.setLore(ar1);
        regeln.setItemMeta(mt1);

        todo_itm = new ItemStack(Material.NAME_TAG);
        ItemMeta mt2 = todo_itm.getItemMeta();
        mt2.setDisplayName(ChatColor.DARK_GREEN + "Todos");
        ar1.clear();
        ar1.add("Todos");
        mt2.setLore(ar1);
        todo_itm.setItemMeta(mt2);

        web = new ItemStack(Material.COMPASS);
        ItemMeta mt3 = web.getItemMeta();
        mt3.setDisplayName(ChatColor.AQUA + "Webseite");
        ar1.clear();
        ar1.add("Webseite");
        mt3.setLore(ar1);
        web.setItemMeta(mt3);

        end = new ItemStack(Material.ENDER_CHEST);
        ItemMeta mt4 = end.getItemMeta();
        mt4.setDisplayName(ChatColor.BLACK + "Endertruhe");
        ar1.clear();
        ar1.add("Enderchest");
        mt4.setLore(ar1);
        end.setItemMeta(mt4);

        tp = new ItemStack(Material.DARK_OAK_DOOR);
        ItemMeta mt5 = tp.getItemMeta();
        mt5.setDisplayName(ChatColor.WHITE + "" + ChatColor.UNDERLINE + "Zurück zum HGF Pausenhof");
        ar1.clear();
        ar1.add("Teleport");
        mt5.setLore(ar1);
        tp.setItemMeta(mt5);

        nv = new ItemStack(Material.BEACON);
        ItemMeta mt6 = nv.getItemMeta();
        mt6.setDisplayName(ChatColor.BLUE + "Nightvision An/Aus");
        ar1.clear();
        ar1.add("Nightvision");
        mt6.setLore(ar1);
        nv.setItemMeta(mt6);

        baukit = new ItemStack(Material.CHEST);
        ItemMeta mt7 = baukit.getItemMeta();
        mt7.setDisplayName(ChatColor.RED + "Baukit select");
        ar1.clear();
        ar1.add("Baukits");
        mt7.setLore(ar1);
        baukit.setItemMeta(mt7);

        einst = new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta mt8 = einst.getItemMeta();
        mt8.setDisplayName(ChatColor.LIGHT_PURPLE + "Einstellungen");
        ar1.clear();
        ar1.add("Einstellungen");
        mt8.setLore(ar1);
        einst.setItemMeta(mt8);

        effects = new ItemStack(Material.POTION);
        ItemMeta mt90 = effects.getItemMeta();
        mt90.setDisplayName(ChatColor.BLUE + "Effects");
        ar1.clear();
        ar1.add("Effect Manager");
        mt90.setLore(ar1);
        effects.setItemMeta(mt90);

        time = new ItemStack(Material.CLOCK);
        ItemMeta mt900 = time.getItemMeta();
        mt900.setDisplayName("Zeiteinstellungen");
        ar1.clear();
        ar1.add("Time Manager");
        mt900.setLore(ar1);
        time.setItemMeta(mt900);

        home = new ItemStack(Material.EMERALD);
        ItemMeta mt9 = home.getItemMeta();
        mt9.setDisplayName(ChatColor.GREEN + "Home");
        ar1.clear();
        ar1.add("Seite 1");
        mt9.setLore(ar1);
        home.setItemMeta(mt9);

        close = new ItemStack(Material.BARRIER);
        ItemMeta mtt = close.getItemMeta();
        mtt.setDisplayName(ChatColor.RED + "Schließen");
        ar1.clear();
        ar1.add("Schließen");
        mtt.setLore(ar1);
        close.setItemMeta(mtt);

        page2 = new ItemStack(Material.SPECTRAL_ARROW);
        ItemMeta mttt = page2.getItemMeta();
        mttt.setDisplayName(ChatColor.GREEN + "Nächste Seite >");
        ar1.clear();
        ar1.add("Seite 2");
        mttt.setLore(ar1);
        page2.setItemMeta(mttt);

        page2_ = new ItemStack(Material.ARROW);
        ItemMeta mttt_ = page2_.getItemMeta();
        mttt_.setDisplayName(ChatColor.GREEN + "< Zurück");
        ar1.clear();
        ar1.add("Seite 2");
        mttt_.setLore(ar1);
        page2_.setItemMeta(mttt_);

        inv.setItem(10, tp);
        inv.setItem(12, nv);
        inv.setItem(14, baukit);
        inv.setItem(16, einst);
        inv.setItem(28, web);
        inv.setItem(30, regeln);
        inv.setItem(32, todo_itm);
        inv.setItem(34, end);
        inv.setItem(45, skull);
        inv.setItem(49, home);
        inv.setItem(50, page2);
        inv.setItem(53, close);

        page1 = new ItemStack(Material.ARROW);
        ItemMeta pg1_mt = page1.getItemMeta();
        pg1_mt.setDisplayName("< Zurück");
        ar1.clear();
        ar1.add("Seite 1");
        pg1_mt.setLore(ar1);
        page1.setItemMeta(pg1_mt);


        page3 = new ItemStack(Material.SPECTRAL_ARROW);
        ItemMeta pg3_mt = page3.getItemMeta();
        pg3_mt.setDisplayName("Nächste Seite >");
        ar1.clear();
        ar1.add("Seite 3");
        pg3_mt.setLore(ar1);
        page3.setItemMeta(pg3_mt);

        inv2 = Bukkit.createInventory(player, 54, "HGF Plugin");
        inv2.setItem(10, time);
        inv2.setItem(12, effects);
        inv2.setItem(49, home);
        inv2.setItem(48, page1);
        inv2.setItem(50, page3);

        inv3 = Bukkit.createInventory(player, 54, "HGF Plugin");
        inv3.setItem(49, home);
        inv3.setItem(48, page2_);

    }

    @EventHandler
    public void onItem(InventoryClickEvent e){

        if(e.getClickedInventory().equals(inv) || e.getClickedInventory().equals(inv2) || e.getClickedInventory().equals(inv3)){

            if(e.getCurrentItem().equals(tp)){

                e.getWhoClicked().teleport(new Location(e.getWhoClicked().getWorld(), -94, 63, -282));

            }

            if(e.getCurrentItem().equals(nv)){

                effectMan.setNightvision((Player) e.getWhoClicked());

            }

            if(e.getCurrentItem().equals(effects)){

                effectMan.onEffects((Player) e.getWhoClicked());

            }

            if(e.getCurrentItem().equals(baukit)){

                kits.openKits(e.getWhoClicked());

            }

            if(e.getCurrentItem().equals(time)){

                timeMan.openGui((Player) e.getWhoClicked());

            }

            if(e.getCurrentItem().equals(einst)){

                sett.openSettings(e.getWhoClicked());

            }

            if(e.getCurrentItem().equals(web)){

                e.getWhoClicked().spigot().sendMessage(new ComponentBuilder("Zur HGF Webseite").color(net.md_5.bungee.api.ChatColor.RED).bold(true).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://minecraft.hg-f.xyz")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Drücken, um zu minecraft.hg-f.xyz weitergeleitet werden.").color(net.md_5.bungee.api.ChatColor.GOLD).create())).create());

            }

            if(e.getCurrentItem().equals(einst)){

                sett.openSettings(e.getWhoClicked());

            }

            if(e.getCurrentItem().equals(regeln)){

                //TODO: Regel Code schreiben! (config ??)
                e.getWhoClicked().sendMessage("Feature in Entwicklung :3");

            }

            if(e.getCurrentItem().equals(todo_itm)){

                ((Player) e.getWhoClicked()).performCommand("todo");

            }

            if(e.getCurrentItem().equals(einst)){

                sett.openSettings(e.getWhoClicked());

            }

            if(e.getCurrentItem().equals(end)){

                e.getWhoClicked().openInventory(e.getWhoClicked().getEnderChest());
                e.setCancelled(false);

            }

            if(e.getCurrentItem().equals(close)){

                e.getWhoClicked().closeInventory();

            }

            if(e.getCurrentItem().equals(page2)){

                e.getWhoClicked().openInventory(inv2);

            }

            if(e.getCurrentItem().equals(page2_)){

                e.getWhoClicked().openInventory(inv2);

            }

            if(e.getCurrentItem().equals(page1)){

                e.getWhoClicked().openInventory(inv);

            }

            if(e.getCurrentItem().equals(page3)){

                e.getWhoClicked().openInventory(inv3);

            }

            if(e.getCurrentItem().equals(home)){

                ((Player) e.getWhoClicked()).openInventory(inv);

            }

            e.setCancelled(true);

        }

    }

    @Override
    public void onDisable() {

        todocmd.onSave();

    }
}
