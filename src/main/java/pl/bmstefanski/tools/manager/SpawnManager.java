package pl.bmstefanski.tools.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.bmstefanski.tools.Tools;

public class SpawnManager {

    private final Tools plugin;

    public SpawnManager(Tools plugin) {
        this.plugin = plugin;
    }

    public Location getSpawn() {
        int x = plugin.getConfiguration().getSpawnX();
        int y = plugin.getConfiguration().getSpawnY();
        int z = plugin.getConfiguration().getSpawnZ();
        World world = Bukkit.getWorld(plugin.getConfiguration().getSpawnWorld());

        return new Location(world, x, y, z);
    }
}
