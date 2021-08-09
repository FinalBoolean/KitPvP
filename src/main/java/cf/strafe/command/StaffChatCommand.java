package cf.strafe.command;

import cf.strafe.data.PlayerData;
import cf.strafe.KitPvP;
import cf.strafe.utils.ColorUtil;
import org.bukkit.Bukkit;
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
            if(data.getPlayer().hasPermission("kitpvp.staff")) {
                if (args.length == 0) {
                    data.setStaffchat(!data.isStaffchat());
                    data.getPlayer().sendMessage(data.isStaffchat() ? ChatColor.GREEN + "Toggled staff chat on" : ChatColor.RED + "Toggled staff chat off");
                } else {
                    StringBuilder message = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        message.append(args[i]).append(" ");
                    }
                    Bukkit.broadcast(ColorUtil.translate("&6[StaffChat] &7" + data.getPlayer().getName() + "»&e " + message), "kitpvp.staff");

                }
            }
        }
        return false;
    }
}
