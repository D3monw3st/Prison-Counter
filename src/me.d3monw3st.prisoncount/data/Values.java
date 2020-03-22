package me.d3monw3st.prisoncount.data;


import me.d3monw3st.prisoncount.config.Config;
import me.d3monw3st.prisoncount.config.ConfigFiles;
import java.io.IOException;
import java.util.*;


public class Values {

    private static double minutesBetweenEvent;
    private static double minutesLastingEvent;
    private static boolean started;
    private static String winnerMessage;
    private static String startingMessage;
    private static String leaderBarMessage;
    private static String playerBarMessage;
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
        if (minutesLastingEvent == 0) {
            minutesLastingEvent = 2.0;
        }

        leaderBarMessage = config.getString("settings.bar.leader", leaderBarMessage);
        if (leaderBarMessage == null) {
            leaderBarMessage = "&3Leader |&b %s &3|&b %c ";
        }

        playerBarMessage = config.getString("settings.bar.player", playerBarMessage);
        if (playerBarMessage == null) {
            playerBarMessage = "&3Blocks Broken:&b %c";
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
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        /*Console.log("Minutes between events set at " + minutesBetweenEvent);
        Console.log("Minutes lasting for an event set at " + minutesLastingEvent);
        Console.log("Winner Message" + winnerMessage);*/
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

    public static String getLeaderBarMessage() {
        return leaderBarMessage;
    }

    public static String getPlayerBarMessage() {
        return playerBarMessage;
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
            config.set("settings.bar.leader", leaderBarMessage);
            config.set("settings.bar.player", playerBarMessage);

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
