package smp.pizza;

import jeeper.utils.MessageTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KnockbackCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(MessageTools.parseText("<red>You must supply a level of knockback!"));
            return false;
        }

        try {
            int level = Integer.parseInt(args[0]);
            ItemStack itemStack = p.getInventory().getItemInMainHand();
            itemStack.addEnchantment(Enchantment.KNOCKBACK, level);
        } catch (NumberFormatException e) {
            sender.sendMessage(MessageTools.parseText("<red>Invalid level!"));
            return false;
        }



        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].equals("")) {
            return List.of("<enchant_level>");
        }
        return List.of();
    }
}
