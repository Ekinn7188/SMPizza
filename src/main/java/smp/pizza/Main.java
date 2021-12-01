package smp.pizza;

import jeeper.utils.config.ConfigSetup;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    private static Main plugin;
    private static ConfigSetup playerData;
    private static ConfigSetup config;

    @Override
    public void onEnable() {
        plugin = this;

        startFileSetup();

        Objects.requireNonNull(this.getCommand("chatcolor")).setExecutor(new NameColor());
        Objects.requireNonNull(this.getCommand("smpizza")).setExecutor(new Reload());
        Objects.requireNonNull(this.getCommand("smpizza")).setTabCompleter(new Reload());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new Chat(), Main.getPlugin());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new CancelCrystal(), Main.getPlugin());
    }

    public static Main getPlugin() {
        return plugin;
    }

    public ConfigSetup getPlayerData() {
        return playerData;
    }
    public ConfigSetup config() { return config; }

    private void startFileSetup() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        String header = """
                ############################################################
                # +------------------------------------------------------+ #
                # |                       SMPizza                        | #
                # +------------------------------------------------------+ #
                ############################################################
                                
                Developed by: Jeeper_ (Jeeper#6808)
                """;

        config = new ConfigSetup("config", "SMPizza");
        config.get().options().header(header);
        config.readDefaults(this, "config.yml");
        config.get().options().copyHeader(true);
        config.get().options().copyDefaults(false);
        config.save();
        
        
        playerData = new ConfigSetup("playerdata", "SMPizza");
        playerData.get().options().header(header);
        playerData.get().options().copyHeader(true);
        playerData.get().options().copyDefaults(false);
        playerData.save();

    }

}
