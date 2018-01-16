package pl.bmstefanski.tools.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;

public class PlayerJoin implements Listener {

    private final Tools plugin;

    public PlayerJoin(Tools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        User user = UserManager.getUser(player.getUniqueId());

//        event.setJoinMessage(MessageUtils.fixColor(StringUtils.replace(plugin.getConfiguration().getJoinFormat(), "%player%", player.getName())));
    }
}
