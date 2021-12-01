package smp.pizza;

import jeeper.utils.config.ConfigSetup;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class Main extends JavaPlugin {

    private static Main plugin;
    private static ConfigSetup playerData;
    private static ConfigSetup config;
    public static JDA jda;

    @Override
    public void onEnable() {
        plugin = this;

        startFileSetup();

        Objects.requireNonNull(this.getCommand("chatcolor")).setExecutor(new NameColor());
        Objects.requireNonNull(this.getCommand("smpizza")).setExecutor(new Reload());
        Objects.requireNonNull(this.getCommand("smpizza")).setTabCompleter(new Reload());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new Chat(), Main.getPlugin());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new CancelCrystal(), Main.getPlugin());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new JoinLeave(), Main.getPlugin());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new DeathMessage(), Main.getPlugin());

        //discord bot
        jda = createJDA();

        if (jda == null) {
            this.getPluginLoader().disablePlugin(this);
        }

        jda.addEventListener(new DiscordChat());

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () ->  {
            try {
                Objects.requireNonNull(jda).getSelfUser().getMutualGuilds().forEach(guild -> {
                    Objects.requireNonNull(guild.getTextChannelById(775525737628172328L)).sendMessage("<:diamond:778407418916372480> **Server Online**").queue();
                });
            } catch (NullPointerException e) {
                Bukkit.getLogger().info("Could not send message to guild");
            }
        }, 50L);


    }

    @Override
    public void onDisable() {
        try {
            Objects.requireNonNull(Objects.requireNonNull(jda.getGuildById(775521131708022784L))
                            .getTextChannelById(775525737628172328L))
                            .sendMessage(":warning: **Server Offline**").queue();
        } catch (NullPointerException e) {
            Bukkit.getLogger().info("Could not send message to guild");
        }
        jda.shutdownNow();
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

    private JDA createJDA() {
        try {
            InputStream ioStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("token.txt");

            Scanner scanner = new Scanner(Objects.requireNonNull(ioStream));
            String token = scanner.nextLine();

            return JDABuilder.createDefault(token)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .build();

        } catch (LoginException | NullPointerException e) {
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
            return null;
        }
    }

}
