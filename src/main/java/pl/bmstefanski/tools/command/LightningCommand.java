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
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.Arguments;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class LightningCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public LightningCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "lightning", usage = "[player]", max = 1, aliases = {"thorn"})
    @Permission("tools.command.lightning")
    @GameOnly(false)
    public void command(Arguments arguments) {

        CommandSender sender = arguments.getSender();

        if (arguments.getArgs().length == 0) {

            if (!(sender instanceof Player)) {
                sendMessage(sender, messages.getOnlyPlayer());
                return;
            }

            Player player = (Player) sender;
            World world = player.getWorld();

            world.strikeLightning(player.getLocation());

            sendMessage(player, messages.getStruck());

            return;
        }

        if (sender.hasPermission("tools.command.lightning.other")) {

            if (Bukkit.getPlayer(arguments.getArgs(0)) == null) {
                sendMessage(sender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", arguments.getArgs(0)));
                return;
            }

            Player target = Bukkit.getPlayer(arguments.getArgs(0));

            World world = target.getWorld();
            world.strikeLightning(target.getLocation());

            sendMessage(sender, StringUtils.replace(messages.getStruckOther(), "%player%", target.getName()));
            sendMessage(target, messages.getStruck());
        }

    }

}
