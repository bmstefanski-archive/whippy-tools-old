package pl.bmstefanski.tools.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;

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
            completer = "broadcastCompleter"
    )

    public void broadcast(CommandSender commandSender, CommandContext context) {

        StringBuilder stringBuilder = new StringBuilder();
        Player player = (Player) commandSender;

        // TODO FUUU BRZYDKIE KURDĘ ASAP
/*        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("action")) {
                new PlayOutChatPacket().sendPacket(player, getMessage(stringBuilder, args), ChatMessageType.GAME_INFO);
            } else if (args[0].equalsIgnoreCase("title")) {
                new PlayOutChatPacket().sendPacket(player, getMessage(stringBuilder, args), ChatMessageType.SYSTEM);
            } else if (args[0].equalsIgnoreCase("subtitle")) {
                new PlayOutChatPacket().sendPacket(player, getMessage(stringBuilder, args), ChatMessageType.CHAT);
            } else if (args[0].equalsIgnoreCase("chat")) {
                Bukkit.broadcastMessage(MessageUtils.fixColor(Messages.BROADCAST_FORMAT
                        .replace("%message%", getMessage(stringBuilder, args))));
            }
        }*/

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
