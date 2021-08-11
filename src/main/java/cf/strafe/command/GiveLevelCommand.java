package cf.strafe.command;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveLevelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if(player.hasPermission("kitpvp.admin")) {
                if(args.length > 0) {
                    if(args[0].equalsIgnoreCase("give")) {
                        if(args.length > 2) {
                            Player gift = Bukkit.getPlayer(args[1]);
                            if (gift != null) {
                                PlayerData giftedData = KitPvP.INSTANCE.getDataManager().getPlayer(gift.getPlayer().getUniqueId());
                                giftedData.setLevel(Integer.parseInt(args[2]));
                                giftedData.saveData();
                            } else {
                                player.sendMessage(ChatColor.RED + "Player is not here!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Usage: /givelevel give [player] [level]");
                        }
                    } else if (args[0].equalsIgnoreCase("all")) {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "(!) Everyone has received 1 level!");
                        for(PlayerData data : KitPvP.INSTANCE.getDataManager().getUsers().values()) {
                            data.setLevel(data.getLevel() + 1);
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /givelevel give [player] [level], /givelevel all");
                }
            } else {
                player.sendMessage(ChatColor.RED + "No permission");
            }
        }
        return false;
    }
}
