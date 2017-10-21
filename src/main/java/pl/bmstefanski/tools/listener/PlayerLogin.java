package pl.bmstefanski.tools.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.bmstefanski.tools.object.User;

public class PlayerLogin implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        User user = User.get(event.getPlayer().getUniqueId());

/*        if (user == null) event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cBlad podczas weryfikacji konta!");
        else if (user.getName() == null) event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cBlad podczas weryfikacji konta!");*/
    }
}
