package com.chaos.ekiLib.utils.network;

import com.chaos.ekiLib.station.data.Station;
import com.chaos.ekiLib.utils.handlers.StationHandler;
import com.chaos.ekiLib.utils.util.UtilPacketBuffer;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class PacketNoticeStationChanged implements IPacketBase {
    private final List<Station> stations;
    protected int counter;

    public PacketNoticeStationChanged(List<Station> stations) {
        this.stations = stations;
        this.counter = stations.size();
    }

    public static void encode(PacketNoticeStationChanged pkg, PacketBuffer buf) {
        buf.writeVarInt(pkg.counter);
        pkg.stations.forEach(station -> UtilPacketBuffer.encodeStation(station, buf));
    }

    public static PacketNoticeStationChanged decode(PacketBuffer buf) {
        List<Station> stations = Lists.newArrayList();
        int counter = buf.readVarInt();
        for (int i = 0; i < counter; i++)
            stations.add(UtilPacketBuffer.decodeStation(buf));
        return new PacketNoticeStationChanged(stations);
    }

    public static void handle(PacketNoticeStationChanged pkg, Supplier<NetworkEvent.Context> ctx) {
        if (IPacketBase.handleInSide(ctx, Dist.CLIENT)) {
            ctx.get().enqueueWork(() -> {
                if (Minecraft.getInstance().isSingleplayer())
                    return;
                StationHandler.INSTANCE.reload(pkg.stations);
            });
        }
    }
}
