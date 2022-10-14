package me.fishbythefin.gladiators.networking.packets;

import me.fishbythefin.gladiators.client.ClientGayRayData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GayRayDataSyncS2CPacket {
    private final long timer;

    public GayRayDataSyncS2CPacket(long timer) {
        this.timer = timer;
    }

    public GayRayDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.timer = buf.readLong();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeLong(timer);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientGayRayData.set(timer);
        });
        return true;
    }
}
