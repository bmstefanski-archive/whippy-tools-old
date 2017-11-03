package pl.bmstefanski.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.object.User;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class GodCommand extends CommandImpl {

    public GodCommand() {
        super("god", "god command", "/god [player]", "god", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;

        if (args.length > 1) {
            Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

        if (args.length == 0) {
            final User user = User.get(player.getUniqueId());
            final boolean godState = !user.isGod();
            user.setGod(godState);

            Utils.sendMessage(player, Messages.GOD_SWITCHED.replace("%state%", Utils.parseBoolean(godState)));
        } else {
            if (Bukkit.getPlayer(args[0]) == null) {
                Utils.sendMessage(player, Messages.PLAYER_NOT_FOUND.replace("%player%", args[0]));
                return;
            }

            final Player target = Bukkit.getPlayer(args[0]);
            final User user = User.get(target.getUniqueId());
            final boolean godState = !user.isGod();

            user.setGod(godState);

            Utils.sendMessage(player, Messages.GOD_SWITCHED_OTHER
                    .replace("%state%", Utils.parseBoolean(godState))
                    .replace("%player%", target.getName()));
            Utils.sendMessage(target, Messages.GOD_SWITCHED.replace("%state%", Utils.parseBoolean(godState)));
        }
    }
}
