package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.GamemodeUtils;
import pl.bmstefanski.tools.util.MessageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GamemodeCommand {

    @CommandInfo(name = {"gamemode", "gm"}, description = "gamemode command", usage = "0/1/2/3 [player]", userOnly = true, permission = "gamemode", completer = "gamemodeCompleter", min = 1)
    public void gamemode(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;

        if (context.getArgs().length == 1) {
            final GameMode gameMode = GamemodeUtils.parseGameMode(context.getParam(0));

            if (gameMode == null) {
                MessageUtils.sendMessage(player, Messages.GAMEMODE_FAIL);
                return;
            }

            player.setGameMode(gameMode);
            MessageUtils.sendMessage(player, StringUtils.replace(Messages.GAMEMODE_SUCCESS, "%gamemode%", gameMode.toString()));
        } else {
            if (Bukkit.getPlayer(context.getParam(1)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(Messages.PLAYER_NOT_FOUND, "%player%", context.getParam(1)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(1));
            GameMode gameMode = GamemodeUtils.parseGameMode(context.getParam(0));

            if (gameMode == null) {
                MessageUtils.sendMessage(player, Messages.GAMEMODE_FAIL);
                return;
            }

            target.setGameMode(gameMode);

            MessageUtils.sendMessage(target, StringUtils.replace(Messages.GAMEMODE_SUCCESS, "%gamemode%", gameMode.toString()));
            MessageUtils.sendMessage(player, StringUtils.replaceEach(Messages.GAMEMODE_SUCCESS_OTHER,
                    new String[] {"%gamemode%", "%player%"},
                    new String[] {gameMode.toString(), target.getName()}));
        }
    }

    public List<String> gamemodeCompleter(CommandSender sender, CommandContext context) {
        if (context.getArgs().length == 1) {
            List<String> availableGamemodes = Arrays.asList("0", "1", "2", "3");

            Collections.sort(availableGamemodes);
            return availableGamemodes;
        } else if (context.getArgs().length == 2) {
            List<String> availableList = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().startsWith(context.getParam(0).toLowerCase())) {
                    availableList.add(player.getName());
                }
            }

            Collections.sort(availableList);
            return availableList;
        }

        return null;
    }
}
