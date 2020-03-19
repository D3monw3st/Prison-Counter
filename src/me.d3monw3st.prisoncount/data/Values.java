package me.d3monw3st.prisoncount.data;

import me.d3monw3st.prisoncount.Console;
import me.d3monw3st.prisoncount.Main;
import me.d3monw3st.prisoncount.config.Config;
import me.d3monw3st.prisoncount.config.ConfigFiles;
import org.bukkit.Bukkit;

import javax.swing.tree.TreeNode;
import java.io.IOException;
import java.util.*;


public class Values {

    private static double minutesBetweenEvent;
    private static double minutesLastingEvent;
    private static boolean started;
    private static String winnerMessage;
    private static String startingMessage;
    private static Map<Double, String> commands;
    private static int id;

    public Values() {

        commands = new TreeMap<>();
        id = 0;

        Config.RConfig config = ConfigFiles.values;

        
        minutesBetweenEvent = config.getDouble("settings.minutes-between-event", minutesBetweenEvent);
        if (minutesBetweenEvent == 0) {
            minutesBetweenEvent = 5.0;
        }
        minutesLastingEvent = config.getDouble("settings.minutes-lasting-event", minutesLastingEvent);
        if (minutesBetweenEvent == 0) {
            minutesBetweenEvent = 5.0;
        }
        winnerMessage = config.getString("winner.message", winnerMessage);
        if (winnerMessage == null) {
            winnerMessage = "&b&l[&aPrefix&b&l] &6 %s has won.";
        }
        startingMessage = config.getString("settings.begin-message", startingMessage);
        if (startingMessage == null) {
            startingMessage = "&b&l[&aPrefix&b&l] &7Event has begun";
        }

        try {
            for (String s : config.getKeys(true)) {

                if (s.startsWith("winner.reward." + id) && config.getString("winner.rewards." + id + ".command") != null) {

                    commands.put(config.getDouble("winner.rewards." + id  + ".chance"), s);
                    id++;
                } else {
                    commands.put(15.2, "give %s diamond 12");
                    commands.put(12.2, "give %s minecart 3");
                }
                Console.log(s);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Console.log("Minutes between events set at " + minutesBetweenEvent);
        Console.log("Minutes lasting for an event set at " + minutesLastingEvent);
        Console.log("Winner Message" + winnerMessage);
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isStarted() {
        return started;
    }

    public double getMinutesBetweenEvent() {
        return minutesBetweenEvent;
    }

    public double getMinutesLastingEvent() {
        return minutesLastingEvent;
    }

    public static String getStartingMessage() {
        return startingMessage;
    }

    public String getWinnerMessage() {
        return winnerMessage;
    }

    public TreeMap<Double, String> getCommands() {
        return (TreeMap<Double, String>) commands;
    }

    public static void saveValues() {
        try {
            Config.RConfig config = ConfigFiles.values;
            config.set("settings.minutes-between-event", minutesBetweenEvent);
            config.set("settings.minutes-lasting-event", minutesLastingEvent);
            config.set("settings.begin-message", startingMessage);
            config.set("winner.message", winnerMessage);

            id = 0;
            for(Map.Entry<Double, String> cmdsToStore : commands.entrySet()) {
                config.set("winner.rewards." + id  + ".command", cmdsToStore.getValue());
                config.set("winner.rewards." + id  + ".chance", cmdsToStore.getKey());
                id++;
            }

            config.save();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
