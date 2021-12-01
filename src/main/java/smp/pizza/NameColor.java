package smp.pizza;

import jeeper.utils.config.ConfigSetup;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import jeeper.utils.MessageTools;

public class NameColor implements CommandExecutor {

    ConfigSetup playerdata = Main.getPlugin().getPlayerData();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        System.out.println("player");

        if (args.length > 0) {
            System.out.println("args > 0");
            if (PlainTextComponentSerializer.plainText().serialize(MessageTools.parseText(args[0]))
                    .equals("")) {
                System.out.println("is a code");
                playerdata.get().set(player.getUniqueId() + ".chatcolor", args[0]);
                playerdata.save();
                playerdata.reload();
                player.sendMessage(MessageTools.parseText("&aDefault chat color set to ").append(Component.text(args[0])));
            } else {
                System.out.println("not a code");
                player.sendMessage(MessageTools.parseText("&cThat is not a valid chat color!"));
            }
            return true;
        }
        System.out.println("incorrect usage");
        player.sendMessage(MessageTools.parseText("&cCorrect Usage: /chatcolor <color code>"));
        return true;
    }
}
