package net.raupi.plugins;


//import net.md_5.bungee.api.chat.ClickEvent;
//import net.md_5.bungee.api.chat.TextComponent;
import jdk.jfr.Experimental;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;
import org.bukkit.potion.PotionType;

import java.util.*;

public final class HGFPlugin extends JavaPlugin implements Listener, TabCompleter {

    private ItemStack navigation;
    private ItemStack book;
    private ItemStack home;
    private ItemStack arrow;
    private ItemStack compass;
    private ItemStack map;
    private ItemStack chest;
    private ItemStack todo;
    private BookMeta todo_meta;
    private ItemStack rules;
    private BookMeta rules_meta;
    private Inventory nav;
    private ItemStack skull;
    private ItemStack todo2;
    private BookMeta todo2_meta;
    private Player jp;
    private TextComponent[] components;
    private String[] todos_str = {"Altbau: Fenster an der Ostseite", "Altbau: Fenster an der Westseite", "Pflanzen/Bäume im Schulgarten", "Straßen rund um das HGF", "Gebäudefassaden rund um das HGF", "Einrichtung Sekretariat/Direktorat", "Einrichtung Kunstsäle", "Altbau: Räume Keller (Grundriss siehe #pläne_fotos )"};
    private CommandSender cms;
    private FileConfiguration config;
    private ItemStack todo_book;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        config = getConfig();
        config.addDefault("Todos", new String[] {"Todo1", "Todo 2"});

        String todos_str_uncut = (String) config.get("Todos");

        todos_str = todos_str_uncut.split(", ");

