package cf.strafe.command;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if (args.length > 0) {
                Player statPlayer = Bukkit.getPlayer(args[0]);
                if (statPlayer != null) {
                    PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(statPlayer.getUniqueId());
                    player.sendMessage(ChatColor.GREEN + "Stats of " + statPlayer.getName());
                    player.sendMessage("");
                    player.sendMessage(ChatColor.GREEN + "level: " + data.getLevel());
                    player.sendMessage(ChatColor.GREEN + "kills: " + data.getKills());
                    player.sendMessage(ChatColor.GREEN + "killStreak: " + data.getKillStreak());
                    player.sendMessage(ChatColor.GREEN + "deaths: " + data.getDeaths());
                } else {
                    player.sendMessage(ChatColor.RED + "Player is not online");
                }
            } else {
                PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "Your stats");
                player.sendMessage("");
                player.sendMessage(ChatColor.GREEN + "level: " + data.getLevel());
                player.sendMessage(ChatColor.GREEN + "kills: " + data.getKills());
                player.sendMessage(ChatColor.GREEN + "killStreak: " + data.getKillStreak());
                player.sendMessage(ChatColor.GREEN + "deaths: " + data.getDeaths());
            }
        }
        return false;
    }
}
