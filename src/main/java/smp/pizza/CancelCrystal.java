package smp.pizza;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CancelCrystal implements Listener {

    @EventHandler
    public void onCrystalUse(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType().equals(EntityType.ENDER_CRYSTAL) && e.getEntityType().equals(EntityType.DROPPED_ITEM)) {
            e.setCancelled(true);
        }
    }

}
