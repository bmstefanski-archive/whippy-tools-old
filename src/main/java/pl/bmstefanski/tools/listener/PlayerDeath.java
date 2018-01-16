package pl.bmstefanski.tools.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.util.TeleportUtils;

public class PlayerDeath implements Listener {

    private final Tools plugin;

    public PlayerDeath(Tools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (event.getEntity() != null) {
//            new TeleportUtils().setLocation(event.getEntity());
        }
    }
}
