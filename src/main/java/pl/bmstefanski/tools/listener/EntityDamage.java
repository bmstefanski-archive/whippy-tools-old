package pl.bmstefanski.tools.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.bmstefanski.tools.manager.SpawnManager;
import pl.bmstefanski.tools.object.User;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        new SpawnManager((Player) event.getEntity()).stop();

        final Player player = (Player) event.getEntity();
        final User user = User.get(player.getUniqueId());

        if (user.isGod()) {
            event.setCancelled(true);
        }

    }
}
