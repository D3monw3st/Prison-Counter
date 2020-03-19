package me.d3monw3st.prisoncount.data;

import me.d3monw3st.prisoncount.Console;
import me.d3monw3st.prisoncount.config.Config;
import me.d3monw3st.prisoncount.config.ConfigFiles;

import java.util.ArrayList;
import java.util.List;

public class Values {

    private int minutesBetweenEvent;
    private int minutesLastingEvent;
    private boolean started;
    private String winnerMessage;
    private List<String> commands;

    public Values() {

        commands = new ArrayList<>();

        //TODO: Add reading a string as a command which will execute for rewards

        minutesBetweenEvent = ConfigFiles.values.getInt("values.minutes-between-event", minutesBetweenEvent);
        minutesLastingEvent = ConfigFiles.values.getInt("values.minutes-lasting-event", minutesLastingEvent);
        winnerMessage = ConfigFiles.values.getString("values.winner.winnermessage", winnerMessage);
        commands = (List<String>) ConfigFiles.values.getList("values.winner.rewards", commands);

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

    public String getWinnerMessage() {
        return winnerMessage;
    }

    public List<String> getCommands() {
        return commands;
    }
}
