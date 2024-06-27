package smp.pizza;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Objects;

public class CancelCrystal implements Listener {

    @EventHandler
    public void onCrystalUse(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType().equals(EntityType.END_CRYSTAL) && e.getEntityType().equals(EntityType.ITEM)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAnchorUse(EntityDamageByBlockEvent e) {
        try {
            if (Objects.requireNonNull(e.getDamager()).getType().equals(Material.RESPAWN_ANCHOR) && e.getEntityType().equals(EntityType.ITEM)) {
                e.setCancelled(true);
            }
        } catch (NullPointerException exception) {
            // Do nothing
        }
    }

}
