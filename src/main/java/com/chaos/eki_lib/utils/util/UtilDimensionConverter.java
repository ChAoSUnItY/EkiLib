package com.chaos.eki_lib.utils.util;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.OptionalInt;

public class UtilDimensionConverter {
    public static int getDimensionID(World world) {
        return dimensionToID(world).getAsInt();
    }

    public static OptionalInt dimensionToID(World world) {
        return dimensionKeyToID(world.getDimensionKey());
    }

    public static OptionalInt dimensionKeyToID(RegistryKey<World> key) {
        int resultID = 0;

        if (DimensionType.OVERWORLD.getLocation().equals(key.getLocation())) {
            return OptionalInt.of(resultID);
        } else if (DimensionType.THE_NETHER.getLocation().equals(key.getLocation())) {
            return OptionalInt.of(resultID--);
        } else if (DimensionType.THE_END.getLocation().equals(key.getLocation())) {
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
