package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.configuration.Messages;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.util.BooleanUtils;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GodCommand {

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

        if (context.getArgs().length == 0) {
            User user = User.get(player.getUniqueId());

            boolean godState = !user.isGod();
            user.setGod(godState);

            MessageUtils.sendMessage(player, StringUtils.replace(Messages.GOD_SWITCHED, "%state%", BooleanUtils.parse(godState)));
        } else {
            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(Messages.PLAYER_NOT_FOUND, "%player%", context.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(0));
            User user = User.get(target.getUniqueId());

            boolean godState = !user.isGod();
            user.setGod(godState);


            MessageUtils.sendMessage(player, StringUtils.replaceEach(Messages.GOD_SWITCHED_OTHER,
                    new String[] {"%state%", "%player%"},
                    new String[] {BooleanUtils.parse(godState), target.getName()}));
            MessageUtils.sendMessage(target, StringUtils.replace(Messages.GOD_SWITCHED, "%state%", BooleanUtils.parse(godState)));
        }
    }

    public List<String> godCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
