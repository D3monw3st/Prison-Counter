package me.d3monw3st.prisoncount.data;

import me.d3monw3st.prisoncount.Console;
import me.d3monw3st.prisoncount.Main;
import me.d3monw3st.prisoncount.config.Config;
import me.d3monw3st.prisoncount.config.ConfigFiles;

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

    public Values() {

        commands = new TreeMap<>();

        //TODO: Add reading a string as a command which will execute for rewards

        Config.RConfig config = ConfigFiles.values;

        
        minutesBetweenEvent = config.getDouble("values.minutes-between-event", minutesBetweenEvent);
        if (minutesBetweenEvent == 0) {
            minutesBetweenEvent = 5.0;
        }
        minutesLastingEvent = config.getDouble("values.minutes-lasting-event", minutesLastingEvent);
        if (minutesBetweenEvent == 0) {
            minutesBetweenEvent = 5.0;
        }
        winnerMessage = config.getString("winner.message", winnerMessage);
        if (winnerMessage == null) {
            winnerMessage = "use %s as a placeholder for the player name.";
        }
        startingMessage = config.getString("values.begin-message", startingMessage);
        if (startingMessage == null) {
            startingMessage = "Event has begun";
        }
        //commands = (TreeMap<Double, String>) config.get ("winner.rewards.");


        try {
            for (String s : config.getConfigurationSection("winner.commands").getKeys(true)) {
                String cmd = config.getConfigurationSection("winner.commands").getString(s);
                commands.put(config.getDouble(s), cmd);
            }
        } catch (NullPointerException e) {
            if (commands == null || commands.isEmpty()) {
                commands.put(15.2,"/give %s 1 12");
            }
        }

        //config.getConfigurationSection("cmds.").getKeys(false).forEach(commands.put(chance -> config.getDouble("cmds."), config.getString("cmds." + chance));

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
            config.set("values.minutes-between-event", minutesBetweenEvent);
            config.set("values.minutes-lasting-event", minutesLastingEvent);
            config.set("values.message", startingMessage);
            config.set("winner.message", winnerMessage);

            for(Map.Entry<Double, String> cmdsToStore : commands.entrySet()) {
                config.getConfigurationSection("winner.commands").set(cmdsToStore.getKey().toString(), cmdsToStore.getValue());
            }


            //config.set("winner.rewards", commands);
            config.save();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
