package smp.pizza;

import jeeper.utils.MessageTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Hat implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        ItemStack[] armor = p.getInventory().getArmorContents();

        System.out.println(Arrays.toString(armor));
        ItemStack headItem = armor[3];
        armor[3] = p.getInventory().getItemInMainHand();

        p.getInventory().setArmorContents(armor);

        System.out.println(Arrays.toString(armor));
        p.getInventory().setItemInMainHand(headItem);

        p.updateInventory();

        p.sendMessage(MessageTools.parseText("<green>Your hat has been set!"));

        return false;
    }
}
