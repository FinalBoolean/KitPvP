package cf.strafe.command;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.map.FFAMap;
import cf.strafe.event.map.MapManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FFACommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if (player.hasPermission("kitpvp.staff")) {
                if (args.length > 1) {
                    switch (args[0].toLowerCase()) {
                        case "create": {
                            MapManager.getFfaMaps().add(new FFAMap(args[1], null, null));
                            player.sendMessage(ChatColor.GREEN + "Created map " + args[1]);
                            break;
                        }

                        case "spawn": {
                            FFAMap ffaMap = MapManager.getFFAMap(args[1]);
                            if (ffaMap != null) {
                                ffaMap.setSpawnLocation(player.getLocation());
                                player.sendMessage(ChatColor.GREEN + "Set spawn");
                            } else {
                                player.sendMessage(ChatColor.RED + "There is no map called " + args[1]);
                            }

                            break;
                        }
                        case "fight": {
                            FFAMap sumoMap = MapManager.getFFAMap(args[1]);
                            if (sumoMap != null) {
                                sumoMap.setFightLocation(player.getLocation());
                                player.sendMessage(ChatColor.GREEN + "Set fight");
                            } else {
                                player.sendMessage(ChatColor.RED + "There is no map called " + args[1]);
                            }
                            break;
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /ffa create [name], ffa spawn [name], sumo fight");
                }
            }
        }
        return false;
    }
}
