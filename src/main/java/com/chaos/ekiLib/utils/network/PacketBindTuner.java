package com.chaos.ekiLib.utils.network;

import com.chaos.ekiLib.station.data.Station;
import com.chaos.ekiLib.utils.util.UtilPacketBuffer;
import com.chaos.ekiLib.utils.util.UtilStationConverter;
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
            ctx.get().enqueueWork(() -> ctx.get().getSender().getHeldItemMainhand().setTag(UtilStationConverter.toNBT(pkg.targetStation)));
        }
    }
}
