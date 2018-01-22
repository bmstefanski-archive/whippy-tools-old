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
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.SpawnConfig;
import pl.bmstefanski.tools.util.MessageUtils;

public class SetSpawnCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;
    private final SpawnConfig config;

    public SetSpawnCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
        this.config = plugin.getSpawnConfiguration();
    }

    @CommandInfo(
            name = "setspawn",
            description = "setspawn command",
            permission = "setspawn",
            userOnly = true
    )
    public void setSpawn(CommandSender commandSender, CommandContext context) {
        Player player = (Player) commandSender;
        Location location = player.getLocation();

        String worldName = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        config.setX(x);
        config.setY(y);
        config.setZ(z);
        config.setWorld(worldName);
        config.setExists(true);

        sendMessage(player, StringUtils.replaceEach(messages.getSetspawnSuccess(),
                new String[] {"%x%", "%y%", "%z%", "%world%"},
                new String[] {x + "", y + "", z + "", worldName}));
    }
}
