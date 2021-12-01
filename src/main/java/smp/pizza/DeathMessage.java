package smp.pizza;

import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.serializer.ComponentSerializer;
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
            String messageString = PaperComponents.plainSerializer().serialize(message);
            Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
                try {
                    Objects.requireNonNull(guild.getTextChannelById(775525737628172328L)).sendMessage("**" + messageString + "**").queue();
                } catch (NullPointerException exception){
                    Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
                }
            });
        }
    }

}
