package cf.strafe.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length > 1) {


                if (Bukkit.getPlayer(args[0]) != null) {
                    Player victim = Bukkit.getPlayer(args[0]);

                    String report = "";
                    for (int i = 1; i < args.length; i++) {
                        report += args[i] + " ";
                    }
                    p.sendMessage(ChatColor.GREEN + "You have reported " + victim.getName() + " for " + report);
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (player.hasPermission("core.report")) {

                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&c" + p.getName() + " has reported " + victim.getName())

                                    + " for " + report);

                        }
                    }

                } else {
                    sender.sendMessage(ChatColor.RED + "Player isn't online");
                }

            } else {
                sender.sendMessage(ChatColor.RED + "Syntax is /report [player] [reason]");
            }
            return false;
        }

        return false;
    }
}
