package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.BooleanUtils;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class FlyCommand {

    private final Tools plugin;
    private final Messages messages;

    public FlyCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "fly",
            description = "fly command",
            usage = "[player]",
            userOnly = true,
            permission = "fly",
            completer = "flyCompleter"
    )
    public void fly(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;

        if (context.getArgs().length == 0) {
            boolean flyState = !player.isFlying();
            player.setAllowFlight(flyState);

            MessageUtils.sendMessage(player, StringUtils.replace(messages.getFlySwitched(), "%state%", BooleanUtils.parse(flyState)));

            return;
        }

        if (Bukkit.getPlayer(context.getParam(0)) == null) {
            MessageUtils.sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
            return;
        }

        Player target = Bukkit.getPlayer(context.getParam(0));
        boolean flyState = !target.isFlying();

        target.setAllowFlight(flyState);

        MessageUtils.sendMessage(player, StringUtils.replaceEach(messages.getFlySwitchedOther(),
                new String[] {"%state%", "%player%"},
                new String[] {BooleanUtils.parse(flyState), target.getName()}));

        MessageUtils.sendMessage(target, StringUtils.replace(messages.getFlySwitched(), "%state%", BooleanUtils.parse(flyState)));
    }

    public List<String> flyCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
