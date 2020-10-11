package com.chaos.ekiLib.utils.util;

import com.chaos.ekiLib.station.data.EnumStationLevel;
import com.chaos.ekiLib.station.data.Station;
import net.minecraft.network.PacketBuffer;

public class UtilPacketBuffer {
    public static void encodeStation(Station station, PacketBuffer buf) {
        buf.writeString(station.getName());
        buf.writeBlockPos(station.getPosition());
        buf.writeString(station.getOperator());
        buf.writeEnumValue(station.getLevel());
        buf.writeVarInt(station.getDimensionID());
    }

    public static Station decodeStation(PacketBuffer buf) {
        return new Station(buf.readString(),
                buf.readBlockPos(),
                buf.readString(),
                buf.readEnumValue(EnumStationLevel.class),
                buf.readVarInt());
    }
}
