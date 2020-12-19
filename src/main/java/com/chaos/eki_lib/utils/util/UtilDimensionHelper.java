package com.chaos.eki_lib.utils.util;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.OptionalInt;

public class UtilDimensionHelper {
    public static ResourceLocation getDimension(World world) {
        return world.getDimensionKey().getLocation();
    }
}
