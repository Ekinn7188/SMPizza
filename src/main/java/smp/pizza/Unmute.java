package smp.pizza;

import jeeper.utils.MessageTools;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Unmute implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MessageTools.parseText("<green>/unmute <player>"));
            return true;
        }

        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);

        if (!p.hasPlayedBefore()) {
            sender.sendMessage("<red>" + args[0] + " has not played before.");
        }

        Main.getPlugin().getPlayerData().get().set(p.getUniqueId() + ".muted", false);


        sender.sendMessage(MessageTools.parseText("<green>You've successfully unmuted " + args[0]));

        return false;
    }
}
