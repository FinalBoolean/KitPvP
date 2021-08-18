package cf.strafe.command;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
            if (KitPvP.INSTANCE.getEventManager().noEvent(data)) {
                if (data.getTask() == null) {
                    player.sendMessage(ChatColor.GREEN + "Teleporting please wait 5 seconds, do not move!");
                    data.setTask(new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.teleport(player.getWorld().getSpawnLocation());
                            player.sendMessage(ChatColor.GREEN + "Successfully teleported to spawn!");
                            data.setTask(null);
                        }
                    }.runTaskLater(KitPvP.INSTANCE.getPlugin(), 20L * 5));
                } else {
                    sender.sendMessage(ChatColor.RED + "You're already queued to teleport!");
                }
            }
        }
        return false;
    }
}


