package me.fishbythefin.gladiators.lamb;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class PlayerSacrificialLamb {
    private UUID lambUUID;


    public UUID getLambUUID() {
        return lambUUID;
    }
    public void setLambUUID(UUID uuid) {
        lambUUID = uuid;
    }

    public void copyFrom(PlayerSacrificialLamb source) {
        this.lambUUID = source.lambUUID;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putUUID("lambUUID", this.lambUUID);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.lambUUID = nbt.getUUID("lambUUID");
    }
}
