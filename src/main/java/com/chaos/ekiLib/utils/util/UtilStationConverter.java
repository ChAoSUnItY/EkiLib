package com.chaos.ekiLib.utils.util;

import com.chaos.ekiLib.station.data.EnumStationLevel;
import com.chaos.ekiLib.station.data.Station;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

public class UtilStationConverter {
    public static final String NAME = "Name";
    public static final String POSITION = "Position";
    public static final String LEVEL = "Station Level";
    public static final String ID = "Dimension ID";

    public static CompoundNBT toNBT(Station station) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(NAME, station.getName());
        nbt.putIntArray(POSITION, toINTarray(station.getPosition()));
        nbt.putString(LEVEL, station.getLevel().name());
        nbt.putInt(ID, station.getDimensionID());
        return nbt;
    }

    public static Station toStation(CompoundNBT nbt) {
        return new Station(nbt.getString(NAME), toBlockPos(nbt.getIntArray(POSITION)), EnumStationLevel.valueOf(nbt.getString(LEVEL)), nbt.getInt(ID));
    }

    public static int[] toINTarray(BlockPos pos) {
        return new int[]{pos.getX(), pos.getY(), pos.getZ()};
    }

    public static BlockPos toBlockPos(int[] pos) {
        return new BlockPos(pos[0], pos[1], pos[2]);
    }
}
