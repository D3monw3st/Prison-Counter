package me.d3monw3st.prisoncount;

import me.d3monw3st.prisoncount.config.ConfigFiles;
import me.d3monw3st.prisoncount.data.PlayerData;
import me.d3monw3st.prisoncount.data.PlayerDataManager;
import me.d3monw3st.prisoncount.data.Values;
import me.d3monw3st.prisoncount.runnables.LeaderboardsRunnable;
import me.d3monw3st.prisoncount.runnables.MineRunnable;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    private static Main plugin;
    private static Values values;


    @Override
    public void onEnable() {
        plugin = this;


        //createConfig();
        ConfigFiles.registerConfig();
        registerValues();
        registerEvents();
        startTask();
        registerOnlinePlayer();
        Console.log("Prison Counter enabled successfully.");
    }

    @Override
    public void onDisable() {

        Console.log("Prison Counter disabled successfully.");
        Values.saveValues();
        clearBossbars();
    }


    public void registerValues() {
        this.values = new Values();
        values.setStarted(true);
    }

    public void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Events(), this);

    }

    public void startTask() {
        new MineRunnable().runTaskTimer(this, 0L, 20L);
        new LeaderboardsRunnable().runTaskTimer(this, 0L, 10L);
    }


    public static Main getPlugin() {
        return plugin;
    }

    public static Values getValues() {
        return values;
    }

    public void registerOnlinePlayer() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData pdata = PlayerDataManager.getPlayerData(p.getUniqueId());
            if (!PlayerDataManager.hasPlayerData(p.getUniqueId()) || pdata == null) {
                PlayerDataManager.createPlayerData(p.getUniqueId());
            }


        }
    }

    public void clearBossbars() {
        Bukkit.getBossBars().forEachRemaining(BossBar::removeAll);
    }

}
