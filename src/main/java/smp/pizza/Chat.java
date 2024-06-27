package smp.pizza;

import com.vdurmont.emoji.EmojiParser;
import io.papermc.paper.event.player.AsyncChatEvent;
import jeeper.utils.MessageTools;
import jeeper.utils.config.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class Chat implements Listener {

    Config playerdata = Main.getPlugin().getPlayerData();
    Config config = Main.getPlugin().config();

    @EventHandler
    public void onChat(AsyncChatEvent e){
        e.setCancelled(true);

        String chatcolor = playerdata.get().getString(e.getPlayer().getUniqueId() + ".chatcolor");
        if (chatcolor == null){
            chatcolor = "<#42EB8a>";
        }

        Player player = e.getPlayer();

        String messageString = PlainTextComponentSerializer.plainText().serialize(e.message());
        messageString = EmojiParser.parseToUnicode(messageString);


        String discordMessageString = PlainTextComponentSerializer.plainText().serialize(MessageTools.parseText(messageString))
                .replaceAll("_", "\\\\_")
                .replaceAll("\\*", "\\\\*")
                .replaceAll("~", "\\\\~");


        Component tag = Component.empty();

        String role = playerdata.get().getString(player.getUniqueId() + ".tag");

        if (role != null) {
            if (role.equals("admin")) {
                // Sword character \uD83D\uDDE1
                tag = MessageTools.parseText(
                        "<hover:show_text:'&7This player is an " + chatcolor + "Admin&7. &7They have operator permissions. They also have access to the server's files. If you need anything, these are the people to ask.'>" +
                                "<dark_gray>[" + chatcolor + "\uD83D\uDDE1"+ chatcolor.replace("<", "</") + "]</dark_gray></hover> ");
            }
            else if (role.equals("mod")) {
                // Bow character \uD83C\uDFF9
                tag = MessageTools.parseText(
                        "<hover:show_text:'&7This player is a " + chatcolor + "Mod&7. &7They have coreinspect and limited console access. If you need a container checked, or if you a question, then these are the people to ask.'>" +
                        "<dark_gray>[" + chatcolor + "\uD83C\uDFF9" + chatcolor.replace("<", "</") + "]</dark_gray></hover> ");
            }
        }

        Component replacedText = MessageTools.parseFromPath(config, "Chat",
                Placeholder.component("tag", tag),
                Placeholder.parsed("player", chatcolor + player.getName() + chatcolor.replaceAll("<", "</")), Placeholder.component("message", MessageTools.parseText(messageString)));

        Bukkit.broadcast(replacedText);


        Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
            try {
                Objects.requireNonNull(guild.getTextChannelById(1077335479638298645L))
                        .sendMessage("**[" + e.getPlayer().getName().replaceAll("_", "\\\\_").replaceAll("\\*", "\\\\*").replaceAll("~", "\\\\~")
                                + "]:** " + discordMessageString).queue();
            } catch (NullPointerException exception){
                Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
            }
        });
    }
}
