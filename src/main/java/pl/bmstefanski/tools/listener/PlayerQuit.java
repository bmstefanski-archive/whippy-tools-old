package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.runnable.SaveDataTask;
import pl.bmstefanski.tools.util.MessageUtils;

public class PlayerQuit implements Listener {

    private final Tools plugin;

    public PlayerQuit(Tools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player.getUniqueId());

        if (user != null) {
            user.setIp(player.getAddress().getHostName());
        }

        event.setQuitMessage(MessageUtils.fixColor(StringUtils.replace(plugin.getConfiguration().getQuitFormat(), "%player%", player.getName())));

        SaveDataTask saveDataTask = new SaveDataTask(plugin.getStorage(), user);
        new Thread(saveDataTask).run();
    }
}
