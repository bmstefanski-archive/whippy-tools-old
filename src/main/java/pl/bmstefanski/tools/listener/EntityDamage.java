package pl.bmstefanski.tools.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.manager.TeleportManager;
import pl.bmstefanski.tools.basic.UserImpl;

public class EntityDamage implements Listener {

    private final Tools plugin;

    public EntityDamage(Tools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        new TeleportManager(plugin, (Player) event.getEntity()).stop();

        Player player = (Player) event.getEntity();
        User user = UserManager.getUser(player.getUniqueId());

        if (user == null) {
            return;
        }

        if (user.isGod()) {
            event.setCancelled(true);
        }

    }
}
