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
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.BooleanUtils;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class FlyCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public FlyCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "fly",
            description = "fly command",
            usage = "[player]",
            userOnly = true,
            permission = "fly",
            completer = "flyCompleter"
    )
    public void fly(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;

        if (context.getArgs().length == 0) {
            boolean flyState = !player.isFlying();
            player.setAllowFlight(flyState);

            sendMessage(player, StringUtils.replace(messages.getFlySwitched(), "%state%", BooleanUtils.parse(flyState)));

            return;
        }

        if (Bukkit.getPlayer(context.getParam(0)) == null) {
            sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
            return;
        }

        Player target = Bukkit.getPlayer(context.getParam(0));
        boolean flyState = !target.isFlying();

        target.setAllowFlight(flyState);

        sendMessage(player, StringUtils.replaceEach(messages.getFlySwitchedOther(),
                new String[] {"%state%", "%player%"},
                new String[] {BooleanUtils.parse(flyState), target.getName()}));

        sendMessage(target, StringUtils.replace(messages.getFlySwitched(), "%state%", BooleanUtils.parse(flyState)));
    }

    public List<String> flyCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
