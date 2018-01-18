package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.Ban;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.BanImpl;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;
import pl.bmstefanski.tools.util.TextUtils;

import java.util.List;

public class BanCommand {

    private final Tools plugin;

    public BanCommand(Tools plugin) {
        this.plugin = plugin;
    }

    @CommandInfo (
            name = "ban",
            description = "ban command",
            usage = "[player] [reason]",
            permission = "ban",
            min = 1,
            completer = "banCompleter"
    )

    private void ban(CommandSender commandSender, CommandContext context) {

        Messages messages = plugin.getMessages();

        Player player = (Player) commandSender;
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(context.getParam(0));

        User punisher = UserManager.getUser(player.getUniqueId());
        User punished = UserManager.getUser(offlinePlayer.getUniqueId());

        if (!offlinePlayer.hasPlayedBefore()) {
            MessageUtils.sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
            return;
        }

        if (punished.isBanned()) {
            MessageUtils.sendMessage(commandSender, StringUtils.replace(messages.getAlreadyBanned(), "%player%", offlinePlayer.getName()));
            return;
        }

        String reason = "";

        if (context.getArgs().length == 1) {
            reason = MessageUtils.fixColor(messages.getDefaultReason());
        } else if (context.getArgs().length > 1) reason = MessageUtils.fixColor(StringUtils.join(context.getArgs(), " ", 1, context.getArgs().length));

        Ban ban = new BanImpl(punished.getUUID(), punisher.getUUID());
        ban.setReason(reason);
        ban.setTime(-1);
        plugin.getBanResource().add(ban);


        if (offlinePlayer.isOnline()) {
            String banFormat = TextUtils.listToString(messages.getBanFormat());
            String untilFormat = MessageUtils.fixColor(messages.getPermanentBan());

            Player target = Bukkit.getPlayer(offlinePlayer.getUniqueId());

            target.kickPlayer(StringUtils.replaceEach(banFormat,
                    new String[]{"%punisher%", "%until%", "%reason%"},
                    new String[]{ban.getPunisher().toString(), untilFormat, reason}));
        }

        MessageUtils.sendMessage(commandSender, StringUtils.replace(messages.getSuccessfullyBanned(), "%player%", offlinePlayer.getName()));
    }

    public List<String> banCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
