package smp.pizza;

import jeeper.utils.MessageTools;
import jeeper.utils.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Reload implements CommandExecutor, TabCompleter {
    Config config = Main.getPlugin().config();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                Main.getPlugin().config().reload();
                Main.getPlugin().getPlayerData().reload();

                sender.sendMessage(MessageTools.parseFromPath(config, "SMPizza Reloaded"));
            }
        }
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return List.of("reload");
    }
}
