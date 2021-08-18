package cf.strafe.command;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.map.MapManager;
import cf.strafe.event.map.SumoMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SumoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if (player.hasPermission("kitpvp.staff")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("join")) {
                        if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {
                            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
                            KitPvP.INSTANCE.getEventManager().getEvent().addPlayer(data);
                            player.sendMessage(ChatColor.GREEN + "You joined the event");
                        } else {
                            player.sendMessage(ChatColor.RED + "No event is on");
                        }
                        return false;
                    }
                }
                if (args.length > 1) {
                    switch (args[0].toLowerCase()) {
                        case "create": {
                            MapManager.getSumoMaps().add(new SumoMap(args[1], null, null, null, 0));
                            player.sendMessage(ChatColor.GREEN + "Created map " + args[1]);
                            break;
                        }

                        case "spawn": {
                            SumoMap sumoMap = MapManager.getSumoMap(args[1]);
                            if (sumoMap != null) {
                                sumoMap.setSpawnLocation(player.getLocation());
                                player.sendMessage(ChatColor.GREEN + "Set spawn");
                            } else {
                                player.sendMessage(ChatColor.RED + "There is no map called " + args[1]);
                            }

                            break;
                        }
                        case "fight1": {
                            SumoMap sumoMap = MapManager.getSumoMap(args[1]);
                            if (sumoMap != null) {
                                sumoMap.setFightLocation1(player.getLocation());
                                player.sendMessage(ChatColor.GREEN + "Set fight 1");
                            } else {
                                player.sendMessage(ChatColor.RED + "There is no map called " + args[1]);
                            }
                            break;
                        }
                        case "fight2": {
                            SumoMap sumoMap = MapManager.getSumoMap(args[1]);
                            if (sumoMap != null) {
                                player.sendMessage(ChatColor.GREEN + "Set fight 2");
                                sumoMap.setFightLocation2(player.getLocation());
                            } else {
                                player.sendMessage(ChatColor.RED + "There is no map called " + args[1]);
                            }
                            break;
                        }
                        case "falllevel": {
                            SumoMap sumoMap = MapManager.getSumoMap(args[1]);
                            if (sumoMap != null) {
                                player.sendMessage(ChatColor.GREEN + "Set fall level");
                                sumoMap.setFallLevel(player.getLocation().getY());
                            } else {
                                player.sendMessage(ChatColor.RED + "There is no map called " + args[1]);
                            }
                            break;
                        }
                        case "join": {
                            if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {
                                PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
                                KitPvP.INSTANCE.getEventManager().getEvent().addPlayer(data);
                                player.sendMessage(ChatColor.GREEN + "You joined the event");
                            } else {
                                player.sendMessage(ChatColor.RED + "No event is on");
                            }
                            break;
                        }
                        case "leave": {
                            if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {
                                PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
                                KitPvP.INSTANCE.getEventManager().getEvent().removePlayer(data);
                                player.sendMessage(ChatColor.GREEN + "You left the event");
                            } else {
                                player.sendMessage(ChatColor.RED + "No event is on");
                            }
                            break;
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /sumo create [name], sumo spawn [name], sumo fight1 [name], sumo fight2 [name], sumo fallLevel");
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
                    player.sendMessage(ChatColor.RED + "/sumo join, /sumo leave ");
                }
            }
        }
        return false;
    }

}
