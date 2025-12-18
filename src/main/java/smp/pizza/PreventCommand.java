package smp.pizza;

import jeeper.utils.MessageTools;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PreventCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {

        // "as" for "armor stands" datapack
        if (e.getMessage().contains("trigger") && !e.getMessage().contains("as") && !e.getPlayer().isOp()) {
            e.getPlayer().sendMessage(MessageTools.parseText("<red>You do not have permission to do this."));
            e.setCancelled(true);
        }
    }

}
