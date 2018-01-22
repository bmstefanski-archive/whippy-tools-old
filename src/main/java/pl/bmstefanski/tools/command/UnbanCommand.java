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
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.Ban;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.BanManager;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class UnbanCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public UnbanCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "unban",
            description = "unban command",
            usage = "[player]",
            permission = "unban",
            min = 1,
            completer = "unbanCompleter"
    )
    private void unban(CommandSender commandSender, CommandContext context) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(context.getParam(0));
        User user = UserManager.getUser(offlinePlayer.getUniqueId());

        if (!offlinePlayer.hasPlayedBefore()) {
            sendMessage(commandSender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
            return;
        }

        if (!user.isBanned()) {
            sendMessage(commandSender, StringUtils.replace(messages.getNotBanned(), "%player%", offlinePlayer.getName()));
            return;
        }

        Ban ban = BanManager.getBan(user.getUUID());
        plugin.getBanResource().remove(ban);

        sendMessage(commandSender, StringUtils.replace(messages.getSuccessfullyUnbanned(), "%player%", offlinePlayer.getName()));
    }

    public List<String> unbanCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }


}
