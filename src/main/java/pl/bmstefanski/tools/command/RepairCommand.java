package pl.bmstefanski.tools.command;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class RepairCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public RepairCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "repair",
            description = "repair command",
            userOnly = true,
            permission = "repair",
            completer = "repairCompleter"
    )
    public void repair(CommandSender commandSender, CommandContext context) {
        Player player = (Player) commandSender;
        PlayerInventory playerInventory = player.getInventory();

        if (playerInventory.getItemInMainHand().getType() == Material.AIR) {
            sendMessage(player, messages.getCannotRepair());
            return;
        }
        if (playerInventory.getItemInMainHand().getDurability() == playerInventory.getItemInMainHand().getType().getMaxDurability()) {
            sendMessage(player, messages.getCannotRepairFull());
            return;
        }

        playerInventory.getItemInMainHand().setDurability(playerInventory.getItemInMainHand().getType().getMaxDurability());
        sendMessage(player, StringUtils.replace(messages.getRepaired(), "%item%", playerInventory.getItemInMainHand().getType().name().toLowerCase()));

    }

    public List<String> repairCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
