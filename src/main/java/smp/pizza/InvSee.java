package smp.pizza;

import jeeper.utils.MessageTools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InvSee implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(MessageTools.parseText("<red>Usage: /invsee <player>"));
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            player.sendMessage(MessageTools.parseText("<red>Player not found."));
            return false;
        }

        player.openInventory(target.getInventory());

        return true;
    }
}
