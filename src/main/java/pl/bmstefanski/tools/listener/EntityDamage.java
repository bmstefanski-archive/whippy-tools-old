package pl.bmstefanski.tools.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.bmstefanski.tools.manager.TeleportManager;
import pl.bmstefanski.tools.basic.User;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        new TeleportManager((Player) event.getEntity()).stop();

        Player player = (Player) event.getEntity();
        User user = User.get(player.getUniqueId());

        if (user.isGod()) {
            event.setCancelled(true);
        }

    }
}
