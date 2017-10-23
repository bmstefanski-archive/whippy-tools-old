package pl.bmstefanski.tools.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import pl.bmstefanski.tools.Tools;

public class SpawnManager {

    private final FileConfiguration fileConfiguration;

    public SpawnManager() {
        this.fileConfiguration = Tools.getInstance().getConfig();
    }

    public Location getSpawn() {
        final int x = fileConfiguration.getInt("spawn.x");
        final int y = fileConfiguration.getInt("spawn.y");
        final int z = fileConfiguration.getInt("spawn.z");
        final World world = Bukkit.getWorld(fileConfiguration.getString("spawn.world"));

        return new Location(world, x, y, z);
    }
}
