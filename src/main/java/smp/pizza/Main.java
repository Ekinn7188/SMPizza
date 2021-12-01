package smp.pizza;

import jeeper.utils.config.ConfigSetup;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main extends JavaPlugin {

    private static Main plugin;
    private static ConfigSetup playerData;
    private static ConfigSetup config;
    private static JDA jda;

    @Override
    public void onEnable() {
        plugin = this;

        startFileSetup();

        Objects.requireNonNull(this.getCommand("chatcolor")).setExecutor(new NameColor());
        Objects.requireNonNull(this.getCommand("smpizza")).setExecutor(new Reload());
        Objects.requireNonNull(this.getCommand("smpizza")).setTabCompleter(new Reload());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new Chat(), Main.getPlugin());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new CancelCrystal(), Main.getPlugin());

        //discord bot
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/token.txt"));
            String token = scanner.nextLine();

            jda = JDABuilder.createDefault(token)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build();
        } catch (LoginException | FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Main getPlugin() {
        return plugin;
    }

    public ConfigSetup getPlayerData() {
        return playerData;
    }
    public ConfigSetup config() { return config; }
    public JDA getJda() { return jda; }

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
