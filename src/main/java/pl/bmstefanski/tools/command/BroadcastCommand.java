package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BroadcastCommand {

    @CommandInfo (
            name = {"broadcast", "bc"},
            description = "broadcast command",
            usage = "<action/title/subtitle/chat>",
            userOnly = true,
            permission = "broadcast",
            min = 2,
            completer = "broadcastCompleter"
    )

    public void broadcast(CommandSender commandSender, CommandContext context) {

        StringBuilder stringBuilder = new StringBuilder();
        Player player = (Player) commandSender;

        for (int i = 1; i < context.getArgs().length; i++) {
            stringBuilder.append(" ");
            stringBuilder.append(context.getArgs()[i]);
        }

        String message = MessageUtils.fixColor(stringBuilder.toString());

        if (context.getArgs().length > 1) {
            if (context.getParam(0).equalsIgnoreCase("action")) {
//                ReflectionUtils.sendPacket(new PacketPlayOutTitle(message, -1, -1, -1).getActionBar());
            } else if (context.getParam(0).equalsIgnoreCase("title")) {
//                ReflectionUtils.sendPacket(new PacketPlayOutTitle(message, -1, -1, -1).getTitle());
            } else if (context.getParam(0).equalsIgnoreCase("subtitle")) {
//                ReflectionUtils.sendPacket(new PacketPlayOutTitle("", -1, -1, -1).getTitle());
//                ReflectionUtils.sendPacket(new PacketPlayOutTitle(message, -1, -1, -1).getSubTitle());
            } else if (context.getParam(0).equalsIgnoreCase("chat")) {
                Bukkit.broadcastMessage(MessageUtils.fixColor(StringUtils.replace(Messages.BROADCAST_FORMAT, "%message%", stringBuilder.toString())));
            }
        }
    }

    public List<String> broadcastCompleter(CommandSender commandSender, CommandContext context) {
        if (context.getArgs().length == 1) {
            List<String> availableGamemodes = Arrays.asList("action", "title", "subtitle", "chat");

            Collections.sort(availableGamemodes);
            return availableGamemodes;
        }

        return null;
    }
}
