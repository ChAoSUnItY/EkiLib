package com.chaos.eki_lib.utils.network;

import com.chaos.eki_lib.station.data.Station;
import com.chaos.eki_lib.utils.util.UtilPacketBuffer;
import com.chaos.eki_lib.utils.util.UtilStationConverter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketBindTuner implements IPacketBase {
    private final Station targetStation;

    public PacketBindTuner(Station targetStation) {
        this.targetStation = targetStation;
    }

    public static void encode(PacketBindTuner pkg, PacketBuffer buf) {
        UtilPacketBuffer.encodeStation(pkg.targetStation, buf);
    }

    public static PacketBindTuner decode(PacketBuffer buf) {
        Station targetStation = UtilPacketBuffer.decodeStation(buf);
        return new PacketBindTuner(targetStation);
    }

    public static void handle(PacketBindTuner pkg, Supplier<NetworkEvent.Context> ctx) {
        if (IPacketBase.handleInSide(ctx, Dist.DEDICATED_SERVER)) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putIntArray(UtilStationConverter.POSITION, UtilStationConverter.toIntegerArray(pkg.targetStation.getPosition()));
            ctx.get().enqueueWork(() -> ctx.get().getSender().getHeldItemMainhand().setTag(nbt));
        }
    }
}
