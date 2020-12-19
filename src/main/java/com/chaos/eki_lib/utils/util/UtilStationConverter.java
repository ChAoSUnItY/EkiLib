package com.chaos.eki_lib.utils.util;

import com.chaos.eki_lib.station.data.EnumStationLevel;
import com.chaos.eki_lib.station.data.Station;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class UtilStationConverter {
    public static final String NAME = "Name";
    public static final String POSITION = "Position";
    public static final String LEVEL = "Station Level";
    public static final String DIMENSION = "Dimension";

    public static CompoundNBT toNBT(Station station) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(NAME, station.getName());
        nbt.putIntArray(POSITION, toIntegerArray(station.getPosition()));
        nbt.putString(LEVEL, station.getLevel().name());
        nbt.putString(DIMENSION, station.getDimension().toString());
        return nbt;
    }

    public static Station toStation(CompoundNBT nbt) {
        return new Station(nbt.getString(NAME), toBlockPos(nbt.getIntArray(POSITION)), EnumStationLevel.valueOf(nbt.getString(LEVEL)), ResourceLocation.tryCreate(nbt.getString(DIMENSION)));
    }

    public static boolean hasStationInfo(CompoundNBT nbt) {
        return nbt.contains(NAME) && nbt.contains(POSITION) && nbt.contains(LEVEL) && nbt.contains(DIMENSION);
    }

    public static int[] toIntegerArray(BlockPos pos) {
        return new int[]{pos.getX(), pos.getY(), pos.getZ()};
    }

    public static BlockPos toBlockPos(CompoundNBT nbt, String key) { return toBlockPos(nbt.getIntArray(key)); }

    public static BlockPos toBlockPos(int[] pos) {
        return new BlockPos(pos[0], pos[1], pos[2]);
    }
}
