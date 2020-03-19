package me.d3monw3st.prisoncount.data;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerData {

    private UUID uuid;
    private int blocksBreaked;
    private int blockPoints;


    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        blockPoints = 0;
        blocksBreaked = 0;
    }




    /** Getters and Setters **/

    public void wipeCounts() {
        blockPoints = 0;
        blocksBreaked = 0;
    }

    public void setPoints(int amt) {
        blockPoints = amt;
    }

    public int getPoints() {
        return blockPoints;
    }

    public void setBlocksBreaked(int amt) {
        blocksBreaked = amt;
    }

    public int getBlocksBreaked() {
        return blocksBreaked;
    }
}
