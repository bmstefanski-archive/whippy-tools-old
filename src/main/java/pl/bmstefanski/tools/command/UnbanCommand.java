package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.Ban;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.BanManager;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class UnbanCommand {

    private final Tools plugin;
    private final Messages messages;

    public UnbanCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "unban",
            description = "unban command",
            usage = "[player]",
            permission = "unban",
            min = 1,
            completer = "unbanCompleter"
    )
    private void unban(CommandSender commandSender, CommandContext context) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(context.getParam(0));
        User user = UserManager.getUser(offlinePlayer.getUniqueId());

        if (!offlinePlayer.hasPlayedBefore()) {
            MessageUtils.sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
            return;
        }

        if (!user.isBanned()) {
            MessageUtils.sendMessage(commandSender, StringUtils.replace(messages.getNotBanned(), "%player%", offlinePlayer.getName()));
            return;
        }

        Ban ban = BanManager.getBan(user.getUUID());
        plugin.getBanResource().remove(ban);

        MessageUtils.sendMessage(commandSender, StringUtils.replace(messages.getSuccessfullyUnbanned(), "%player%", offlinePlayer.getName()));
    }

    public List<String> unbanCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }


}
