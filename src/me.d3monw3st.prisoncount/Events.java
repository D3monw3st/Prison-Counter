package me.d3monw3st.prisoncount;

import me.d3monw3st.prisoncount.data.PlayerData;
import me.d3monw3st.prisoncount.data.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Events implements Listener {

    @EventHandler
    public void onOreBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerData pdata = PlayerDataManager.getPlayerData(player.getUniqueId());

        if (pdata == null) {
            player.sendMessage("§4§l! §7Error player data not found, please relog.");
            return;
        }






    }


}
