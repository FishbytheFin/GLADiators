package me.fishbythefin.gladiators.gay;

import net.minecraft.nbt.CompoundTag;

public class PlayerGayTimer {
    private long gayTimer;


    public long getGayTimer() {
        return gayTimer;
    }


    public void addGayTime(long time) {
        this.gayTimer = Math.min(200L, this.gayTimer + time);
    }

    public void subtractGayTime(long time) {
        this.gayTimer = Math.max(0, this.gayTimer - time);
    }

    public void copyFrom(PlayerGayTimer source) {
        this.gayTimer = source.gayTimer;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putLong("gayTimer", this.gayTimer);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.gayTimer = nbt.getLong("gayTimer");
    }

}
