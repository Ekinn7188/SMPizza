package smp.pizza;

import jeeper.utils.MessageTools;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Collection;
import java.util.Objects;

public class DiscordChat extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getChannel().getIdLong() != 1077335479638298645L) return;
        if (event.getAuthor().isBot()) return;
        String message = event.getMessage().getContentRaw();
        String name;
        try {
            Member member = Objects.requireNonNull(event.getMember());
            name = member.getNickname();
            if (name == null) {
                name = member.getUser().getName();
            }
        } catch (NullPointerException e) {
            name = "Unknown User";
        }
        Bukkit.broadcast(MessageTools.parseFromPath(Main.getPlugin().config(), "Discord Message",
                Placeholder.parsed("player", name),
                Placeholder.parsed("message", message)));
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("onlineplayers")) {
            Collection<? extends Player> players = Main.getPlugin().getServer().getOnlinePlayers();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Online Players (" + players.size() + ")");
            eb.setColor(new Color(93,124,22, 255));
            StringBuilder sb = new StringBuilder();
            for (Player player : players) {
                boolean isAFK = player.getScoreboard().getTeam("afkDis.afk") != null;

                if (isAFK) {
                    sb.append("*");
                }
                sb.append(player.getName()).append(" [").append(player.getPing()).append("]");
                if (isAFK) {
                    sb.append("*");
                }
                sb.append("\n");

            }
            eb.setDescription(sb.toString());

            event.replyEmbeds(eb.build()).queue();
        }
    }

}
