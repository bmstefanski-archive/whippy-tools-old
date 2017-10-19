package pl.bmstefanski.tools.command.normal;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class HealCommand extends CommandImpl {

    public HealCommand() {
        super("heal", "heal command", "/heal [player]", "heal", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;

        if (args.length > 1) {
            Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

        if (args.length == 0) {
            player.setHealth(20D);

            Utils.sendMessage(player, Messages.HEALED);
        } else {
            if (Bukkit.getPlayer(args[0]) == null) {
                Utils.sendMessage(player, Messages.PLAYER_NOT_FOUND.replace("%player%", args[0]));
                return;
            }

            final Player target = Bukkit.getPlayer(args[0]);

            target.setHealth(20D);

            Utils.sendMessage(target, Messages.HEALED);
            Utils.sendMessage(player, Messages.HEALED_OTHER.replace("%player%", target.getName()));
        }

    }
}
