package me.fishbythefin.gladiators.band;

import net.minecraft.nbt.CompoundTag;

public class PlayerRubberBand {
    private int rubberBandBounces = 0;


    public int getRubberBandBounces() {
        return rubberBandBounces;
    }
    public void setRubberBandBounces(int i) {
        rubberBandBounces = i;
    }

    public void copyFrom(PlayerRubberBand source) {
        this.rubberBandBounces = source.rubberBandBounces;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("rubberBandBounces", this.rubberBandBounces);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.rubberBandBounces = nbt.getInt("rubberBandBounces");
    }
}
