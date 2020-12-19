package com.chaos.eki_lib.utils.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class UtilCastMagic {
    public static <T extends Item> T castItem(Class<T> clazz, Item item) {
        return cast(clazz, item);
    }

    public static <T extends Block> T castBlock(Class<T> clazz, Block blk) {
        return cast(clazz, blk);
    }

    public static <T extends TileEntity> T castTileEntity(Class<T> clazz, TileEntity te) {
        return cast(clazz, te);
    }

    private static <T, K extends T> K cast(Class<K> clazz, T obj) {
        return clazz.cast(obj);
    }
}
