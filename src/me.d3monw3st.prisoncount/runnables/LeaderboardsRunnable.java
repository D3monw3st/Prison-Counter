package me.d3monw3st.prisoncount.runnables;

import com.mysql.fabric.xmlrpc.base.Value;
import me.d3monw3st.prisoncount.data.PlayerData;
import me.d3monw3st.prisoncount.data.PlayerDataManager;
import me.d3monw3st.prisoncount.data.Values;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LeaderboardsRunnable extends BukkitRunnable {

    private KeyedBossBar leaderboard;
    private List<Player> playersList;
    private int count;
    private boolean switchBoolean;

    public LeaderboardsRunnable() {
        leaderboard = Bukkit.createBossBar(NamespacedKey.minecraft("lb"),"§bLeaderbaord", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
        playersList = new ArrayList<>();
        count = 0;
    }


    @Override
    public void run() {

        if (MineRunnable.eventActive) {
            switchBoolean = true;
            Player leader = null;
            int highest = 0;

            for (Player p: Bukkit.getOnlinePlayers()) {
                if (playersList.isEmpty() || !playersList.contains(p)) {
                    leaderboard.addPlayer(p);
                    playersList.add(p);
                }

                PlayerData pdata = PlayerDataManager.getPlayerData(p.getUniqueId());
                if (pdata != null) {
                    int tempBlocks = pdata.getBlocksBreaked();
                    if (tempBlocks > highest) {
                        highest = tempBlocks;
                        leader = p;
                    }


                    if (leader != null && count <= 10) {
                        String leaderboardStr = Values.getLeaderBarMessage();
                        leaderboardStr = leaderboardStr.replaceAll("%s", leader.getName());
                        leaderboardStr = leaderboardStr.replaceAll("%c", highest + "");
                        leaderboardStr = leaderboardStr.replaceAll("&", "§");
                        leaderboard.setTitle(leaderboardStr);
                    } else {

                        String playerBarStr = Values.getPlayerBarMessage();
                        playerBarStr = playerBarStr.replaceAll("%c", pdata.getBlocksBreaked() + "");
                        playerBarStr = playerBarStr.replaceAll("&", "§");
                        leaderboard.setTitle(playerBarStr);
                    }
                }
            }

        } else  {

            if (switchBoolean) {

            for (Player p: Bukkit.getOnlinePlayers()) {
                if (playersList.contains(p)) {
                    clearBossBars(p);
                }
            }
                playersList.clear();
            switchBoolean = false;
            }

        }


        if (count == 20) {
            count = 0;
        }

        count++;
    }


    public void clearBossBars(Player player) {
        for (@Nonnull Iterator<KeyedBossBar> it = Bukkit.getBossBars(); it.hasNext(); ) {
            final BossBar bossBar = it.next();
            bossBar.removePlayer(player);
        }

    }

}
