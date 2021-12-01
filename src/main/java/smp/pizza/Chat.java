package smp.pizza;

import io.papermc.paper.event.player.AsyncChatEvent;
import jeeper.utils.MessageTools;
import jeeper.utils.config.ConfigSetup;
import net.dv8tion.jda.api.JDA;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Template;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class Chat implements Listener {

    ConfigSetup playerdata = Main.getPlugin().getPlayerData();
    ConfigSetup config = Main.getPlugin().config();

    @EventHandler
    public void onChat(AsyncChatEvent e){
        e.setCancelled(true);

        String chatcolor = playerdata.get().getString(e.getPlayer().getUniqueId() + ".chatcolor");
        if (chatcolor == null){
            chatcolor = "<#42EB8a>";
        }

        Player player = e.getPlayer();

        String messageString = PlainTextComponentSerializer.plainText().serialize(e.message());

        Component replacedText = MessageTools.parseFromPath(config, "Chat",
                Template.template("player", MessageTools.parseText(chatcolor + player.getName())), Template.template("message", messageString));

        Bukkit.broadcast(replacedText);

        Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
            try {
                Objects.requireNonNull(guild.getTextChannelById(775525737628172328L)).sendMessage("[" + e.getPlayer().getName() + "]: " + messageString).queue();
            } catch (NullPointerException exception){
                Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
            }
        });

    }

}
