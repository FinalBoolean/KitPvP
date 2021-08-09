package nl.cryonic.command;

import nl.cryonic.KitPvP;
import nl.cryonic.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(((Player) sender).getPlayer().getUniqueId());
            data.setStaffchat(!data.isStaffchat());
            data.getPlayer().sendMessage(data.isStaffchat() ? ChatColor.GREEN + "Toggled staff chat on" : ChatColor.RED  + "Toggled staff chat off");

        }
        return false;
    }
}
