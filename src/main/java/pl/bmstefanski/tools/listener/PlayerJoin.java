package pl.bmstefanski.tools.listener;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.configuration.Config;
import pl.bmstefanski.tools.util.MessageUtils;

public class PlayerJoin implements Listener {

    private final Config config = Config.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        User.get(event.getPlayer());
        User user = User.get(event.getPlayer().getUniqueId());
        Player player = user.getPlayer();

        event.setJoinMessage(MessageUtils.fixColor(StringUtils.replace(config.joinFormat, "%player%", player.getName())));
    }
}
