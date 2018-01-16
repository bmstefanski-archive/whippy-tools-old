package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class UnbanCommand {

    @CommandInfo (
            name = "unban",
            description = "unban command",
            usage = "[player]",
            permission = "unban",
            min = 1,
            completer = "unbanCompleter"
    )

    private void unban(CommandSender commandSender, CommandContext context) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(context.getParam(0));

        if (!offlinePlayer.hasPlayedBefore()) {
            MessageUtils.sendMessage(commandSender, StringUtils.replace(Messages.PLAYER_NOT_FOUND, "%player%", context.getParam(0)));
            return;
        }

//        if (!BanManager.isBanned(offlinePlayer)) {
//            MessageUtils.sendMessage(commandSender, StringUtils.replace(Messages.NOT_BANNED, "%player%", offlinePlayer.getName()));
//            return;
//        }
//
//        Ban ban = BanManager.getBan(offlinePlayer);
//        BanManager.removeBan(ban);

        MessageUtils.sendMessage(commandSender, StringUtils.replace(Messages.SUCCESSFULLY_UNBANNED, "%player%", offlinePlayer.getName()));
    }

    public List<String> unbanCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }


}
