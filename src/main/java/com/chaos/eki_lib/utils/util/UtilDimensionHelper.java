package com.chaos.eki_lib.utils.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class UtilDimensionHelper {
    public static ResourceLocation getDimension(World world) {
        return world.getDimension().getType().getRegistryName();
    }
}
