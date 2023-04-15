package me.fishbythefin.gladiators.band;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerRubberBandProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerRubberBand> PLAYER_RUBBER_BAND = CapabilityManager.get(new CapabilityToken<PlayerRubberBand>() {});
    private PlayerRubberBand rubberBand = null;
    private final LazyOptional<PlayerRubberBand> optional = LazyOptional.of(this::createPlayerRubberBand);

    private @NotNull PlayerRubberBand createPlayerRubberBand() {
        if (this.rubberBand == null) {
            this.rubberBand = new PlayerRubberBand();
        }
        return this.rubberBand;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_RUBBER_BAND) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerRubberBand().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerRubberBand().loadNBTData(nbt);
    }
}

