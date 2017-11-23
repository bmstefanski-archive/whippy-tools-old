package pl.bmstefanski.tools.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.bmstefanski.tools.configuration.Config;

public class SpawnManager {

    private final Config config = Config.getInstance();

    public Location getSpawn() {
        int x = config.spawnBlockX;
        int y = config.spawnBlockY;
        int z = config.spawnBlockZ;
        World world = Bukkit.getWorld(config.spawnWorld);

        return new Location(world, x, y, z);
    }
}
