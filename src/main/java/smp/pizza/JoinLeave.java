package smp.pizza;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class JoinLeave implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
            try {
                Objects.requireNonNull(guild.getTextChannelById(775525737628172328L))
                        .sendMessage("**<:enter:778395035841986561> " + e.getPlayer().getName() + " Joined**").queue();
            } catch (NullPointerException exception){
                Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent e) {
        Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
            try {
                Objects.requireNonNull(guild.getTextChannelById(775525737628172328L))
                        .sendMessage("**<:quit:778395035385200663> " + e.getPlayer().getName() + " Left**").queue();
            } catch (NullPointerException exception){
                Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
            }
        });
    }


}
