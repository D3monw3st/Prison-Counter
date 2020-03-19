package me.d3monw3st.prisoncount.config;

import me.d3monw3st.prisoncount.Main;

public class ConfigFiles {

    public static Config.RConfig values;

    public static void registerConfig() {

        Config.registerConfig("config", "config.yml", Main.getPlugin());
        Config.load("config");

    }

}
