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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.BooleanUtils;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class GodCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public GodCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "god",
            description = "god command",
            usage = "[player]",
            userOnly = true,
            permission = "god",
            completer = "godCompleter"
    )
    public void god(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;

        if (context.getArgs().length == 0) {
            User user = UserManager.getUser(player.getUniqueId());

            if (user == null) {
                return;
            }

            boolean godState = !user.isGod();
            user.setGod(godState);

            sendMessage(player, StringUtils.replace(messages.getGodSwitched(), "%state%", BooleanUtils.parse(godState)));

            return;
        }

        if (Bukkit.getPlayer(context.getParam(0)) == null) {
            sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
            return;
        }

        Player target = Bukkit.getPlayer(context.getParam(0));
        User user = UserManager.getUser(target.getUniqueId());

        if (user == null) {
            return;
        }

        boolean godState = !user.isGod();
        user.setGod(godState);


        sendMessage(player, StringUtils.replaceEach(messages.getGodSwitchedOther(),
                new String[] {"%state%", "%player%"},
                new String[] {BooleanUtils.parse(godState), target.getName()}));
        sendMessage(target, StringUtils.replace(messages.getGodSwitched(), "%state%", BooleanUtils.parse(godState)));
    }

    public List<String> godCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