        navigation = new ItemStack(Material.NETHER_STAR);
        ItemMeta nmeta = navigation.getItemMeta();
        nmeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "HGF - Navigation");
        ArrayList<String> ary1 = new ArrayList<String>();
        ary1.add(ChatColor.LIGHT_PURPLE + "Nutze dieses Item, um im Server zu navigieren!");
        ary1.add("Für Infos:" + ChatColor.RED + "" + ChatColor.BOLD + " /hgf oder /todo !");
        ary1.add(ChatColor.LIGHT_PURPLE + "Benutze dies, um das Menü zu öffnen!");
        nmeta.setLocalizedName(ChatColor.AQUA + "" + ChatColor.BOLD + "HGF - Menü");
        nmeta.setLore(ary1);
        navigation.setItemMeta(nmeta);
        System.out.println("HGF Plugin by Krachkiste enabled!");
        getServer().getPluginManager().registerEvents(this, this);

        todo = new ItemStack(Material.WRITTEN_BOOK);
        todo_meta = (BookMeta) todo.getItemMeta();
        todo_meta.setTitle("HGF Server Bauaufgaben");
        todo_meta.setAuthor("HGF Plugin by Krachkiste");
        todo_meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_RED + "Aufgaben");

        components = new TextComponent[todos_str.length];

        for(int i = 0; i <= todos_str.length-1; i++){
            TextComponent txt = new TextComponent("\u2718 " + todos_str[i] + "\n");
            txt.setColor(net.md_5.bungee.api.ChatColor.RED);
            txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/todoremoveinternal " + todos_str[i].replaceAll(" ", "!")));
            txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("/todoremoveinternal " +  todos_str[i] + " (OP)").create()));
            components[i] = txt;
            System.out.println(todos_str[i]);
        }

        todo_meta.spigot().setPages(components, new TextComponent[] {components[components.length-1]});

        /*ArrayList<TextComponent>[] components2 = new ArrayList[49];

        int ii = 0;
        int useMe = 0;

        /*for(int i = 0; i <= components.length; i++){

            if(useMe >= 256|| (256 - (useMe + components[i].getText().length()) > 256)){
                ii++;
                useMe = 0;
                System.out.println("-------------------------------");
            }

            System.out.println("Todo: " + components[i].getText());

            useMe = useMe + components[i].getText().length();

            components2[ii].add(components[i]);

        }

        for(int i = 0; i <= components2.length; i++){

            todo_meta.spigot().addPage(components2[i].toArray(new TextComponent[] {}));

        }*/

    System.out.println("\n _  __               _     _    _     _       \n" +
            "| |/ /              | |   | |  (_)   | |      \n" +
            "| ' / _ __ __ _  ___| |__ | | ___ ___| |_ ___ \n" +
            "|  < | '__/ _` |/ __| '_ \\| |/ / / __| __/ _ \\\n" +
            "| . \\| | | (_| | (__| | | |   <| \\__ \\ ||  __/\n" +
            "|_|\\_\\_|  \\__,_|\\___|_| |_|_|\\_\\_|___/\\__\\___|\n" +
            "                                              ");

        skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull_meta = (SkullMeta) skull.getItemMeta();
        skull_meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString("16cc60d9-094c-49a4-8e07-47d581369360")));
        skull_meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Plugin Creator: Krachkiste");
        ArrayList<String> ary8 = new ArrayList<String>();
        ary8.add(ChatColor.DARK_GRAY + "Plugin erstellt von Krachkiste");
        ary8.add(ChatColor.DARK_GRAY + "Version 1.5");
        ary8.add(ChatColor.DARK_GRAY + "Bei Vorschlägen nicht zögern, sondern");
        ary8.add(ChatColor.DARK_GRAY + "einfach in den Discord schreiben ;)");
        skull_meta.setLore(ary8);
        skull.setItemMeta(skull_meta);

        todo.setItemMeta(todo_meta);

        todo2 = new ItemStack(Material.WRITTEN_BOOK);
        todo2_meta = (BookMeta) todo2.getItemMeta();
        todo2_meta.setTitle("HGF Server Begrüßung");
        todo2_meta.setAuthor("HGF Plugin by Krachkiste");
        todo2_meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_RED + "Aufgaben");
        todo2_meta.setPages(ChatColor.RED + "" + ChatColor.BOLD + "Wilkommen zum HGF Projekt\nSeason 2 eröffnet!" + ChatColor.BOLD + ChatColor.BLUE + "/hgf!\n" + ChatColor.GREEN + "Das HGF-Bauteam begrüßt dich herzlich auf dem HGF Projektserver!");
        todo2.setItemMeta(todo2_meta);

        rules = new ItemStack(Material.WRITTEN_BOOK);
        rules_meta = (BookMeta) rules.getItemMeta();
        rules_meta.setTitle("HGF Server Bau-Regeln");
        rules_meta.setAuthor("HGF Plugin by Krachkiste");
        /*String rt_tmp = (String) config.get("RTitle");
        String rtitle = rt_tmp.split("; ")[0] + ChatColor.valueOf(rt_tmp.split("; ")[1]);*/
        String aa_tmp = (String) config.get("RegelA");
        String aa = aa_tmp.split("; ")[0] + ChatColor.valueOf(aa_tmp.split("; ")[1]);
        String bb_tmp = (String) config.get("RegelB");
        String bb = bb_tmp.split("; ")[0] + ChatColor.valueOf(bb_tmp.split("; ")[1]);
        String cc_tmp = (String) config.get("RegelC");
        String cc = cc_tmp.split("; ")[0] + ChatColor.valueOf(cc_tmp.split("; ")[1]);
        rules_meta.setPages(ChatColor.BOLD + "" + ChatColor.RED + "Regeln" + ChatColor.RESET + "\n" + ChatColor.GOLD +  aa, ChatColor.GOLD +  bb, ChatColor.GOLD +  cc,  "~ Stand aktuell\n" + ChatColor.LIGHT_PURPLE + "Auto generiert vom HGF Plugin by Krachkiste");
        rules_meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_RED + "Bau-Regeln");
        rules.setItemMeta(rules_meta);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                getServer().getOnlinePlayers().size();
                for(int i = 0;i <= getServer().getOnlinePlayers().size()-1; i++) {
                    try {
                        Player curp = (Player) getServer().getOnlinePlayers().toArray()[i];
                        if(curp.getInventory().containsAtLeast(navigation, 2)){
                            curp.getInventory().remove(navigation);
                            curp.getInventory().setItem(8, navigation);
                        }
                        if(curp.getInventory().contains(arrow)){
                            curp.getInventory().remove(arrow);
                        }
                        if(curp.getInventory().contains(skull)){
                            curp.getInventory().remove(skull);
                        }
                        if(curp.getInventory().contains(home)){
                            curp.getInventory().remove(home);
                        }
                        if(curp.getInventory().contains(compass)){
                            curp.getInventory().remove(compass);
                        }
                        if(curp.getInventory().contains(skull)){
                            curp.getInventory().remove(skull);
                        }
                        if(curp.getInventory().contains(chest)){
                            curp.getInventory().remove(chest);
                        }
                        if(curp.getInventory().contains(map)){
                            curp.getInventory().remove(map);
                        }
                    } catch (NullPointerException e){

                    }

                }
            }
        }, 0, 0);

        getCommand("nightvision").setExecutor(new Nightvision());

    }

    @EventHandler
    public void onClear(PlayerCommandPreprocessEvent e){
        if(e.getMessage().equals("/clear") || e.getMessage().equals("clear")){
            e.setCancelled(true);
            e.getPlayer().getInventory().clear();
            e.getPlayer().getInventory().setItem(8, navigation);
        }
        if(e.getMessage().contains("tnt") || e.getMessage().contains("Tnt") || e.getMessage().contains("TNt") || e.getMessage().contains("TNT") || e.getMessage().contains("tnT") || e.getMessage().contains("tNT")){
            e.setCancelled(true);
            e.getPlayer().getInventory().setItem(8, navigation);
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[HGFPlugin]: Kein TNT auf dem Server, "  + e.getPlayer().getName() + "!");
        }
        if(e.getMessage().contains("/replaceitem entity " + e.getPlayer().getName() + " container.8") || e.getMessage().contains("replaceitem entity " + e.getPlayer().getName() + " container.8") || (e.getMessage().equals("itemrename") && e.getPlayer().getInventory().getItemInMainHand().equals(nav))){
            e.setCancelled(true);
            e.getPlayer().getInventory().setItem(8, navigation);
            e.getPlayer().openInventory(nav);
        }
    }


    @EventHandler
    public void onPlayerIssueCommand(PlayerCommandSendEvent e){
        if(!e.getPlayer().getInventory().getItem(8).equals(navigation)){
            e.getPlayer().getInventory().setItem(8, navigation);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        e.getPlayer().sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "Season 2", ChatColor.GRAY + "Für mehr Infos, besuch den Discord Server des Projekt", 20, 35, 20);

        jp = e.getPlayer();

        e.getPlayer().getInventory().setItem(8, navigation);

        nav = Bukkit.createInventory(e.getPlayer(), 45, "HGF Bauprojekt Navigationsmenü");

        home = new ItemStack(Material.DARK_OAK_DOOR);
        ItemMeta home_meta = home.getItemMeta();
        home_meta.setDisplayName(ChatColor.AQUA + "Zurück zum Hardenberg Bauort");
        ArrayList<String> ary2 = new ArrayList<String>();
        ary2.add(ChatColor.GOLD + "Teleportiert dich zurück.");
        home_meta.setLore(ary2);
        home.setItemMeta(home_meta);
        nav.setItem(19, home);

        arrow = new ItemStack(Material.ARROW);
        ItemMeta arrow_meta = arrow.getItemMeta();
        arrow_meta.setDisplayName(ChatColor.WHITE + "Menü schließen");
        ArrayList<String> ary3 = new ArrayList<String>();
        ary3.add(ChatColor.GRAY + "Schließt das Menü.");
        arrow_meta.setLore(ary3);
        arrow.setItemMeta(arrow_meta);
        nav.setItem(40, arrow);

        compass = new ItemStack(Material.COMPASS);
        ItemMeta compass_meta = compass.getItemMeta();
        compass_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Zur Webseite");
        ArrayList<String> ary4 = new ArrayList<String>();
        ary4.add(ChatColor.DARK_PURPLE + "Bringt dich zur Webseite!");
        compass_meta.setLore(ary4);
        compass.setItemMeta(compass_meta);
        nav.setItem(21, compass);

        map = new ItemStack(Material.FILLED_MAP);
        ItemMeta map_meta = map.getItemMeta();
        map_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Aufgaben");
        ArrayList<String> ary5 = new ArrayList<String>();
        ary5.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "! WICHTIG !");
        ary5.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Dies sind die wichtigsten Aufgaben");
        ary5.add(ChatColor.GREEN + "Wähl dir eine Aufgabe aus und");
        ary5.add(ChatColor.GREEN + "Arbeite daran! Beachte die Regeln!");
        map_meta.setLore(ary5);
        map.setItemMeta(map_meta);
        nav.setItem(23, map);

        chest = new ItemStack(Material.CHEST);
        ItemMeta chest_meta = chest.getItemMeta();
        chest_meta.setDisplayName(ChatColor.YELLOW + "Bau Inventar");
        ArrayList<String> ary6 = new ArrayList<String>();
        ary6.add(ChatColor.LIGHT_PURPLE + "Öffnet das Bau Inventar mit den Standard Blöcken");
        ary6.add(ChatColor.DARK_RED + "(Achtung: Überscheibt dein Inventar!)");
        chest_meta.setLore(ary6);
        chest.setItemMeta(chest_meta);
        nav.setItem(25, chest);

        book = new ItemStack(Material.BOOK);
        ItemMeta book_meta = book.getItemMeta();
        book_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "REGELN");
        ArrayList<String> ary7 = new ArrayList<String>();
        ary7.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "! WICHTIG !");
        ary7.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Dies sind die Hauptregeln");
        ary7.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Lies sie dir bitte aufmerksam durch");
        ary7.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "-> Du kannt bei Verstößen gebannt werden!!!");
        book_meta.setLore(ary7);
        book.setItemMeta(book_meta);
        nav.setItem(44, book);

        nav.setItem(8, skull);

        e.getPlayer().openBook(todo2);

        Player player = e.getPlayer();
        player.sendMessage(ChatColor.LIGHT_PURPLE + "-----------------------------------------------------");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Hallo " + player.getName() + ", hier ist Krachkiste, ich hab ein paar Features hinzugefügt.");
        player.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ", versuch mal /hgf");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Vielleicht hastt du schon das Navigationsmenü in deiner Hotbar bemerkt, öffne es mal um die neuen Features zu sehen.Ich freu mich gerene über dein Feedback zum neuen HGF Plugin, bitte schreib sie gerene in #hgf-plugin im Discord Server. Wenn du Ideen hastt für neue Features oder Commands, dann ist das noch besser, schreib sie ohne zu zögern gerne in #hgf-plugin.");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "-----------------------------------------------------");
    }

    /**@EventHandler
    public void onPlayerRightClickItem(PlayerInteractEvent e) {

        if(e.getPlayer().getOpenInventory().getTitle().equals("HGF Bauprojekt Navigationsmenü")) {

            if (e.getItem().equals(navigation)) {

                Player player = (Player) e.getPlayer();

                player.openInventory(nav);

                e.setCancelled(true);
            }

            if (e.getItem().equals(skull)) {
                e.setCancelled(true);
            }

            if (e.getItem().equals(home)) {

                Player player = (Player) e.getPlayer();
                player.teleport(new Location(player.getLocation().getWorld(), -97, 63, -271));

                e.setCancelled(true);
            }

            if (e.getItem().equals(arrow)) {

                Player player = (Player) e.getPlayer();
                player.closeInventory();
                player.teleport(player.getLocation());

                e.setCancelled(true);
            }

            if (e.getItem().equals(compass)) {

                Player player = (Player) e.getPlayer();

                TextComponent message = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + "Drücke hier, um die Hardenberg-Gymnasium Bauprojekt Webseite zu öffnen");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://minecraft.hg-f.xyz"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Drücke, um zu minecraft.hg-f.xyz zu gelangen.\n(Projektwebseite)")}));
                player.spigot().sendMessage(message);

                e.setCancelled(true);
            }

            if (e.getItem().equals(map)) {

                Player player = (Player) e.getPlayer();

                player.openBook(todo);

                e.setCancelled(true);
            }

            if (e.getItem().equals(chest)) {

                Player player = (Player) e.getPlayer();

                player.getInventory().clear();

                player.getInventory().setItem(0, new ItemStack(Material.POLISHED_ANDESITE));
                player.getInventory().setItem(1, new ItemStack(Material.BRICKS));
                player.getInventory().setItem(2, new ItemStack(Material.SMOOTH_SANDSTONE));
                player.getInventory().setItem(3, new ItemStack(Material.SMOOTH_STONE));
                player.getInventory().setItem(4, new ItemStack(Material.GRAY_CONCRETE));
                player.getInventory().setItem(5, new ItemStack(Material.LIGHT_GRAY_CONCRETE));
                player.getInventory().setItem(6, new ItemStack(Material.DARK_OAK_PLANKS));
                player.getInventory().setItem(7, new ItemStack(Material.SMOOTH_QUARTZ));
                player.getInventory().setItem(8, navigation);

                e.setCancelled(true);
            }

            if (e.getItem().equals(book)) {

                Player player = (Player) e.getPlayer();

                player.openBook(rules);

                e.setCancelled(true);
            }

            e.setCancelled(true);

        }

    }**/

    @EventHandler
    public void onItemClick(InventoryClickEvent e){

        Player ply = (Player) e.getWhoClicked();

        if (e.getCurrentItem().equals(navigation)) {

            Player player = (Player) e.getWhoClicked();

            player.openInventory(nav);

            e.setCancelled(true);
        }

        if(ply.getOpenInventory().getTitle().equals("HGF Bauprojekt Navigationsmenü") || ply.getOpenInventory().getTopInventory().equals(nav)) {

            if (e.getCurrentItem().equals(navigation)) {

                Player player = (Player) e.getWhoClicked();

                player.openInventory(nav);

                e.setCancelled(true);
            }

            if (e.getCurrentItem().equals(skull)) {
                e.setCancelled(true);
            }

            if (e.getCurrentItem().equals(home)) {

                Player player = (Player) e.getWhoClicked();
                player.teleport(new Location(player.getLocation().getWorld(), -97, 63, -271));

                e.setCancelled(true);
            }

            if (e.getCurrentItem().equals(arrow)) {

                Player player = (Player) e.getWhoClicked();
                player.closeInventory();
                player.teleport(player.getLocation());

                e.setCancelled(true);
            }

            if (e.getCurrentItem().equals(compass)) {

                Player player = (Player) e.getWhoClicked();

                TextComponent message = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + "Drücke hier, um die Hardenberg-Gymnasium Bauprojekt Webseite zu öffnen");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://minecraft.hg-f.xyz"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Drücke, um zu minecraft.hg-f.xyz zu gelangen.\n(Projektwebseite)")}));
                player.spigot().sendMessage(message);

                e.setCancelled(true);
            }

            if (e.getCurrentItem().equals(map)) {

                Player player = (Player) e.getWhoClicked();

                player.performCommand("todo");

                e.setCancelled(true);
            }

            if (e.getCurrentItem().equals(chest)) {

                Player player = (Player) e.getWhoClicked();

                player.getInventory().clear();

                player.getInventory().setItem(0, new ItemStack(Material.POLISHED_ANDESITE));
                player.getInventory().setItem(1, new ItemStack(Material.BRICKS));
                player.getInventory().setItem(2, new ItemStack(Material.SMOOTH_SANDSTONE));
                player.getInventory().setItem(3, new ItemStack(Material.SMOOTH_STONE));
                player.getInventory().setItem(4, new ItemStack(Material.GRAY_CONCRETE));
                player.getInventory().setItem(5, new ItemStack(Material.LIGHT_GRAY_CONCRETE));
                player.getInventory().setItem(6, new ItemStack(Material.DARK_OAK_PLANKS));
                player.getInventory().setItem(7, new ItemStack(Material.SMOOTH_QUARTZ));
                player.getInventory().setItem(8, navigation);

                e.setCancelled(true);
            }

            if (e.getCurrentItem().equals(book)) {

                Player player = (Player) e.getWhoClicked();

                player.openBook(rules);

                e.setCancelled(true);
            }

            e.setCancelled(true);

        }

    }

    @EventHandler
    public void onTNTMinecart(EntitySpawnEvent e){
        if(e.getEntityType().equals(EntityType.MINECART_TNT) || e.getEntityType().equals(EntityType.PRIMED_TNT)){
            e.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[HGFPlugin]: Kein TNT auf dem Server!");
        }
    }

    @EventHandler
    public void onItemHotbarMove(InventoryMoveItemEvent e){
        if(e.getItem().equals(navigation)){

            Player player = (Player) e.getSource().getHolder();

            player.openInventory(nav);

            e.setCancelled(true);

        }

    }

    @EventHandler
    public void onClearEvent(PlayerCommandSendEvent e) {
        e.getPlayer().getInventory().setItem(8, navigation);
    }

    @EventHandler
    public void onTNTPlace(BlockPlaceEvent event){
        if(event.getBlock().getType().equals(Material.TNT)){
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[HGFPlugin]: Kein TNT auf dem Server, " + event.getPlayer().getName() + "!");
        }
    }

    @EventHandler
    public void onNetherStarHand(PlayerInteractEvent event){
        if((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK ||event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR) && event.getItem().equals(navigation)) {
            event.getPlayer().openInventory(nav);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onNetherStarDrop(PlayerDropItemEvent event){
        if(event.getItemDrop().getItemStack().equals(navigation)) {
            event.getPlayer().openInventory(nav);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event){
        event.getEntity().getInventory().setItem(8, navigation);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        event.getPlayer().getInventory().setItem(8, navigation);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("hgf")){
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.getInventory().setItem(8, navigation);
                player.openInventory(nav);
            }
        }

        if(command.getName().equalsIgnoreCase("regeln")){
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.openBook(rules);
            }
        }

        /*if(command.getName().equalsIgnoreCase("todoadd")) {
            /*if (sender instanceof Player || sender.equals(cms)) {
                if (sender.isOp()) {

                    try {

                        String new_todo = args[0];

                        for(int i = 0; i <= todos_str.length-1; i++){
                            new_todo = new_todo + ", " + todos_str[i];
                        }

                        config.set("Todos", new_todo);

                    } catch (ArrayIndexOutOfBoundsException|NullPointerException e){
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.RED + "[TODOManager]: Bitte überprüfe deine Eingabe! Es muss ein gültiges TODO angegeben sein!");
                    }

                } else {
                    sender.sendMessage("Du hast keine Berechtigung, um TODOs hinzuzufügen, bitte frage bei Krachkiste nach Erlaubniss.");
                }
            }

            config.get("Todos", config.get("Todos") + ", " + args[0]);

        }

        if(command.getName().equalsIgnoreCase("todoremove")) {
            if (sender instanceof Player || sender.equals(cms)) {
                if (sender.isOp()) {

                    try{
                        String todos_conf_str = (String) config.get("Todos");

                        String[] todos_conf = todos_conf_str.split(", ");

                        String[] todos_conf_new = new String[todos_conf.length-2];

                        for(int i = 0; i <= todos_conf.length-1; i++){
                            if(todos_conf[i].equalsIgnoreCase(args[0])){
                                continue;
                            }
                            todos_conf_new[i] = todos_conf[i];
                        }
                    } catch (ArrayIndexOutOfBoundsException|NullPointerException e){
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.RED + "[TODOManager]: Bitte überprüfe deine Eingabe! Es muss ein gültiges TODO angegeben sein!");
                    }

                } else {
                    sender.sendMessage("Du hastt keine Berechtigung, um TODOs hinzuzufügen, bitte frage bei Krachkiste nach Erlaubniss.");
                }
            }
        }

        if(command.getName().equalsIgnoreCase("todoremoveinternal")){
            if(sender instanceof Player || sender.equals(cms)) {
                if(sender.isOp()){
                    for(int i = 0; i <= components.length-1; i++){
                        if(components[i].getText().contains(args[0].replaceAll("!", " "))){
                            components[i].setColor(net.md_5.bungee.api.ChatColor.GREEN);
                            components[i].setText("\u2714 " + todos_str[i] + "\n");
                            components[i].setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder("/todoaddinternal " + todos_str[i] + " (OP)").create()));
                            components[i].setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/todoaddinternal " + todos_str[i].replaceAll(" ", "!")));
                            components[i].setColor(net.md_5.bungee.api.ChatColor.GREEN);
                            todo_meta.spigot().setPages(components);
                            todo.setItemMeta(todo_meta);
                            if(sender instanceof Player){
                                Player pls = (Player) sender;
                                pls.openBook(todo);
                            }
                            break;
                        }
                    }
                } else {
                    sender.sendMessage("Du hast keine Berechtigung, um TODOs zu entfernen, bitte frage bei Krachkiste nach Erlaubniss.");
                }
            }
        }


        if(command.getName().equalsIgnoreCase("todoaddinternal")){
            if(sender instanceof Player || sender.equals(cms)) {
                if(sender.isOp()){
                    for(int i = 0; i <= components.length-1; i++){
                        if(components[i].getText().contains(args[0].replaceAll("!", " "))) {
                            components[i].setColor(net.md_5.bungee.api.ChatColor.RED);
                            components[i].setText("\u2718 " + todos_str[i] + "\n");
                            components[i].setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,  new ComponentBuilder("/todoremoveinternal " + todos_str[i] + " (OP)").create()));
                            components[i].setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/todoremoveinternal " + todos_str[i].replaceAll(" ", "!")));
                            components[i].setColor(net.md_5.bungee.api.ChatColor.RED);
                            todo_meta.spigot().setPages(components);
                            todo.setItemMeta(todo_meta);
                            if(sender instanceof Player){
                                Player pls = (Player) sender;
                                pls.openBook(todo);
                            }
                            break;
                        }
                    }
                } else {
                    sender.sendMessage("Du hast keine Berechtigung, um TODOs hinzuzufügen, bitte frage bei Krachkiste nach Erlaubniss.");
                }
            }
        }*/

        if(command.getName().equalsIgnoreCase("todo")){

            todo_book = new ItemStack(Material.WRITTEN_BOOK);
            todo_meta = (BookMeta) todo_book.getItemMeta();
            todo_meta.setTitle("Season 2 TODOS");
            todo_meta.setAuthor("Krachkiste, HerrPi");
            todo_meta.setPages();
            todo_book.setItemMeta(todo_meta);

            Player player = (Player) sender;
            player.openBook(todo_book);

        }

        return false;
    }

    @Override
    public void onDisable(){

        System.out.println("\n _  __               _     _    _     _       \n" +
                "| |/ /              | |   | |  (_)   | |      \n" +
                "| ' / _ __ __ _  ___| |__ | | ___ ___| |_ ___ \n" +
                "|  < | '__/ _` |/ __| '_ \\| |/ / / __| __/ _ \\\n" +
                "| . \\| | | (_| | (__| | | |   <| \\__ \\ ||  __/\n" +
                "|_|\\_\\_|  \\__,_|\\___|_| |_|_|\\_\\_|___/\\__\\___|\n" +
                "                                              ");

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(command.getName().equalsIgnoreCase("todoadd") | command.getName().equalsIgnoreCase("todoremove") | command.getName().equalsIgnoreCase("todo+") | command.getName().equalsIgnoreCase("todo-")) {

            ArrayList<String> ary = new ArrayList<String>();

            for(int i = 0; i <= todos_str.length-1; i++){
                ary.add(todos_str[i]);
            }

            return ary;

        }
        return null;
    }

}
