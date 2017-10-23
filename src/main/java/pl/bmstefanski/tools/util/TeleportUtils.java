package pl.bmstefanski.tools.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.manager.SpawnManager;

import java.util.HashMap;

public class TeleportUtils {

    private static final HashMap<Player, Location> locations = new HashMap<>();

    public Location getLocation(Player player) {
        if (locations.get(player) != null) {
            return locations.get(player);
        } else return new SpawnManager().getSpawn();
    }

    public void setLocation(Player player) {
        locations.put(player, player.getLocation());
        return;
    }
}
