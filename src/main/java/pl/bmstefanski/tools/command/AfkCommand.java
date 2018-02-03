package pl.bmstefanski.tools.command;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.Arguments;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class AfkCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public AfkCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "afk")
    @Permission("tools.command.afk")
    @GameOnly
    public void command(Arguments arguments) {
        Player player = (Player) arguments.getSender();
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
