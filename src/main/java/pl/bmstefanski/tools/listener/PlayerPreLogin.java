package pl.bmstefanski.tools.listener;

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
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.runnable.LoadDataTask;

public class PlayerPreLogin implements Listener {

    private final Tools plugin;

    public PlayerPreLogin(Tools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {

        Player player = Bukkit.getPlayer(event.getUniqueId());
        User user = UserManager.getUser(event.getUniqueId());

//        Ban ban = BanManager.getBan(offlinePlayer);

//        if (ban == null) return;
//        if (BanManager.isBanned(offlinePlayer)) {
//            String banFormat = TextUtils.listToString(Messages.BAN_FORMAT);
//            String untilFormat = MessageUtils.fixColor(Messages.PERMANENT_BAN);
//
//            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, StringUtils.replaceEach(banFormat,
//                    new String[]{"%punisher%", "%until%", "%reason%"},
//                    new String[]{ban.getPunisherName(), ban.getUntil() <= 0 ? untilFormat : ban.getUntil() + "", ban.getReason()}));
//
//            return;
//        } else BanManager.removeBan(ban);

        LoadDataTask loadDataTask = new LoadDataTask(plugin.getStorage(), user);
        new Thread(loadDataTask).run();
    }
}
