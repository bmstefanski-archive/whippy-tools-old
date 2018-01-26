package pl.bmstefanski.tools.command;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;
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
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.AIR) {
            sendMessage(player, messages.getCannotRepair());
            return;
        }

        if (item.getDurability() == item.getType().getMaxDurability()) {
            sendMessage(player, messages.getCannotRepairFull());
            return;
        }

        item.setDurability((short) 0);
        sendMessage(player, StringUtils.replace(messages.getRepaired(), "%item%", item.getType().name().toLowerCase()));

    }
}
