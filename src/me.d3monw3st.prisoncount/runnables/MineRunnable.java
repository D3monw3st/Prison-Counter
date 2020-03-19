package me.d3monw3st.prisoncount.runnables;

import me.d3monw3st.prisoncount.Main;
import me.d3monw3st.prisoncount.data.PlayerData;
import me.d3monw3st.prisoncount.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.print.attribute.standard.PDLOverrideSupported;

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
        if (Main.getValues().isStarted() == false && (int) (System.currentTimeMillis() - timeLastEvent)/60000 == Main.getValues().getMinutesBetweenEvent() && eventActive == false) {

            Bukkit.broadcastMessage(Main.getValues().getStartingMessage());

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
        if (eventActive && (int) (System.currentTimeMillis() - timeLastEvent)/60000 == Main.getValues().getMinutesLastingEvent()) {

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
                Bukkit.broadcastMessage(String.format(Main.getValues().getWinnerMessage(), tempPlayerTop.getName()));
                for (int i = 0; i < Main.getValues().getCommands().size(); i++) {
                    String command = Main.getValues().getCommands().get(i);
                    Bukkit.dispatchCommand(console, command);
                }
            }


            eventActive = false;
        }


    }


    public void setTimeLastEvent(long timeLastEvent) {
        this.timeLastEvent = timeLastEvent;
    }



}
