package smp.pizza;

import jeeper.utils.MessageTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Ping implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            p.sendMessage(MessageTools.parseText("<green>Your ping is " + p.getPing()));
        }
        else {
            sender.sendMessage(MessageTools.parseText("<green>Your ping is 0"));
        }


        return true;
    }
}
