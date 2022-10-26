package me.fishbythefin.gladiators.lamb;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class PlayerSacrificialLamb {
    private UUID lambUUID = UUID.randomUUID();


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
        if (this.lambUUID != null) {
            tag.putUUID("lambUUID", this.lambUUID);
        } else {
            tag.putUUID("lambUUID", UUID.randomUUID());
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        try {
            this.lambUUID = nbt.getUUID("lambUUID");
        }
        catch (Exception e) {
            this.lambUUID = UUID.randomUUID();
        }
    }
}
