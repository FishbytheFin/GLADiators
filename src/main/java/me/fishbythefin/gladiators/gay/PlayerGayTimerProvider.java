package me.fishbythefin.gladiators.gay;

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

public class PlayerGayTimerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerGayTimer> PLAYER_GAY_TIMER = CapabilityManager.get(new CapabilityToken<PlayerGayTimer>() {});
    private PlayerGayTimer gayTimer = null;
    private final LazyOptional<PlayerGayTimer> optional = LazyOptional.of(this::createPlayerGayTimer);

    private PlayerGayTimer createPlayerGayTimer() {
        if (this.gayTimer == null) {
            this.gayTimer = new PlayerGayTimer();
        }
        return this.gayTimer;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_GAY_TIMER) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerGayTimer().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerGayTimer().loadNBTData(nbt);
    }
}
