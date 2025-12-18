package smp.pizza;

import jeeper.utils.MessageTools;
import jeeper.utils.config.Config;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NameColor implements CommandExecutor {

    Config playerdata = Main.getPlugin().getPlayerData();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length > 0) {
            if (PlainTextComponentSerializer.plainText().serialize(MessageTools.parseText(args[0]))
                    .equals("")) {
                String code = PlainTextComponentSerializer.plainText().serialize(MessageTools.parseText("\\" + args[0]));
                playerdata.get().set(player.getUniqueId() + ".chatcolor", code);
                playerdata.save();
                playerdata.reload();
                player.sendMessage(MessageTools.parseText("&aDefault chat color set to " + code + "\\" + code));
            } else if (args[0].equalsIgnoreCase("reset")) {
                playerdata.get().set(player.getUniqueId() + ".chatcolor", null);
                playerdata.save();
                playerdata.reload();
                player.sendMessage(MessageTools.parseText("&aDefault chat color reset"));
            } else {
                player.sendMessage(MessageTools.parseText("&cThat is not a valid chat color!"));
            }
            return true;
        }
        player.sendMessage(MessageTools.parseText("&cCorrect Usage: /chatcolor <color code | reset>"));
        return true;
    }
}
