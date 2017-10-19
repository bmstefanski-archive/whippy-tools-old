package pl.bmstefanski.tools.command.normal;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GamemodeCommand extends CommandImpl{

    public GamemodeCommand() {
        super("gamemode", "gamemode command", "/gamemode 0/1/2/3 [player]", "gamemode", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;

        if (args.length == 0 || args.length > 2) {
            Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

        if (args.length == 1) {
            final GameMode gameMode = parseGameMode(args[0]);

            if (gameMode == null) {
                Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
                return;
            }

            player.setGameMode(gameMode);
            Utils.sendMessage(player, Messages.GAMEMODE_SUCCESS.replace("%gamemode%", gameMode.toString()));
        } else {
            if (Bukkit.getPlayer(args[1]) == null) {
                Utils.sendMessage(player, Messages.PLAYER_NOT_FOUND.replace("%player%", args[1]));
                return;
            }

            final Player target = Bukkit.getPlayer(args[1]);
            final GameMode gameMode = parseGameMode(args[0]);

            if (gameMode == null) {
                Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
                return;
            }

            target.setGameMode(gameMode);

            Utils.sendMessage(target, Messages.GAMEMODE_SUCCESS.replace("%gamemode%", gameMode.toString()));
            Utils.sendMessage(player, Messages.GAMEMODE_SUCCESS_OTHER.replace("%gamemode%", gameMode.toString()).replace("%player%", target.getName()));
        }
    }

    private GameMode getGameMode(String string) {
        for (GameMode mode : GameMode.values()) {
            if (mode.name().toLowerCase().contains(string.toLowerCase())) {
                return mode;
            }
        }
        return null;
    }

    @Deprecated
    private GameMode parseGameMode(String string) {
        if (StringUtils.isNumeric(string)) {
            return GameMode.getByValue(Integer.valueOf(string));
        } else return getGameMode(string);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            final List<String> availableGamemodes = Arrays.asList("0", "1", "2", "3");

            Collections.sort(availableGamemodes);
            return availableGamemodes;
        } else if (args.length == 2) {
            final List<String> availablePlayers = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().startsWith(args[1])) {
                    availablePlayers.add(player.getName());
                }
            }

            Collections.sort(availablePlayers);
            return availablePlayers;

        }

        return null;
    }
}
