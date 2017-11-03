package pl.bmstefanski.tools.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class BroadcastCommand extends CommandImpl {

    public BroadcastCommand() {
        super("broadcast", "broadcast command", "/broadcast action/title/subtitle/chat [message]", "broadcast", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final StringBuilder stringBuilder = new StringBuilder();
        final Player player = (Player) commandSender;

        if (args.length == 0) {
            Utils.sendMessage(commandSender, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

/*        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("action")) {
                new PlayOutChatPacket().sendPacket(player, getMessage(stringBuilder, args), ChatMessageType.GAME_INFO);
            } else if (args[0].equalsIgnoreCase("title")) {
                new PlayOutChatPacket().sendPacket(player, getMessage(stringBuilder, args), ChatMessageType.SYSTEM);
            } else if (args[0].equalsIgnoreCase("subtitle")) {
                new PlayOutChatPacket().sendPacket(player, getMessage(stringBuilder, args), ChatMessageType.CHAT);
            } else if (args[0].equalsIgnoreCase("chat")) {
                Bukkit.broadcastMessage(Utils.fixColor(Messages.BROADCAST_FORMAT
                        .replace("%message%", getMessage(stringBuilder, args))));
            }
        }*/

    }

    private String getMessage(StringBuilder stringBuilder, String[] args) {

        stringBuilder.setLength(0);

        for (String string : args) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }
}
