package cf.strafe.event.map;

import cf.strafe.KitPvP;
import cf.strafe.event.map.skywars.ChestLocation;
import cf.strafe.utils.LocationUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    public SkywarsMap getSkywarsMap(String name) {
        for (SkywarsMap map : skywarsMap) {
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
            for (String key : config.getKeys(false)) {
                String arenaName = config.getString(key + ".name");
                List<Location> locations = new ArrayList<>();
                List<ChestLocation> chestLocations = new ArrayList<>();
                for (int i = 0; i < config.getInt(arenaName + ".spawnLocations.count"); ) {
                    i++;
                    locations.add(LocationUtil.parseToLocation(config.getString(arenaName + ".spawnLocations." + i + ".location")));
                }
                for(int i = 0; i < config.getInt(arenaName + ".chestLocations.count");) {
                    i++;
                    Location chestLocation = LocationUtil.parseToLocation(config.getString(arenaName + ".chestLocations." + i + ".location"));
                    List<ItemStack> itemStacks = new ArrayList<>();
                    for(int i2 = 0; i2 < config.getInt(arenaName + ".chestLocations." + i + ".items.count");) {
                        i2++;
                        itemStacks.add(config.getItemStack(arenaName + ".chestLocations." + i + ".items." + i2));
                    }
                    chestLocations.add(new ChestLocation(chestLocation, itemStacks));
                }
                Location spectatorLocation = LocationUtil.parseToLocation(config.getString(arenaName + ".spawn"));
                getSkywarsMap().add(new SkywarsMap(arenaName, spectatorLocation, chestLocations, locations));
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
            yml.set(skywarsMap.getMapName() + ".spawn", LocationUtil.parseToString(skywarsMap.getSpectatorLocation()));
            int l = 0;
            for(Location location : skywarsMap.getSpawnLocations()) {
                l++;
                yml.set(skywarsMap.getMapName() + ".spawnLocations." + l + ".id", l);
                yml.set(skywarsMap.getMapName() + ".spawnLocations." + l + ".location", LocationUtil.parseToString(location));
            }
            yml.set(skywarsMap.getMapName() + ".spawnLocations.count", l);
            /*
            Mapping this is going to be difficult
             */
            int s = 0;
            for(ChestLocation chestLocation : skywarsMap.getChestLocations()) {
                s++;
                yml.set(skywarsMap.getMapName() + ".chestLocations." + s + ".id", s);
                yml.set(skywarsMap.getMapName() + ".chestLocations." + s + ".location", LocationUtil.parseToString(chestLocation.getLocation()));

                //FUCKING KILL ME
                int count = 0;
                for (ItemStack itemStack : chestLocation.getInventory()) {
                    count++;
                    yml.set(skywarsMap.getMapName() + ".chestLocations." + s + ".items." + count, itemStack);
                }
                yml.set(skywarsMap.getMapName() + ".chestLocations." + s + ".items.count", count);


            }
            yml.set(skywarsMap.getMapName() + ".chestLocations.count", s);
            /*
            Alright were done
             */
            yml.set(skywarsMap.getMapName() + ".name", skywarsMap.getMapName());
        }
        try {
            yml.save(file);
        } catch (IOException e) {

            e.printStackTrace();
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
            yml.set(arena.getMapName() + ".spawnLocation", LocationUtil.parseToString(arena.getSpawnLocation()));
            yml.set(arena.getMapName() + ".fight1", LocationUtil.parseToString(arena.getFightLocation1()));
            yml.set(arena.getMapName() + ".fight2", LocationUtil.parseToString(arena.getFightLocation2()));
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
                Location spawnLocation = LocationUtil.parseToLocation(config.getString(key + ".spawnLocation"));
                Location fight1 = LocationUtil.parseToLocation(config.getString(key + ".fight1"));
                Location fight2 = LocationUtil.parseToLocation(config.getString(key + ".fight2"));
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
                Location spawnLocation = LocationUtil.parseToLocation(config.getString(key + ".spawnLocation"));
                Location fight = LocationUtil.parseToLocation(config.getString(key + ".fight"));
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
            yml.set(arena.getMapName() + ".spawnLocation", LocationUtil.parseToString(arena.getSpawnLocation()));
            yml.set(arena.getMapName() + ".fight", LocationUtil.parseToString(arena.getFightLocation()));
            yml.set(arena.getMapName() + ".name", arena.getMapName());
        }

        try {
            yml.save(file);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
