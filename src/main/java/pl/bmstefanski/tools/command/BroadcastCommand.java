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

import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.reflect.PacketSender;
import pl.bmstefanski.tools.util.reflect.transition.PacketPlayOutTitle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class BroadcastCommand {

    private final Tools plugin;
    private final Messages messages;

    public BroadcastCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = {"broadcast", "bc"},
            description = "broadcast command",
            usage = "<action/title/subtitle/chat>",
            userOnly = true,
            permission = "broadcast",
            min = 2,
            completer = "broadcastCompleter"
    )
    public void broadcast(CommandSender commandSender, CommandContext context) {

        StringBuilder stringBuilder = new StringBuilder();
        Player player = (Player) commandSender;

        for (int i = 1; i < context.getArgs().length; i++) {
            stringBuilder.append(" ");
            stringBuilder.append(context.getArgs()[i]);
        }

        String message = stringBuilder.toString();

        // todo builder do packetplayouttitle :D

        switch (context.getParam(0)) {
            case "action":
                List<Object> actionPackets = Collections.singletonList(
                        PacketPlayOutTitle.getPacket(EnumTitleAction.ACTIONBAR, message, -1, -1, -1)
                );

                PacketSender.sendPacket(Bukkit.getOnlinePlayers(), actionPackets);
                break;

            case "title":
                List<Object> titlePackets = Collections.singletonList(
                        PacketPlayOutTitle.getPacket(EnumTitleAction.TITLE, message, -1, -1, -1)
                );

                PacketSender.sendPacket(Bukkit.getOnlinePlayers(), titlePackets);
                break;

            case "subtitle":
                List<Object> subtitlePackets = Arrays.asList(
                        PacketPlayOutTitle.getPacket(EnumTitleAction.TITLE, "", -1, -1, -1),
                        PacketPlayOutTitle.getPacket(EnumTitleAction.SUBTITLE, message, -1, -1, -1)
                );

                PacketSender.sendPacket(Bukkit.getOnlinePlayers(), subtitlePackets);
                break;

            case "chat":
                Bukkit.broadcastMessage(MessageUtils.fixColor(StringUtils.replace(messages.getBroadcastFormat(), "%message%", stringBuilder.toString())));
                break;
        }
    }

    public List<String> broadcastCompleter(CommandSender commandSender, CommandContext context) {
        if (context.getArgs().length == 1) {
            List<String> availableGamemodes = Arrays.asList("action", "title", "subtitle", "chat");

            Collections.sort(availableGamemodes);
            return availableGamemodes;
        }

        return null;
    }
}
