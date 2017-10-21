package pl.bmstefanski.tools.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.bmstefanski.tools.object.User;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        User user = User.get(event.getPlayer().getUniqueId());

        user.setIp(user.getPlayer().getAddress().getHostName());
        user.setLast(System.currentTimeMillis());
    }
}
