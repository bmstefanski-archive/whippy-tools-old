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
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.Arguments;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class NicknameCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public NicknameCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "nick", min = 1, max = 2, usage = "[player] [nickname]", aliases = {"setnick", "nickname"})
    @Permission("tools.command.nick")
    @GameOnly(false)
    public void command(Arguments arguments) {

        CommandSender sender = arguments.getSender();

        if (arguments.length() == 1) {

            if (!(sender instanceof Player)) {
                sendMessage(sender, messages.getOnlyPlayer());
                return;
            }

            Player player = (Player) sender;
            String nickname = fixColor(arguments.getArgs(0) + ChatColor.RESET);

            player.setDisplayName(nickname);
            player.setPlayerListName(nickname);

            sendMessage(player, StringUtils.replace(messages.getSetNickname(), "%nickname%", arguments.getArgs(0)));

            return;
        }

        if (sender.hasPermission("tools.command.nick.other")) {

            if (Bukkit.getPlayer(arguments.getArgs(0)) == null) {
                sendMessage(sender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", arguments.getArgs(0)));
                return;
            }

            Player target = Bukkit.getPlayer(arguments.getArgs(0));
            String nickname = fixColor(arguments.getArgs(1) + ChatColor.RESET);

            target.setDisplayName(nickname);
            target.setPlayerListName(nickname);

            sendMessage(target, StringUtils.replace(messages.getSetNickname(), "%nickname%", arguments.getArgs(1)));
            sendMessage(sender, StringUtils.replaceEach(messages.getSetNicknameOther(),
                    new String[] {"%player%", "%nickname%"},
                    new String[] {target.getName(), arguments.getArgs(1)}
            ));
        }

    }

}
