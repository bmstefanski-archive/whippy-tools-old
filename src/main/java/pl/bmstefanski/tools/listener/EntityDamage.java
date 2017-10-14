package pl.bmstefanski.tools.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.bmstefanski.tools.manager.SpawnManager;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if (event.getEntity().getType() == EntityType.PLAYER) {
            new SpawnManager((Player) event.getEntity()).stop();
        }
    }
}
