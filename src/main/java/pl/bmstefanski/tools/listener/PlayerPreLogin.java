package pl.bmstefanski.tools.listener;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.basic.Ban;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.basic.util.BanUtils;
import pl.bmstefanski.tools.configuration.Messages;
import pl.bmstefanski.tools.runnable.LoadDataTask;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TextUtils;

public class PlayerPreLogin implements Listener {

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        User user = User.get(event.getUniqueId());
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(event.getUniqueId());
        Ban ban = BanUtils.getBan(offlinePlayer);

        if (ban == null) return;
        if (BanUtils.isBanned(offlinePlayer)) {
            String banFormat = TextUtils.listToString(Messages.BAN_FORMAT);
            String untilFormat = MessageUtils.fixColor(Messages.PERMANENT_BAN);

            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, StringUtils.replaceEach(banFormat,
                    new String[]{"%punisher%", "%until%", "%reason%"},
                    new String[]{ban.getPunisherName(), ban.getUntil() <= 0 ? untilFormat : ban.getUntil() + "", ban.getReason()}));

            return;
        } else BanUtils.removeBan(ban);

        new LoadDataTask(user).runTaskAsynchronously(Tools.getInstance());
    }
}
