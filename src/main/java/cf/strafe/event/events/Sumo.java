package cf.strafe.event.events;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.Event;
import cf.strafe.event.map.SumoMap;
import cf.strafe.utils.ColorUtil;
import cf.strafe.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Sumo extends Event {

    public int round;

    private final Pair<PlayerData, PlayerData> roundPlayers = new Pair<>();
    private final SumoMap map;

    public Sumo(SumoMap map, PlayerData host) {
        this.map = map;
        maxPlayers = 50;
        state = State.WAITING;
        gameTime = 30;
        this.host = host;
    }

    @Override
    public void addPlayer(PlayerData player) {
        player.getPlayer().teleport(map.getSpawnLocation());
        player.getPlayer().getInventory().clear();
        super.addPlayer(player);
    }


    @Override
    public void update() {
        switch (state) {
            case WAITING: {
                gameTime--;
                if (gameTime == 29) {
                    Bukkit.broadcastMessage(ColorUtil.translate("&7[&6SUMO&7] &f" + host.getPlayer().getName() + " &7is hosting a &fSumo Event! &edo /sumo join!"));
                }

                if (gameTime == 20) {
                    Bukkit.broadcastMessage(ColorUtil.translate("&7[&6SUMO&7] &f" + host.getPlayer().getName() + " &7is hosting a &fSumo Event! &edo /sumo join!"));
                }
                if (gameTime == 10) {
                    Bukkit.broadcastMessage(ColorUtil.translate("&7[&6SUMO&7] &f" + host.getPlayer().getName() + " &7is hosting a &fSumo Event! &edo /sumo join!"));
                }
                if (gameTime == 0) {
                    if (players.size() > 1) {
                        state = State.INGAME;
                        Bukkit.broadcastMessage(ChatColor.GREEN + "Sumo event has started!");
                    } else {
                        KitPvP.INSTANCE.getEventManager().deleteEvent("Not enough players.");
                    }
                }
                break;
            }
            case INGAME: {
                if (roundPlayers.getY() == null) {
                    gameTime = 5;
                    round++;

                    Random random = new Random();

                    PlayerData randomPlayer = players.get(random.nextInt(players.size()));

                    PlayerData randomPlayer2 = players.get(random.nextInt(players.size()));

                    while (randomPlayer == randomPlayer2 && players.size() > 1) {
                        randomPlayer2 = players.get(random.nextInt(players.size()));
                    }
                    if(players.size() < 2) {
                        Bukkit.broadcastMessage(ChatColor.GREEN  + "Game ended! " + players.get(0).getPlayer().getName() + " won!");
                        state = State.END;
                    }

                    roundPlayers.setX(randomPlayer);
                    roundPlayers.setY(randomPlayer2);
                } else {
                    if (gameTime == 5) {
                        players.forEach(playerData -> playerData.getPlayer().sendMessage(ChatColor.GREEN + "Round " + round + ". Next up is " + roundPlayers.getX().getPlayer().getName() + " vs " + roundPlayers.getY().getPlayer().getName()));
                    }
                    if(players.size() < 2) {
                        Bukkit.broadcastMessage(ChatColor.GREEN  + "Game ended! " + players.get(0).getPlayer().getName() + " won!");

                        state = State.END;

                    }
                    if (gameTime == 1) {
                        roundPlayers.getX().getPlayer().teleport(map.getFightLocation1());
                        roundPlayers.getY().getPlayer().teleport(map.getFightLocation2());
                        PotionEffect potionEffect = PotionEffectType.DAMAGE_RESISTANCE.createEffect(999999, 255);
                        roundPlayers.getX().getPlayer().addPotionEffect(potionEffect);
                        roundPlayers.getY().getPlayer().addPotionEffect(potionEffect);
                    }
                    if(gameTime == 0) {

                        Location player1 = roundPlayers.getX().getPlayer().getLocation();
                        Location player2 = roundPlayers.getY().getPlayer().getLocation();

                        if(player1.getY() <= map.getFallLevel()) {
                            players.remove(roundPlayers.getX());
                            addPlayer(roundPlayers.getX());
                            roundPlayers.getX().getPlayer().teleport(map.getSpawnLocation());
                            roundPlayers.getY().getPlayer().teleport(map.getSpawnLocation());
                            roundPlayers.setX(null);
                            roundPlayers.setY(null);
                        }

                        if(player2.getY() <= map.getFallLevel()) {
                            players.remove(roundPlayers.getY());
                            addPlayer(roundPlayers.getY());
                            roundPlayers.getY().getPlayer().teleport(map.getSpawnLocation());
                            roundPlayers.getX().getPlayer().teleport(map.getSpawnLocation());
                            roundPlayers.setX(null);
                            roundPlayers.setY(null);
                        }
                    }
                    if (gameTime != 0) {
                        gameTime--;
                    }
                }
                break;
            }
            case END: {
                KitPvP.INSTANCE.getEventManager().deleteEvent();
                for(PlayerData players : getSpectators()) {
                    removePlayer(players);
                }
                break;
            }
        }

    }

}
