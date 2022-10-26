package me.fishbythefin.gladiators.lamb;

import me.fishbythefin.gladiators.gay.PlayerGayTimer;
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

public class PlayerSacrificialLambProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerSacrificialLamb> PLAYER_SACRIFICIAL_LAMB = CapabilityManager.get(new CapabilityToken<PlayerSacrificialLamb>() {});
    private PlayerSacrificialLamb lamb = null;
    private final LazyOptional<PlayerSacrificialLamb> optional = LazyOptional.of(this::createPlayerSacrificialLamb);

    private @NotNull PlayerSacrificialLamb createPlayerSacrificialLamb() {
        if (this.lamb == null) {
            this.lamb = new PlayerSacrificialLamb();
        }
        return this.lamb;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_SACRIFICIAL_LAMB) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSacrificialLamb().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSacrificialLamb().loadNBTData(nbt);
    }
}
