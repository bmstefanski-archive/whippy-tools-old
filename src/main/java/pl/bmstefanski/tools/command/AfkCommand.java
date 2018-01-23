package pl.bmstefanski.tools.command;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;

public class AfkCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public AfkCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "afk",
            description = "afk command",
            permission = "afk",
            userOnly = true
    )
    public void afk(CommandSender sender, CommandContext context) {
        Player player = (Player) sender;
        User user = UserManager.getUser(player.getUniqueId());

        if(user.isAfk()){
            user.setAfk(false);
            sendMessage(player, messages.getNoLongerAfk());
            Bukkit.getOnlinePlayers().forEach(p ->
                    sendMessage(p, StringUtils.replace(messages.getNoLongerAfkGlobal(), "%player%", player.getName())));
            return;
        }

        user.setAfk(true);
        sendMessage(player, messages.getAfk());
        Bukkit.getOnlinePlayers().forEach(p ->
                sendMessage(p, StringUtils.replace(messages.getAfkGlobal(), "%player%", player.getName())));
    }
}
