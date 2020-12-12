package com.chaos.eki_lib.utils.util;

import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;

import java.util.Optional;
import java.util.OptionalInt;

public class UtilDimensionConverter {
    public static int getDimensionID(World world) {
        return dimensionToID(world).getAsInt();
    }

    public static OptionalInt dimensionToID(World world) {
        return dimensionKeyToID(world.getDimension());
    }

    public static OptionalInt dimensionKeyToID(Dimension dimension) {
        int resultID = 0;

        if (DimensionType.OVERWORLD.equals(dimension.getType())) {
            return OptionalInt.of(resultID);
        } else if (DimensionType.THE_NETHER.equals(dimension.getType())) {
            return OptionalInt.of(resultID--);
        } else if (DimensionType.THE_END.equals(dimension.getType())) {
            return OptionalInt.of(resultID++);
        } else {
            return OptionalInt.empty();
        }
    }

    public static Optional<String> IDtoDimensionRegistryName(int ID) {
        if (ID == 0) {
            return Optional.of(DimensionType.OVERWORLD.getRegistryName().getPath());
        } else if (ID == 1) {
            return Optional.of(DimensionType.THE_END.getRegistryName().getPath());
        } else if (ID == -1) {
            return Optional.of(DimensionType.THE_NETHER.getRegistryName().getPath());
        } else {
            return Optional.empty();
        }
    }
}
