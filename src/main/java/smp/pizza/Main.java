package smp.pizza;

import jeeper.utils.config.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main extends JavaPlugin {

    private static Main plugin;
    private static Config playerData;
    private static Config config;
    public static JDA jda;

    @Override
    public void onEnable() {
        plugin = this;

        startFileSetup();

        Objects.requireNonNull(this.getCommand("chatcolor")).setExecutor(new NameColor());
        Objects.requireNonNull(this.getCommand("smpizza")).setExecutor(new Reload());
        Objects.requireNonNull(this.getCommand("smpizza")).setTabCompleter(new Reload());
        Objects.requireNonNull(this.getCommand("cd")).setExecutor(new CdList());
        Objects.requireNonNull(this.getCommand("cd")).setTabCompleter(new CdList());
        Objects.requireNonNull(this.getCommand("mod")).setExecutor(new Mod());
        Objects.requireNonNull(this.getCommand("mod")).setTabCompleter(new DefaultTabCompleter());
        Objects.requireNonNull(this.getCommand("admin")).setExecutor(new Admin());
        Objects.requireNonNull(this.getCommand("admin")).setTabCompleter(new DefaultTabCompleter());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new Chat(), Main.getPlugin());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new CancelCrystal(), Main.getPlugin());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new JoinLeave(), Main.getPlugin());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new DeathMessage(), Main.getPlugin());
        Main.getPlugin().getServer().getPluginManager().registerEvents(new AchievementMessage(), Main.getPlugin());

        //discord bot
        jda = createJDA();

        if (jda == null) {
            this.getServer().getPluginManager().disablePlugin(this);
        }

        jda.addEventListener(new DiscordChat());

        for (Command cmd : jda.retrieveCommands().complete()) {
            cmd.delete().complete();
        }

        jda.upsertCommand("onlineplayers", "Check who is currently on the server.")
                .queue();

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () ->  {
            try {
                Objects.requireNonNull(jda).getSelfUser().getMutualGuilds().forEach(guild -> {
                    Objects.requireNonNull(guild.getTextChannelById(1077335479638298645L)).sendMessage("**[<:diamond:778407418916372480>] Server Online**").queue();
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
                            .getTextChannelById(1077335479638298645L))
                            .sendMessage("**[:octagonal_sign:] Server Offline**").queue();
        } catch (NullPointerException e) {
            Bukkit.getLogger().info("Could not send message to guild");
        }
        jda.shutdownNow();
    }

    public static Main getPlugin() {
        return plugin;
    }

    public Config getPlayerData() {
        return playerData;
    }
    public Config config() { return config; }

    private void startFileSetup() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        List<String> header = List.of("""
                ############################################################
                # +------------------------------------------------------+ #
                # |                       SMPizza                        | #
                # +------------------------------------------------------+ #
                ############################################################
                                
                Developed by: Jeeper_
                """.split("\\n"));

        config = new Config("config", "SMPizza");
        config.get().options().setHeader(header);
        config.readDefaults(this, "config.yml");
        config.get().options().parseComments(true);
        config.get().options().copyDefaults(false);
        config.save();
        
        
        playerData = new Config("playerdata", "SMPizza");
        playerData.get().options().setHeader(header);
        playerData.get().options().parseComments(true);
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
                    .enableIntents(EnumSet.allOf(GatewayIntent.class))
                    .build();

        } catch (NullPointerException e) {
            e.printStackTrace();
            this.getServer().getPluginManager().disablePlugin(this);
            return null;
        }
    }

}
