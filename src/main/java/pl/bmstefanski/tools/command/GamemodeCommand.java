package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.GamemodeUtils;
import pl.bmstefanski.tools.util.MessageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GamemodeCommand {

    private final Tools plugin;

    public GamemodeCommand(Tools plugin) {
        this.plugin = plugin;
    }

    @CommandInfo (
            name = {"gamemode", "gm"},
            description = "gamemode command",
            usage = "0/1/2/3 [player]",
            userOnly = true,
            permission = "gamemode",
            completer = "gamemodeCompleter",
            min = 1
    )

    public void gamemode(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;
        Messages messages = plugin.getMessages();

        if (context.getArgs().length == 1) {
            GameMode gameMode = GamemodeUtils.parseGameMode(context.getParam(0));

            if (gameMode == null) {
                MessageUtils.sendMessage(player, messages.getGamemodeFail());
                return;
            }

            player.setGameMode(gameMode);
            MessageUtils.sendMessage(player, StringUtils.replace(messages.getGamemodeSuccess(), "%gamemode%", gameMode.toString()));

            return;
        }

        if (Bukkit.getPlayer(context.getParam(1)) == null) {
            MessageUtils.sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(1)));
            return;
        }

        Player target = Bukkit.getPlayer(context.getParam(1));
        GameMode gameMode = GamemodeUtils.parseGameMode(context.getParam(0));

        if (gameMode == null) {
            MessageUtils.sendMessage(player, messages.getGamemodeFail());
            return;
        }

        target.setGameMode(gameMode);

        MessageUtils.sendMessage(target, StringUtils.replace(messages.getGamemodeSuccess(), "%gamemode%", gameMode.toString()));
        MessageUtils.sendMessage(player, StringUtils.replaceEach(messages.getGamemodeSuccessOther(),
                new String[] {"%gamemode%", "%player%"},
                new String[] {gameMode.toString(), target.getName()}));
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
