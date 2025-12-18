package smp.pizza;

import jeeper.utils.MessageTools;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Ping implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                // Self-ping
                p.sendMessage(MessageTools.parseText("<green>Your ping is " + p.getPing()));
            } else {
                // Other player's ping
                String playerName = args[0];

                Player player = Bukkit.getPlayer(playerName);

                if (player == null) {
                    p.sendMessage(MessageTools.parseText("<red>" + playerName + " is not online"));
                }
                else {
                    p.sendMessage(MessageTools.parseText("<green>" + playerName + "'s ping is " + player.getPing()));
                }
            }
        }
        else {
            // Console duh
            sender.sendMessage(MessageTools.parseText("<green>Your ping is 0"));
        }


        return true;
    }
}
