package cf.strafe.command;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.Event;
import cf.strafe.event.events.Sumo;
import cf.strafe.event.map.MapManager;
import cf.strafe.event.map.SumoMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();

            if(player.hasPermission("kitpvp.events")) {
                if(args.length > 1) {
                    switch (args[0].toLowerCase()) {
                        case "start": {
                            if(args.length > 2) {
                                if(KitPvP.INSTANCE.getEventManager().event == null) {
                                    if (args[1].equalsIgnoreCase("sumo")) {
                                        SumoMap sumoMap = MapManager.getSumoMap(args[2]);
                                        if (sumoMap != null) {
                                            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
                                            KitPvP.INSTANCE.getEventManager().createSumoEvent(Event.Type.SUMO, data, sumoMap);
                                            player.sendMessage(ChatColor.GREEN + "Started event");
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Available maps: ");
                                            for (SumoMap map : MapManager.getSumoMaps()) {
                                                player.sendMessage(ChatColor.GREEN + map.getMapName());
                                            }
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Available events: sumo");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "An event is already going on");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Usage: /event start [event] [map], /event stop [event] [reason]");
                            }
                            break;
                        }
                        case "stop": {
                            if(args.length > 2) {
                                PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
                                if(KitPvP.INSTANCE.getEventManager().host ==  data) {

                                    KitPvP.INSTANCE.getEventManager().deleteEvent(args[2]);
                                } else {
                                    player.sendMessage(ChatColor.RED  + "You are not the host of that event!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Usage: /event start [event] [map], /event stop [event] [reason]");
                            }
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /event start [event] [map], /event stop [event] [reason]");
                }
            }
        }
        return false;
    }
}
