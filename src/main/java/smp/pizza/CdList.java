package smp.pizza;

import jeeper.utils.MessageTools;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CdList implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MessageTools.parseText(
        """
                <aqua>----[ Custom Discs ]----</aqua>
                <green>/customdisc create <filename> "Custom Lore" <dark_gray>-<gray> Creates a custom music disc.
                <green>/customdisc download <url> <filename.extension> <dark_gray>-<gray> Downloads a file from a given URL.
                <green>/customdisc list <dark_gray>-<gray> Lists all files currently downloaded.
                <aqua>------------------------</aqua>"""));
            return true;
        }

        switch (args[0]) {
            case "list":
                try {
                    List<Path> stream = Files.list(new File(Main.getPlugin().getDataFolder().getParent() + "/CustomDiscs/musicdata").toPath()).toList();
                    List<String> result = stream.stream().map(x -> x.toString().substring(30)).toList();

                    StringBuilder sb = new StringBuilder();

                    sb.append("<aqua>----[ Downloaded Files ]----</aqua>\n<green>");

                    for (String fileName : result) {
                        sb.append(fileName).append(" ");
                    }

                    sb.append("\n<aqua>------------------------</aqua>");

                    sender.sendMessage(MessageTools.parseText(sb.toString()));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case "create":
                if (sender instanceof Player player) {
                    player.performCommand("customdiscs:cd create " +
                            String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                }
                break;
            case "download":
                if (sender instanceof Player player) {
                    player.performCommand("customdiscs:cd download " +
                            String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                }
                break;
            default:
                sender.sendMessage(MessageTools.parseText(
                        """
                                <aqua>----[ Custom Discs ]----</aqua>
                                <green>/customdisc create <filename> "Custom Lore" <dark_gray>-<gray> Creates a custom music disc.
                                <green>/customdisc download <url> <filename.extension> <dark_gray>-<gray> Downloads a file from a given URL.
                                <green>/customdisc list <dark_gray>-<gray> Lists all files currently downloaded.
                                <aqua>------------------------</aqua>"""));
                break;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].equals("")) {
            return List.of("list", "create", "download");
        }
        if (args.length == 2) {
            if (args[0].equals("download")) {
                return List.of("<url>");
            }
            if (args[0].equals("create")) {
                List<Path> stream = null;
                try {
                    stream = Files.list(new File(Main.getPlugin().getDataFolder().getParent() + "\\CustomDiscs\\musicdata").toPath()).toList();

                    return stream.stream().map(x -> x.toString().substring(30)).toList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (args.length == 3) {
            if (args[0].equals("download")) {
                return List.of("<filename.extension>");
            }
            if (args[0].equals("create")) {
                return List.of("\"Custom Lore\"");
            }
        }

        return List.of();
    }
}
