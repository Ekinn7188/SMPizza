package smp.pizza;

import io.papermc.paper.advancement.AdvancementDisplay;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.craftbukkit.advancement.CraftAdvancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.Objects;

public class AchievementMessage implements Listener {

    @EventHandler
    public void onAchievement(PlayerAdvancementDoneEvent e) {
        CraftAdvancement craftAdvancement = (CraftAdvancement) e.getAdvancement();

        AdvancementDisplay rootDisplay = craftAdvancement.getRoot().getDisplay();
        AdvancementDisplay advancementDisplay = craftAdvancement.getDisplay();

        if (rootDisplay == null || advancementDisplay == null) {
            return;
        }
        // is root
        if (rootDisplay.equals(advancementDisplay)) {
            return;
        }


        AdvancementDisplay.Frame frame = advancementDisplay.frame();

        String advancementName = PlainTextComponentSerializer.plainText().serialize(e.getAdvancement().displayName());

        if (frame == AdvancementDisplay.Frame.CHALLENGE) {
            // **[:trophy:] <username> has completed the challenge [<advancement>]**
            Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
                try {
                    Objects.requireNonNull(guild.getTextChannelById(1077335479638298645L)).sendMessage("**[:trophy:] " + e.getPlayer().getName() + " has completed the challenge " + advancementName + "**").queue();
                } catch (NullPointerException exception){
                    Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
                }
            });
        }
        else if (frame == AdvancementDisplay.Frame.GOAL) {
            // **[:trophy:] <username> has reached the goal [<advancement>]**
            Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
                try {
                    Objects.requireNonNull(guild.getTextChannelById(1077335479638298645L)).sendMessage("**[:trophy:] " + e.getPlayer().getName() + " has reached the goal " + advancementName + "**").queue();
                } catch (NullPointerException exception){
                    Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
                }
            });
        }
        else { // FrameType.TASK OR FrameType.UNKNOWN
            //**[:trophy:] <username> has made the advancement [<advancement>]**
            Main.jda.getSelfUser().getMutualGuilds().forEach(guild -> {
                try {
                    Objects.requireNonNull(guild.getTextChannelById(1077335479638298645L)).sendMessage("**[:trophy:] " + e.getPlayer().getName() + " has made the advancement " + advancementName + "**").queue();
                } catch (NullPointerException exception){
                    Bukkit.getLogger().info("Could not send message to guild " + guild.getName());
                }
            });
        }
    }

}
