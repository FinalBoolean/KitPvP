package cf.strafe.event.map;

import cf.strafe.KitPvP;
import cf.strafe.event.events.Skywars;
import cf.strafe.event.map.skywars.ChestLocation;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@UtilityClass
public class MapManager {
    private final ArrayList<SumoMap> sumoMaps = new ArrayList<>();
    private final ArrayList<FFAMap> ffaMaps = new ArrayList<>();
    private final ArrayList<SkywarsMap> skywarsMap = new ArrayList<>();


    public static ArrayList<SumoMap> getSumoMaps() {
        return sumoMaps;
    }

    public static ArrayList<FFAMap> getFfaMaps() {
        return ffaMaps;
    }

    public static ArrayList<SkywarsMap> getSkywarsMap() {
        return skywarsMap;
    }


    public void saveArenas() {
        saveSumo();
        saveFFA();
        saveSkywars();
    }

    public void loadArenas() {
        loadSumo();
        loadFFa();
        loadSkywars();
    }

    public SumoMap getSumoMap(String name) {
        for (SumoMap map : sumoMaps) {
            if (map.getMapName().equalsIgnoreCase(name)) {
                return map;
            }
        }
        return null;
    }

    public FFAMap getFFAMap(String name) {
        for (FFAMap map : ffaMaps) {
            if (map.getMapName().equalsIgnoreCase(name)) {
                return map;
            }
        }
        return null;
    }


    private void loadSkywars() {
        File file = new File(KitPvP.INSTANCE.getPlugin().getDataFolder(), "SkywarsMaps.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String key : config.getKeys(true)) {
                System.out.println(key);
            }
        }
    }

    private void saveSkywars() {
        File file = new File(KitPvP.INSTANCE.getPlugin().getDataFolder(), "SkywarsMaps.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {

            }
        }

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        for(SkywarsMap skywarsMap : MapManager.getSkywarsMap()) {
            yml.set(skywarsMap.getMapName() + ".spawn", skywarsMap.getSpectatorLocation());
            for(int i = 0; i < skywarsMap.getSpawnLocations().size(); i++) {
                Location location = skywarsMap.getSpawnLocations().get(i);
                yml.set(skywarsMap.getMapName() + ".spawnLocations." + i + ".id", i);
                yml.set(skywarsMap.getMapName() + ".spawnLocations." + i + ".location", location);
            }
            /*
            Mapping this is going to be difficult
             */
            for(int i = 0; i < skywarsMap.getChestLocations().size(); i++) {
                ChestLocation chestLocation = skywarsMap.getChestLocations().get(i);
                yml.set(skywarsMap.getMapName() + ".chestLocations." + i + ".id", i);
                yml.set(skywarsMap.getMapName() + ".chestLocations." + i + ".location", chestLocation.getLocation());

                //FUCKING KILL ME

                for(int o = 0; i < chestLocation.getInventory().length; o++) {
                    ItemStack itemStack = chestLocation.getInventory()[o];
                    yml.set(skywarsMap.getMapName() + ".chestLocations." + i + ".items." + o, itemStack);
                }
            }
            /*
            Alright were done
             */
            yml.set(skywarsMap.getMapName() + ".name", skywarsMap.getMapName());
        }
    }

    private void saveSumo() {
        File file = new File(KitPvP.INSTANCE.getPlugin().getDataFolder(), "SumoArenas.yml");

        if (!file.exists()) {


            try {
                file.createNewFile();
            } catch (IOException ignored) {

            }

        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        for (SumoMap arena : MapManager.getSumoMaps()) {
            yml.set(arena.getMapName() + ".spawnLocation", arena.getSpawnLocation());
            yml.set(arena.getMapName() + ".fight1", arena.getFightLocation1());
            yml.set(arena.getMapName() + ".fight2", arena.getFightLocation2());
            yml.set(arena.getMapName() + ".fallLevel", arena.getFallLevel());
            yml.set(arena.getMapName() + ".name", arena.getMapName());
        }

        try {
            yml.save(file);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void loadSumo() {
        File file = new File(KitPvP.INSTANCE.getPlugin().getDataFolder(), "SumoArenas.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String key : config.getKeys(false)) {
                String arenaName = config.getString(key + ".name");
                Location spawnLocation = config.getLocation(key + ".spawnLocation");
                Location fight1 = config.getLocation(key + ".fight1");
                Location fight2 = config.getLocation(key + ".fight2");
                int fallLevel = config.getInt(key + ".fallLevel");
                MapManager.getSumoMaps().add(new SumoMap(arenaName, spawnLocation, fight1, fight2, fallLevel));
                System.out.println("Loading Arena " + arenaName);
            }
        }
    }

    private void loadFFa() {
        File file = new File(KitPvP.INSTANCE.getPlugin().getDataFolder(), "FFAArenas.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String key : config.getKeys(false)) {
                String arenaName = config.getString(key + ".name");
                Location spawnLocation = config.getLocation(key + ".spawnLocation");
                Location fight = config.getLocation(key + ".fight");
                MapManager.getFfaMaps().add(new FFAMap(arenaName, spawnLocation, fight));
                System.out.println("Loading Arena " + arenaName);
            }
        }
    }

    private void saveFFA() {
        File file = new File(KitPvP.INSTANCE.getPlugin().getDataFolder(), "FFAArenas.yml");

        if (!file.exists()) {


            try {
                file.createNewFile();
            } catch (IOException ignored) {

            }

        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        for (FFAMap arena : MapManager.getFfaMaps()) {
            yml.set(arena.getMapName() + ".spawnLocation", arena.getSpawnLocation());
            yml.set(arena.getMapName() + ".fight", arena.getFightLocation());
            yml.set(arena.getMapName() + ".name", arena.getMapName());
        }

        try {
            yml.save(file);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
