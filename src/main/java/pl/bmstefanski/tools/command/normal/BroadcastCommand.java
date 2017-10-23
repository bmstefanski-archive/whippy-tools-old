package pl.bmstefanski.tools.command.normal;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class BroadcastCommand extends CommandImpl {

    public BroadcastCommand() {
        super("broadcast", "broadcast command", "/broadcast [message]", "broadcast", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final StringBuilder stringBuilder = new StringBuilder();

        if (args.length == 0) {
            Utils.sendMessage(commandSender, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

        stringBuilder.setLength(0);
        for (String string : args) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(string);
        }

        Bukkit.broadcastMessage(Utils.fixColor(Messages.BROADCAST_FORMAT
                .replace("%message%", stringBuilder.toString())));
    }
}
