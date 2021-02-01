package mte;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {

    public static String pluginVersion = "v1.0";
    public static String pluginName = "Modded Teleporting Experience";
    public static String tpPrefix = (ChatColor.BLUE + "[") + (ChatColor.AQUA + "MTE")
            + (ChatColor.BLUE + "]") + (ChatColor.RESET + " ");
    String authors = (ChatColor.GOLD + "Phatsanphon Nakaranurak, Marudet Boonseng, Nantipat Laingein");

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getConsoleSender().sendMessage(" ");
        Bukkit.getServer().getConsoleSender().sendMessage(
                "    " + (ChatColor.AQUA + pluginName) + " " + (ChatColor.GREEN + pluginVersion) + " " + (ChatColor.GRAY + "by")
                        + " " + (ChatColor.AQUA + authors));
        Bukkit.getServer().getConsoleSender().sendMessage(
                "    " + (ChatColor.WHITE + "Running on") + " " + (ChatColor.BLUE + getServer().getName() + "/" + getServer().getVersion()));
        Bukkit.getServer().getConsoleSender().sendMessage(" ");
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "follow for more plugin update:");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "https://github.com/ssuniie/NokPlugin/releases");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("mte")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.WHITE + "============================================");
                    player.sendMessage((ChatColor.AQUA + ">>> ") + (ChatColor.BLUE + "/teleport") + (ChatColor.WHITE + " - ")
                            + (ChatColor.WHITE + "To teleport to other players"));
                    player.sendMessage((ChatColor.AQUA + ">>> ") + (ChatColor.BLUE + "/execute") + (ChatColor.WHITE + " - ")
                            + (ChatColor.WHITE + "To kill yourself"));
                    player.sendMessage((ChatColor.AQUA + ">>> ") + (ChatColor.BLUE + "/mte") + (ChatColor.WHITE + " - ")
                            + (ChatColor.WHITE + "Shows help menu"));
                    player.sendMessage((ChatColor.AQUA + ">>> ") + (ChatColor.BLUE + "/mte info") + (ChatColor.WHITE + " - ")
                            + (ChatColor.WHITE + "About this plugin"));
                    player.sendMessage((ChatColor.AQUA + ">>> ") + (ChatColor.BLUE + "/mte github") + (ChatColor.WHITE + " - ")
                            + (ChatColor.WHITE + "Go to GitHub page"));
                    player.sendMessage(ChatColor.WHITE + "==============================================");
                } else if (args[0].equalsIgnoreCase("info")) {
                    player.sendMessage((ChatColor.WHITE + "You are using") + " " + (ChatColor.AQUA + pluginName) + " "
                            + (ChatColor.GREEN + pluginVersion) + (ChatColor.GRAY + " by ") + authors);
                } else if (args[0].equalsIgnoreCase("github")) {
                    player.sendMessage((ChatColor.WHITE + "You are using") + " " + pluginName + " "
                            + (ChatColor.GREEN + pluginVersion) + (ChatColor.GRAY + " by ") + authors);
                }
            }

            if (label.equalsIgnoreCase("teleport")) {
                if (args.length == 0) {
                    player.sendMessage(tpPrefix + (ChatColor.WHITE + "Just type /teleport <username>"));
                }
                else if (args.length == 1) {
                    Player target = player.getServer().getPlayer(args[0]);
                    Location targetLocation = target.getLocation();

                    if (player != target) {
                        if (target != null) {
                            player.teleport(target);
                            player.sendMessage(tpPrefix + (ChatColor.WHITE + "You have been teleported to") + " "
                                    + (ChatColor.GREEN + target.getName()) + (ChatColor.WHITE + "."));
                            target.sendMessage(tpPrefix + (ChatColor.GREEN + player.getName())
                                    + (ChatColor.WHITE + " been teleported to you."));
                            target.playSound(targetLocation, Sound.ENTITY_CREEPER_PRIMED, 100.0f, 1.0f);
                        }
                        else {
                            player.sendMessage(tpPrefix + (ChatColor.WHITE + "Can not find any players")); // If 'possible'
                        }
                    }
                    else {
                        player.sendMessage(tpPrefix + (ChatColor.WHITE + "You can not teleport to yourself!"));
                    }
                }
            }

            if (command.getName().equalsIgnoreCase("execute")) {
                World world = Bukkit.getServer().getWorld("world");
                boolean keepInvGameRuleCheck = world.getGameRuleValue(GameRule.KEEP_INVENTORY);

                if (keepInvGameRuleCheck == false) {
                    player.sendMessage(tpPrefix + (ChatColor.WHITE + "You can not self-execute because your ")
                            + (ChatColor.GREEN + "keepInventory gamerule ") + (ChatColor.WHITE + "is not enable."));
                } else {
                    player.setHealth(0);
                    player.sendMessage(tpPrefix + (ChatColor.WHITE + "You died..."));
                }
            }

            if (command.getName().equalsIgnoreCase("keepInv")) {
                World world = Bukkit.getServer().getWorld("world");
                boolean keepInvGameRuleCheck = world.getGameRuleValue(GameRule.KEEP_INVENTORY);

                if (keepInvGameRuleCheck == false) {
                    world.setGameRule(GameRule.KEEP_INVENTORY, true);
                    Bukkit.broadcastMessage(tpPrefix + (ChatColor.AQUA + player.getName()) + (ChatColor.WHITE + " enabled ")
                            + (ChatColor.GREEN + "keepInventory gamerule"));
                } else {
                    world.setGameRule(GameRule.KEEP_INVENTORY, false);
                    Bukkit.broadcastMessage(tpPrefix + (ChatColor.AQUA + player.getName()) + (ChatColor.WHITE + " disabled ")
                            + (ChatColor.GREEN + "keepInventory gamerule"));
                }
            }
            /*if (command.getName().equalsIgnoreCase("back")) {
                UUID playerUUID = player.getUniqueId();
                boolean onDeath = onDeath();
                if (onDeath == false) {
                    player.teleport(onDeath().playerDeathLocation);
                }
            }*/

            return true;

        } else {
            sender.sendMessage(tpPrefix
                    + (ChatColor.WHITE + "Open in-game chat to get started with this plugin"));
            return false;
        }
    }

    /*@EventHandler
    public boolean onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        public Location playerDeathLocation = player.getLocation();
        player.sendMessage(tpPrefix + (ChatColor.WHITE + "/back"));
        return true;
    }*/

    @EventHandler
    public void onJoinServer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage((ChatColor.BLUE + "Welcome! ") + (ChatColor.GREEN + player.getDisplayName())
                + (ChatColor.WHITE + " to test server!"));
    }

    @EventHandler
    public void onLeaveServer(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage((ChatColor.BLUE + "Goodbye! ") + (ChatColor.GREEN + player.getDisplayName()));
    }
}

