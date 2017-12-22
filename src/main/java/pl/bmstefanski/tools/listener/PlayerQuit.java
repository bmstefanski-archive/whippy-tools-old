package pl.bmstefanski.tools.listener;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.configuration.Config;
import pl.bmstefanski.tools.runnable.SaveDataTask;
import pl.bmstefanski.tools.util.MessageUtils;

public class PlayerQuit implements Listener {

    private final Config config = Config.getInstance();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        User user = User.get(event.getPlayer());
        Player player = user.getPlayer();

        user.setIp(user.getPlayer().getAddress().getHostName());

        event.setQuitMessage(MessageUtils.fixColor(StringUtils.replace(config.quitFormat, "%player%", player.getName())));

        new SaveDataTask(user).runTaskAsynchronously(Tools.getInstance());
    }
}
