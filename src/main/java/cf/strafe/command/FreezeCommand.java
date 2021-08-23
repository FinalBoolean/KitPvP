package cf.strafe.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class FreezeCommand implements CommandExecutor {

    private final ArrayList<UUID> frozenPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if(player.hasPermission("kitpvp.staff")) {
                if(args.length > 0) {
                    Player victim = Bukkit.getPlayer(args[0]);
                    frozenPlayers.add(victim.getUniqueId());
                    if (frozenPlayers.contains(victim.getUniqueId())) {

                    }
                } else {
                    player.sendMessage(ChatColor.GRAY + "/freeze [player]");
                }
            }

        }
        return false;
    }
}
