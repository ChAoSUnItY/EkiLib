package com.chaos.eki_lib.utils.util;

import com.chaos.eki_lib.station.data.EnumStationLevel;
import com.chaos.eki_lib.station.data.Station;
import net.minecraft.network.PacketBuffer;

public class UtilPacketBuffer {
    public static void encodeStation(Station station, PacketBuffer buf) {
        buf.writeString(station.getName(), 100);
        buf.writeBlockPos(station.getPosition());
        buf.writeEnumValue(station.getLevel());
        buf.writeResourceLocation(station.getDimension());
    }

    public static Station decodeStation(PacketBuffer buf) {
        return new Station(buf.readString(100),
                buf.readBlockPos(),
                buf.readEnumValue(EnumStationLevel.class),
                buf.readResourceLocation());
    }
}
