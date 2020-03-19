package me.d3monw3st.prisoncount.data;

import me.d3monw3st.prisoncount.Console;

import java.util.TreeMap;
import java.util.UUID;

public class PlayerDataManager {

    private static TreeMap<UUID, PlayerData> playerData = new TreeMap<>();
    public static boolean hasPlayerData(UUID uuid) {
        return playerData.containsKey(uuid);
    }

    public static PlayerData createPlayerData(UUID uuid) {
        if (playerData.containsKey(uuid)) {
            Console.warn(String.format("Tried to create player data for uuid that already exists: %s", uuid.toString()));
            return playerData.get(uuid);
        }
        Console.log(String.format("Created player data for uuid %s", uuid.toString()));
        return playerData.put(uuid, new PlayerData(uuid));
    }

    public static PlayerData getPlayerData(UUID uuid) {
        return playerData.get(uuid);
    }
}
