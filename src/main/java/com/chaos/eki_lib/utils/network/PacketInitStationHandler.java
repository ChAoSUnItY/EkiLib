package com.chaos.eki_lib.utils.network;

import com.chaos.eki_lib.station.data.Station;
import com.chaos.eki_lib.utils.handlers.PacketHandler;
import com.chaos.eki_lib.utils.handlers.StationHandler;
import com.chaos.eki_lib.utils.util.UtilPacketBuffer;
import com.google.common.collect.Lists;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.function.Supplier;

public class PacketInitStationHandler implements IPacketBase {
    protected final List<Station> stations;
    protected int counter;

    public PacketInitStationHandler(List<Station> stations) {
        this.stations = stations;
        this.counter = stations.size();
    }

    public static void encode(PacketInitStationHandler pkg, PacketBuffer buf) {
        buf.writeVarInt(pkg.counter);
        pkg.stations.forEach(station -> UtilPacketBuffer.encodeStation(station, buf));
    }

    public static PacketInitStationHandler decode(PacketBuffer buf) {
        List<Station> stations = Lists.newArrayList();
        int counter = buf.readVarInt();
        for (int i = 0; i < counter; i++)
            stations.add(UtilPacketBuffer.decodeStation(buf));
        return new PacketInitStationHandler(stations);
    }

    public static void handle(PacketInitStationHandler pkg, Supplier<NetworkEvent.Context> ctx) {
        if (IPacketBase.handleInSide(ctx, Dist.CLIENT)) {
            ctx.get().enqueueWork(() -> {
                StationHandler.INSTANCE.init(pkg.stations);
            });
        }
    }

    public static class PacketReloadStationHandler extends PacketInitStationHandler {

        public PacketReloadStationHandler(List<Station> stations) {
            super(stations);
        }

        public static void encode(PacketReloadStationHandler pkg, PacketBuffer buf) {
            buf.writeVarInt(pkg.counter);
            pkg.stations.forEach(station -> UtilPacketBuffer.encodeStation(station, buf));
        }

        public static PacketReloadStationHandler decode(PacketBuffer buf) {
            List<Station> stations = Lists.newArrayList();
            int counter = buf.readVarInt();
            for (int i = 0; i < counter; i++)
                stations.add(UtilPacketBuffer.decodeStation(buf));
            return new PacketReloadStationHandler(stations);
        }

        public static void handle(PacketReloadStationHandler pkg, Supplier<NetworkEvent.Context> ctx) {
            if (IPacketBase.handleInSide(ctx, Dist.DEDICATED_SERVER)) {
                ctx.get().enqueueWork(() -> {
                    StationHandler.INSTANCE.reload(pkg.stations);
                    PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketNoticeStationChanged(StationHandler.INSTANCE.getStations()));
                });
            }
        }
    }
}
