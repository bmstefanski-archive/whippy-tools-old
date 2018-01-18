package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.Ban;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.BanManager;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.runnable.LoadDataTask;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TextUtils;

public class PlayerPreLogin implements Listener {

    private final Tools plugin;

    public PlayerPreLogin(Tools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {

        Player player = Bukkit.getPlayer(event.getUniqueId());
        User user = UserManager.getUser(event.getUniqueId());
        Messages messages = plugin.getMessages();

        Ban ban = BanManager.getBan(user.getUUID());

        if (ban == null) return;
        if (user.isBanned()) {
            String banFormat = TextUtils.listToString(messages.getBanFormat());
            String untilFormat = MessageUtils.fixColor(messages.getPermanentBan());

            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, StringUtils.replaceEach(banFormat,
                    new String[]{"%punisher%", "%until%", "%reason%"},
                    new String[]{ban.getPunisher().toString(), ban.getTime() <= 0 ? untilFormat : ban.getTime() + "", ban.getReason()}));

            return;
        } else plugin.getBanResource().remove(ban);

        LoadDataTask loadDataTask = new LoadDataTask(plugin.getStorage(), user);
        new Thread(loadDataTask).run();
    }
}
