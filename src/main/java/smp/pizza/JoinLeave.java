package smp.pizza;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Objects;

public class JoinLeave implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
            try {
                Objects.requireNonNull(guild.getTextChannelById(1077335479638298645L))
                        .sendMessage("**[<:enter:778395035841986561>] " + e.getPlayer().getName()
                                .replaceAll("_", "\\\\_")
                                .replaceAll("\\*", "\\\\*")
                                .replaceAll("~", "\\\\~") + "**").queue();
            } catch (NullPointerException exception){
                Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
            }
        });

        // Player permissions
        PermissionAttachment attachment = e.getPlayer().addAttachment(Main.getPlugin());
        attachment.setPermission("customdiscs.create", true);
        attachment.setPermission("customdiscs.download", true);

        attachment.setPermission("imageframe.create", true);
        attachment.setPermission("imageframe.create.animated", false);
        attachment.setPermission("imageframe.overlay", true);
        attachment.setPermission("imageframe.clone", true);
        attachment.setPermission("imageframe.rename", true);
        attachment.setPermission("imageframe.info", true);
        attachment.setPermission("imageframe.list", true);
        attachment.setPermission("imageframe.list.others", true);
        attachment.setPermission("imageframe.delete", true);
        attachment.setPermission("imageframe.get", true);

        attachment.setPermission("bukkit.command.tps", true);
        attachment.setPermission("bukkit.command.mspt", true);


    }

    @EventHandler
    public void onJoin(PlayerQuitEvent e) {
        Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
            try {
                Objects.requireNonNull(guild.getTextChannelById(1077335479638298645L))
                        .sendMessage("**[<:quit:778395035385200663>] " + e.getPlayer().getName()
                                .replaceAll("_", "\\\\_")
                                .replaceAll("\\*", "\\\\*")
                                .replaceAll("~", "\\\\~") + "**").queue();
            } catch (NullPointerException exception){
                Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
            }
        });
    }


}
