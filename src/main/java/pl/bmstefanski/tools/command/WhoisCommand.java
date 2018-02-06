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
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.Arguments;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.Completer;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.*;

import java.util.List;

public class WhoisCommand implements Messageable, Parser {

    private final Tools plugin;
    private final Messages messages;

    public WhoisCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "whois", usage = "[player]", max = 1)
    @Permission("tools.command.whois")
    @GameOnly(false)
    public void command(Arguments arguments) {

        CommandSender commandSender = arguments.getSender();

        if (arguments.getArgs().length == 0) {

            if (!(commandSender instanceof Player)) {
                sendMessage(commandSender, messages.getOnlyPlayer());
                return;
            }

            Player player = (Player) commandSender;
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());

            sendMessage(player, messageContent(offlinePlayer));

            return;
        }

        if (commandSender.hasPermission("tools.command.whois.other")) {

            if (Bukkit.getPlayer(arguments.getArgs(0)) == null) {
                sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", arguments.getArgs(0)));
                return;
            }

            Player target = Bukkit.getPlayer(arguments.getArgs(0));
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target.getUniqueId());

            sendMessage(commandSender, messageContent(offlinePlayer));

        }

    }

    private String messageContent(OfflinePlayer offlinePlayer) {
        User user = UserManager.getUser(offlinePlayer.getUniqueId());

        Location location = offlinePlayer.getPlayer().getLocation();
        String playerHealth = offlinePlayer.getPlayer().getHealth() + "/20";
        String playerFoodLevel = offlinePlayer.getPlayer().getFoodLevel() + "/20";
        String playerGamemode = offlinePlayer.getPlayer().getGameMode().toString().toLowerCase();
        String playerLocation = "("
                + offlinePlayer.getPlayer().getWorld().getName() + ", "
                + location.getBlockX() + ", "
                + location.getBlockY() + ", "
                + location.getBlockZ() + ")";
        String playerJoin = parseLong(offlinePlayer.getFirstPlayed());
        String playerLast = user.isOnline() ? "online" : parseLong(offlinePlayer.getLastPlayed());
        String whois = listToString(messages.getWhois());

        return StringUtils.replaceEach(whois,
                new String[] {"%nickname%", "%uuid%", "%ip%", "%registered%", "%last%", "%location%", "%hp%", "%hunger%", "%gamemode%", "%god%", "%fly%"},
                new String[] {offlinePlayer.getName(), offlinePlayer.getUniqueId().toString(), offlinePlayer.getPlayer().getAddress().getAddress().toString(),
                        playerJoin, playerLast, playerLocation, playerHealth, playerFoodLevel, playerGamemode, parseBoolean(user.isGod()),
                        parseBoolean(offlinePlayer.getPlayer().isFlying())
                });
    }

    @Completer("whois")
    public List<String> completer(Arguments arguments) {
        List<String> availableList = TabCompleterUtils.getAvailableList(arguments);
        if (availableList != null) return availableList;

        return null;
    }
}
