package cf.strafe.command;

import cf.strafe.data.PlayerData;
import cf.strafe.KitPvP;
import cf.strafe.gui.KitGui;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            PlayerData playerData = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
            if(playerData.getKit() == null) {
                KitGui kitGui = new KitGui(playerData);
                kitGui.openGui(player);
            } else {
                player.sendMessage(ChatColor.RED + "You already have a kit!");
            }
        }
        return false;
    }
}