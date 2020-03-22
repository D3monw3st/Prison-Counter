package me.d3monw3st.prisoncount.runnables;

import me.d3monw3st.prisoncount.Main;
import me.d3monw3st.prisoncount.data.PlayerData;
import me.d3monw3st.prisoncount.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class MineRunnable extends BukkitRunnable {

    public static boolean eventActive;
    private long timeLastEvent;



    @Override
    public void run() {

        // Set the original factor of the event to 0 upon boot to prevent random events from starting
        if (Main.getValues().isStarted()) {
            Main.getValues().setStarted(false);
            setTimeLastEvent(System.currentTimeMillis());
        }


        // Start event run once every time
        if (Main.getValues().isStarted() == false && (double) (System.currentTimeMillis() - timeLastEvent)/60000 >= Main.getValues().getMinutesBetweenEvent() && eventActive == false) {

            String startingMessage = Main.getValues().getStartingMessage();
            startingMessage = startingMessage.replaceAll("&", "§");

            Bukkit.broadcastMessage(startingMessage);

            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerData pdata = PlayerDataManager.getPlayerData(p.getUniqueId());

                if (pdata != null) {
                    // Wipe counts as the event will now begin, don't want any interference
                    pdata.wipeCounts();
                }

            }

            eventActive = true;
            timeLastEvent = System.currentTimeMillis() - 100;
        }

        // Stop the event now that it's complete
        if (eventActive && (double) (System.currentTimeMillis() - timeLastEvent)/60000 >= Main.getValues().getMinutesLastingEvent()) {

            Player tempPlayerTop = null;
            int blocksBreaked = 0;

            for (Player p: Bukkit.getOnlinePlayers()) {
                PlayerData pdata = PlayerDataManager.getPlayerData(p.getUniqueId());

                if (pdata != null) {
                    int tempBlocks = pdata.getBlocksBreaked();
                    if (tempBlocks > blocksBreaked) {
                        blocksBreaked = tempBlocks;
                        tempPlayerTop = p;
                    }
                }
            }

            if (tempPlayerTop != null) {
                //Announce the winner and give items.
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

                // Announce the winners
                String broadcast = Main.getValues().getWinnerMessage().replaceAll("%s", tempPlayerTop.getName());
                broadcast = broadcast.replaceAll("&", "§");
                Bukkit.broadcastMessage(broadcast);


                // Calculate reward
                double randomChance = Math.random() * 100;
                Map.Entry<Double,String> low = Main.getValues().getCommands().floorEntry(randomChance);
                Map.Entry<Double, String> high = Main.getValues().getCommands().ceilingEntry(randomChance);
                String cmd = null;

                if (low != null && high != null) {
                    int tempLow = (int) Math.abs(randomChance - low.getKey());
                    int tempHigh = (int) Math.abs(randomChance - high.getKey());

                    if (tempLow < tempHigh) {
                        cmd = low.getValue();
                    } else {
                        cmd = high.getValue();
                    }
                    //cmd = Math.abs(randomChance-low.getKey()) < Math.abs(randomChance-high.getKey()) ?   low.getValue() :   high.getValue();
                } else if (low != null || high != null) {
                    cmd = low != null ? low.getValue() : high.getValue();
                }

                cmd = cmd.replaceAll("%s", tempPlayerTop.getName());

                if (cmd != null) {
                    Bukkit.dispatchCommand(console, cmd);
                }

            }


            eventActive = false;
        }


    }


    public void setTimeLastEvent(long timeLastEvent) {
        this.timeLastEvent = timeLastEvent;
    }



}
