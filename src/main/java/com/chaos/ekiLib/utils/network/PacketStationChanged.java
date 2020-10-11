package com.chaos.ekiLib.utils.network;

import com.chaos.ekiLib.event.EventStationChanged;
import com.chaos.ekiLib.station.StationManager;
import com.chaos.ekiLib.station.data.Station;
import com.chaos.ekiLib.utils.handlers.PacketHandler;
import com.chaos.ekiLib.utils.util.UtilPacketBuffer;
import com.google.common.collect.Lists;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class PacketStationChanged implements IPacketBase {
    protected final List<Station> stations;

    public PacketStationChanged(List<Station> stations) {
        this.stations = stations;
    }

    public static void encode(PacketStationChanged pkg, PacketBuffer buf) {
        buf.writeVarInt(pkg.stations.size());
        pkg.stations.forEach(station -> UtilPacketBuffer.encodeStation(station, buf));
    }

    public static PacketStationChanged decode(PacketBuffer buf) {
        List<Station> stations = Lists.newArrayList();
        IntStream.range(0, buf.readVarInt() - 1)
                .forEach(i -> stations.add(UtilPacketBuffer.decodeStation(buf)));
        return new PacketStationChanged(stations);
    }

    public static void handle(PacketStationChanged pkg, Supplier<NetworkEvent.Context> ctx) {
        if (IPacketBase.handleInSide(ctx, Dist.DEDICATED_SERVER)) {
            ctx.get().enqueueWork(() -> {
                StationManager.INSTANCE.reload(pkg.stations);
                MinecraftForge.EVENT_BUS.post(new EventStationChanged.Server(pkg.stations));
                PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new PacketRemindStationChanged(pkg.stations));
            });
        }
    }

    public static class PacketRemindStationChanged extends PacketStationChanged {
        public PacketRemindStationChanged(List<Station> stations) {
            super(stations);
        }

        public static void encode(PacketRemindStationChanged pkg, PacketBuffer buf) {
            buf.writeVarInt(pkg.stations.size());
            pkg.stations.forEach(station -> UtilPacketBuffer.encodeStation(station, buf));
        }

        public static PacketRemindStationChanged decode(PacketBuffer buf) {
            List<Station> stations = Lists.newArrayList();
            IntStream.range(0, buf.readVarInt() - 1)
                    .forEach(i -> stations.add(UtilPacketBuffer.decodeStation(buf)));
            return new PacketRemindStationChanged(stations);
        }

        public static void handle(PacketRemindStationChanged pkg, Supplier<NetworkEvent.Context> ctx) {
            if (IPacketBase.handleInSide(ctx, Dist.CLIENT)) {
                ctx.get().enqueueWork(() -> {
                    StationManager.INSTANCE.reload(pkg.stations);
                    MinecraftForge.EVENT_BUS.post(new EventStationChanged.Both(pkg.stations));
                });
            }
        }
    }
}
