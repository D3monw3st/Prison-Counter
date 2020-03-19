package me.d3monw3st.prisoncount.data;

import me.d3monw3st.prisoncount.Console;
import me.d3monw3st.prisoncount.Main;
import me.d3monw3st.prisoncount.config.Config;
import me.d3monw3st.prisoncount.config.ConfigFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Values {

    private static int minutesBetweenEvent;
    private static int minutesLastingEvent;
    private static boolean started;
    private static String winnerMessage;
    private static String startingMessage;
    private static List<String> commands;

    public Values() {

        commands = new ArrayList<>();

        //TODO: Add reading a string as a command which will execute for rewards

        Config.RConfig config = ConfigFiles.values;

        
        minutesBetweenEvent = config.getInt("values.minutes-between-event", minutesBetweenEvent);
        minutesLastingEvent = config.getInt("values.minutes-lasting-event", minutesLastingEvent);
        winnerMessage = config.getString("winner.message", winnerMessage);
        if (winnerMessage == null) {
            winnerMessage = "%s has won - use %s as a placeholder for the player name.";
        }
        startingMessage = config.getString("values.begin-message", startingMessage);
        if (startingMessage == null) {
            startingMessage = "Event has begun";
        }
        commands = (List<String>) config.getList("winner.rewards", commands);
        if (commands == null || commands.isEmpty()) {
            commands.add("/give %s 1 diamondsword");
        }

            //Main.getPlugin().getFileConfig().load();
            /*minutesBetweenEvent = Main.getPlugin().getFileConfig().getInt("values.minutes-between-event", minutesBetweenEvent);
            minutesLastingEvent = Main.getPlugin().getFileConfig().getInt("values.minutes-lasting-event", minutesLastingEvent);
            winnerMessage = Main.getPlugin().getFileConfig().getString("values.winner.winnermessage", winnerMessage);
            commands = (List<String>) Main.getPlugin().getFileConfig().getList("values.winner.rewards", commands);*/

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

    public int getMinutesBetweenEvent() {
        return minutesBetweenEvent;
    }

    public int getMinutesLastingEvent() {
        return minutesLastingEvent;
    }

    public static String getStartingMessage() {
        return startingMessage;
    }

    public String getWinnerMessage() {
        return winnerMessage;
    }

    public List<String> getCommands() {
        return commands;
    }

    public static void saveValues() {
        try {
            Config.RConfig config = ConfigFiles.values;
            config.set("values.minutes-between-event", minutesBetweenEvent);
            config.set("values.minutes-lasting-event", minutesLastingEvent);
            config.set("values.message", startingMessage);
            config.set("winner.message", winnerMessage);
            config.set("winner.rewards", commands);
            config.save();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
