package nl.cryonic.command;

import nl.cryonic.KitPvP;
import nl.cryonic.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class VanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if (player.hasPermission("kitpvp.staff")) {
                PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
                data.setVanished(!data.isVanished());
                if (data.isVanished()) {
                    player.sendMessage(ChatColor.GREEN + "You just vanished");
                    data.vanish(data);
                } else {
                    data.unvanish(data);
                    player.sendMessage(ChatColor.RED + "You unvanished");
                }
            } else {
                player.sendMessage(ChatColor.RED + "No perms");
            }
        }
        return false;
    }
}
