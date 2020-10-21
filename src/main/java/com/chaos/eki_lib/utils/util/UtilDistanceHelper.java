package com.chaos.eki_lib.utils.util;

import net.minecraft.util.math.BlockPos;

import java.text.DecimalFormat;

public class UtilDistanceHelper {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static double calculatePrice(BlockPos posA, BlockPos posB) {
        double p = calculateLength(posA, posB);
        return Double.parseDouble(df.format(p));
    }

    public static double calculateLength(BlockPos posA, BlockPos posB) {
        double l = (double) (Math.round(Math.sqrt(Math.pow(posB.getX() - posA.getX(), 2) + Math.pow(posB.getZ() - posA.getZ(), 2))));
        return Double.parseDouble(df.format(l));
    }


}
