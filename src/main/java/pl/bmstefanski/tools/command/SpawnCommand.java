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

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.manager.TeleportManager;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.SpawnConfig;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class SpawnCommand {

    private final Tools plugin;
    private final Messages messages;
    private final SpawnConfig config;

    public SpawnCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
        this.config = plugin.getSpawnConfiguration();
    }

    @CommandInfo(
            name = "spawn",
            description = "spawn command",
            permission = "spawn",
            userOnly = true,
            usage = "[player]",
            completer = "spawnCompleter"
    )
    public void spawn(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;
        TeleportManager taskManager = new TeleportManager(plugin);

        if (config.getExists()) {

            int x = config.getX();
            int y = config.getY();
            int z = config.getZ();
            String worldName = config.getWorld();

            Location location = new Location(Bukkit.getWorld(worldName), x, y, z);

            if (context.getArgs().length == 0) {
                taskManager.teleport(player, location, plugin.getConfiguration().getSpawnDelay());
                return;
            }

            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(0));
            target.teleport(location);

            return;
        }

        MessageUtils.sendMessage(player, messages.getSpawnFailed());
    }

    public List<String> spawnCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
