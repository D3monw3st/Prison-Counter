package me.d3monw3st.prisoncount;

import me.d3monw3st.prisoncount.runnables.MineRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        registerEvents();
        startTask();
        Console.log("Prison Counter enabled successfully.");

    }

    @Override
    public void onDisable() {

        Console.log("Prison Counter disabled successfully.");

    }


    public void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Events(), this);

    }

    public void startTask() {
        new MineRunnable().runTaskTimer(this, 0L, 20L);
    }

}
