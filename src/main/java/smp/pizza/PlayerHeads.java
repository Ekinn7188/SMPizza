package smp.pizza;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class PlayerHeads implements Listener {

    @EventHandler
    public void onPlayerHeadPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();

        if (block.getState() instanceof Skull skull) {
            String base64ItemStack = toBase64(e.getItemInHand());

            skull.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "CustomSkullData"),
                    PersistentDataType.STRING,
                    base64ItemStack);
        }
    }

    @EventHandler
    public void onPlayerHeadBreak(BlockBreakEvent e) {
        Block block = e.getBlock();

        if (block.getState() instanceof Skull skull) {
            String data = skull.getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(), "CustomSkullData"),
                    PersistentDataType.STRING);

            if (data != null) {
                ItemStack item = fromBase64(data);

                block.getWorld().dropItem(block.getLocation(), item);
            }


        }
    }

    private String toBase64(ItemStack itemStack) {
        byte[] serialized = ItemStack.serializeItemsAsBytes(List.of(itemStack));
        return new String(Base64Coder.encode(serialized));
    }

    private ItemStack fromBase64(String base64) {
        byte[] serialized = Base64Coder.decode(base64);
        return ItemStack.deserializeItemsFromBytes(serialized)[0];
    }
}
