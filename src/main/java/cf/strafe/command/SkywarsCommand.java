package cf.strafe.command;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.events.Skywars;
import cf.strafe.event.map.FFAMap;
import cf.strafe.event.map.MapManager;
import cf.strafe.event.map.SkywarsMap;
import cf.strafe.event.map.skywars.ChestLocation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.Arrays;

public class SkywarsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("kitpvp.admin")) {

                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("join")) {
                        if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {

                            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
                            if(KitPvP.INSTANCE.getEventManager().noEvent(data)) {
                                KitPvP.INSTANCE.getEventManager().getEvent().addPlayer(data);
                                player.sendMessage(ChatColor.GREEN + "You joined the event");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "No event is on");
                        }
                        return false;
                    }
                }
                if (args.length > 1) {
                    switch (args[0].toLowerCase()) {
                        case "create": {
                            MapManager.getSkywarsMap().add(new SkywarsMap(args[1], null, new ArrayList<>(), new ArrayList<>()));
                            player.sendMessage(ChatColor.GREEN + "Created map " + args[1]);
                            break;
                        }
                        case "spawn": {
                            SkywarsMap skywarsMap = MapManager.getSkywarsMap(args[1]);
                            if (skywarsMap != null) {
                                skywarsMap.setSpectatorLocation(player.getLocation());
                                player.sendMessage(ChatColor.GREEN + "Set spawn");
                            } else {
                                player.sendMessage(ChatColor.RED + "There is no map called " + args[1]);
                            }
                            break;
                        }
                        case "addspawn": {
                            SkywarsMap skywarsMap = MapManager.getSkywarsMap(args[1]);

                            if(skywarsMap != null) {
                                skywarsMap.getSpawnLocations().add(player.getLocation());
                                player.sendMessage(ChatColor.GREEN + "Added spawn");
                            } else {
                                player.sendMessage(ChatColor.RED + "There is no map called " + args[1]);
                            }
                            break;
                        }
                        case "addchest": {
                            SkywarsMap skywarsMap = MapManager.getSkywarsMap(args[1]);
                            if(skywarsMap != null) {
                                Block block = getTargetBlock(player, 6);
                                if(block.getType() == Material.CHEST) {

                                    Chest chest = (Chest)block.getState();
                                    skywarsMap.getChestLocations().add(new ChestLocation(chest.getLocation(), Arrays.asList(chest.getInventory().getContents())));
                                    player.sendMessage(ChatColor.GREEN + "Added chest");
                                } else {
                                    player.sendMessage(ChatColor.RED + "That is no chest sir.");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED  + "There is no map called " + args[1]);
                            }
                            break;
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /skywars create [name], skywars spawn, skywars addSpawn");
                }
            } else {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("join")) {
                        if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {
                            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
                            KitPvP.INSTANCE.getEventManager().getEvent().addPlayer(data);
                            player.sendMessage(ChatColor.GREEN + "You joined the event");
                        } else {
                            player.sendMessage(ChatColor.RED + "No event is on");
                        }
                    } else if (args[0].equalsIgnoreCase("leave")) {
                        if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {
                            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
                            KitPvP.INSTANCE.getEventManager().getEvent().removePlayer(data);
                            player.sendMessage(ChatColor.GREEN + "You left the event");
                        } else {
                            player.sendMessage(ChatColor.RED + "No event is on");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "/skywars join, /skywars leave ");
                }
            }
        }
        return false;
    }

    //https://www.spigotmc.org/threads/solved-get-coords-for-a-block-a-player-is-looking-at.64576/
    public final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }
}
