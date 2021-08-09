package cf.strafe.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            player.sendMessage(ChatColor.GREEN + "Discord: https://discord.gg/hdzAMTaRSq");
        }
        return false;
    }
}
