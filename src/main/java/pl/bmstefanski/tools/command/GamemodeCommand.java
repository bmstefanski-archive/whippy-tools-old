/*
 MIT License

 Copyright (c) 2018 Whippy Tools

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

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

public class GamemodeCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public GamemodeCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
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

        if (context.getArgs().length == 1) {
            GameMode gameMode = GamemodeUtils.parseGameMode(context.getParam(0));

            if (gameMode == null) {
                sendMessage(player, messages.getGamemodeFail());
                return;
            }

            player.setGameMode(gameMode);
            sendMessage(player, StringUtils.replace(messages.getGamemodeSuccess(), "%gamemode%", gameMode.toString()));

            return;
        }

        if (Bukkit.getPlayer(context.getParam(1)) == null) {
            sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(1)));
            return;
        }

        Player target = Bukkit.getPlayer(context.getParam(1));
        GameMode gameMode = GamemodeUtils.parseGameMode(context.getParam(0));

        if (gameMode == null) {
            sendMessage(player, messages.getGamemodeFail());
            return;
        }

        target.setGameMode(gameMode);

        sendMessage(target, StringUtils.replace(messages.getGamemodeSuccess(), "%gamemode%", gameMode.toString()));
        sendMessage(player, StringUtils.replaceEach(messages.getGamemodeSuccessOther(),
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
