package me.d3monw3st.prisoncount.config;

import me.d3monw3st.prisoncount.Console;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private static List<RConfig> configs = new ArrayList<>();

    public static boolean registerConfig(String id, String fileName, JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                copy(plugin.getResource(fileName), file);
            } catch (Exception e) {

            }
        }

        RConfig c = new RConfig(id, file);
        for (RConfig x : configs) {
            if (x.equals(c))
                return false;
        }

        configs.add(c);
        return true;
    }


    public static boolean load(String id) {
        RConfig c = getConfig(id);
        if (c == null)
            return false;
        try {
            c.load();
        } catch (Exception e) {
            Console.warn("An error occurred while loading a config with id " + id);
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public static RConfig getConfig(String id) {
        for (RConfig c : configs) {
            if (c.getConfigId().equalsIgnoreCase(id))
                return c;
        }
        return null;
    }

    public static class RConfig extends YamlConfiguration {
        private String id;
        private File file;

        public String getConfigId() {
            return id;
        }


        private RConfig(String id, File file) {
            super();
            this.id = id;
            this.file = file;
        }

        public void load() throws InvalidConfigurationException, FileNotFoundException, IOException {
            load(file);
        }
        public void save() throws IOException {
            save(file);
        }

        public boolean equals(RConfig c) {
            return c.getConfigId().equalsIgnoreCase(this.id);
        }
    }

    private static void copy(InputStream in, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        out.close();
        in.close();
    }


}
