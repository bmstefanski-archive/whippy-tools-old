package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.BooleanUtils;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class GodCommand {

    private final Tools plugin;

    public GodCommand(Tools plugin) {
        this.plugin = plugin;
    }

    @CommandInfo (
            name = "god",
            description = "god command",
            usage = "[player]",
            userOnly = true,
            permission = "god",
            completer = "godCompleter"
    )

    public void god(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;
        Messages messages = plugin.getMessages();

        if (context.getArgs().length == 0) {
            User user = UserManager.getUser(player.getUniqueId());

            if (user == null) {
                return;
            }

            boolean godState = !user.isGod();
            user.setGod(godState);

            MessageUtils.sendMessage(player, StringUtils.replace(messages.getGodSwitched(), "%state%", BooleanUtils.parse(godState)));
        } else {
            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(0));
            User user = UserManager.getUser(target.getUniqueId());

            if (user == null) {
                return;
            }

            boolean godState = !user.isGod();
            user.setGod(godState);


            MessageUtils.sendMessage(player, StringUtils.replaceEach(messages.getGodSwitchedOther(),
                    new String[] {"%state%", "%player%"},
                    new String[] {BooleanUtils.parse(godState), target.getName()}));
            MessageUtils.sendMessage(target, StringUtils.replace(messages.getGodSwitched(), "%state%", BooleanUtils.parse(godState)));
        }
    }

    public List<String> godCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
