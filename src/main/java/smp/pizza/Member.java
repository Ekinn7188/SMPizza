package smp.pizza;

import jeeper.utils.MessageTools;
import jeeper.utils.config.Config;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Member implements CommandExecutor {
    Config playerdata = Main.getPlugin().getPlayerData();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MessageTools.parseText("<green>/member <player>"));
            return true;
        }
        OfflinePlayer player = Main.getPlugin().getServer().getOfflinePlayer(args[0]);

        if (!player.hasPlayedBefore()){
            sender.sendMessage(MessageTools.parseText("<green>Player has not played before."));
        }

        sender.sendMessage(MessageTools.parseText("<green>Successfully gave " + args[0] + " the member tag."));
        playerdata.get().set(player.getUniqueId() + ".tag", null);
        playerdata.save();
        playerdata.reload();

        return false;
    }
}
