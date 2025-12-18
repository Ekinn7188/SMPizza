package smp.pizza;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class DeathMessage implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Component message = e.deathMessage();
        if (message != null) {
            String messageString = PlainTextComponentSerializer.plainText().serialize(message);
            Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
                try {
                    Objects.requireNonNull(guild.getTextChannelById(1077335479638298645L)).sendMessage("**[:skull:] " + messageString.replace("_", "\\_").replace("*", "\\*") + "**").queue();
                } catch (NullPointerException exception){
                    Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
                }
            });
        }
    }

}
