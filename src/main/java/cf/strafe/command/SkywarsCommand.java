package cf.strafe.command;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.map.FFAMap;
import cf.strafe.event.map.MapManager;
import cf.strafe.event.map.SkywarsMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class SkywarsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("kitpvp.admin")) {
                if (args.length > 1) {
                    switch (args[0].toLowerCase()) {
                        case "create": {
                            MapManager.getSkywarsMap().add(new SkywarsMap(args[1], null, null, null));
                            player.sendMessage(ChatColor.GREEN + "Created map " + args[1]);
                            break;
                        }

                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /skywars create [name], skywars spawn, skywars addSpawn, skywars removeSpawn [index]");
                }
            }
        }
        return false;
    }

    //https://www.spigotmc.org/threads/solved-get-coords-for-a-block-a-player-is-looking-at.64576/
    public final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }
}
