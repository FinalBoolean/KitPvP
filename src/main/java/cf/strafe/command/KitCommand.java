package cf.strafe.command;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.gui.KitGui;
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
            if(KitPvP.INSTANCE.getEventManager().event != null) {
                if (!KitPvP.INSTANCE.getEventManager().event.getSpectators().contains(playerData) && KitPvP.INSTANCE.getEventManager().event.getPlayers().contains(playerData)) {
                    KitGui kitGui = new KitGui(playerData);

                    kitGui.openGui(player);
                }
            } else {
                KitGui kitGui = new KitGui(playerData);
                kitGui.openGui(player);
            }
        }
        return false;
    }
}