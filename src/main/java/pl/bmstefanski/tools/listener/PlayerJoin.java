package pl.bmstefanski.tools.listener;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.util.MessageUtils;

public class PlayerJoin implements Listener {

    private final FileConfiguration config = Tools.getInstance().getConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        User.get(event.getPlayer());
        User user = User.get(event.getPlayer().getUniqueId());
        Player player = user.getPlayer();

        String joinMessage = config.getString("join-format");
        event.setJoinMessage(MessageUtils.fixColor(StringUtils.replace(joinMessage, "%player%", player.getName())));
    }
}
