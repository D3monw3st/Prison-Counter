package me.d3monw3st.prisoncount;

import me.d3monw3st.prisoncount.config.ConfigFiles;
import me.d3monw3st.prisoncount.data.Values;
import me.d3monw3st.prisoncount.runnables.MineRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;
    private static Values values;


    @Override
    public void onEnable() {
        registerValues();
        registerEvents();
        startTask();
        ConfigFiles.registerConfig();
        Console.log("Prison Counter enabled successfully.");

    }

    @Override
    public void onDisable() {

        Console.log("Prison Counter disabled successfully.");

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
    }


    public static Main getPlugin() {
        return plugin;
    }

    public static Values getValues() {
        return values;
    }
}
