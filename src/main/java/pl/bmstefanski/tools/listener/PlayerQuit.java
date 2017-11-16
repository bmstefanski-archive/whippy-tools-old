package pl.bmstefanski.tools.listener;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.util.MessageUtils;

public class PlayerQuit implements Listener {

    private final FileConfiguration config = Tools.getInstance().getConfig();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        User user = User.get(event.getPlayer().getUniqueId());
        Player player = user.getPlayer();

        user.setIp(user.getPlayer().getAddress().getHostName());

        String quitMessage = config.getString("quit-format");
        event.setQuitMessage(MessageUtils.fixColor(StringUtils.replace(quitMessage, "%player%", player.getName())));
    }
}
