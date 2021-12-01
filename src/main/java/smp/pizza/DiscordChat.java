package smp.pizza;

import jeeper.utils.MessageTools;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class DiscordChat extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getChannel().getIdLong() != 775525737628172328L) return;
        if (event.getAuthor().isBot()) return;
        String message = event.getMessage().getContentDisplay();
        Bukkit.broadcast(MessageTools.parseFromPath(Main.getPlugin().config(), "Discord Message",
                Template.template("player", event.getAuthor().getName()),
                Template.template("message", message)));
    }

}
