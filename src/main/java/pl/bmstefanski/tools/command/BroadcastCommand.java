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

    public BroadcastCommand(Tools plugin) {
        this.plugin = plugin;
    }

    @CommandInfo (
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

        Messages messages = plugin.getMessages();

        for (int i = 1; i < context.getArgs().length; i++) {
            stringBuilder.append(" ");
            stringBuilder.append(context.getArgs()[i]);
        }

        String message = stringBuilder.toString();
        Object reset = PacketPlayOutTitle.getPacket(EnumTitleAction.RESET, "", -1, -1, -1);


        // todo builder do packetplayouttitle :D

        switch (context.getParam(0)) {
            case "action":
                List<Object> actionPackets = Arrays.asList(
                        reset,
                        PacketPlayOutTitle.getPacket(EnumTitleAction.ACTIONBAR, message, -1, -1, -1)
                );

                PacketSender.sendPacket(Bukkit.getOnlinePlayers(), actionPackets);
                break;

            case "title":
                List<Object> titlePackets = Arrays.asList(
                        reset,
                        PacketPlayOutTitle.getPacket(EnumTitleAction.TITLE, message, -1, -1, -1)
                );

                PacketSender.sendPacket(Bukkit.getOnlinePlayers(), titlePackets);
                break;

            case "subtitle":
                List<Object> subtitlePackets = Arrays.asList(
                        reset,
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
