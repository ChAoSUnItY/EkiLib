package com.chaos.eki_lib.utils.util.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class UtilSubblockRegistry<A extends Block> {
    private final Supplier<A> blockIniter;
    private final Item.Properties itemProp;

    public UtilSubblockRegistry(Supplier<A> blockIniter, Item.Properties itemProp) {
        this.blockIniter = blockIniter;
        this.itemProp = itemProp;
    }

    public <I extends Enum<I> & IStringSerializable> void registerSubblocksWithOneEnum(RegistryObject<A>[] blocksToAssign,
                                                                                       DeferredRegister<Block> deferredRegisterBlock,
                                                                                       RegistryObject<BlockItem>[] itemsToAssign,
                                                                                       DeferredRegister<Item> deferredRegisterItem,
                                                                                       String baseName,
                                                                                       Class<I> enumClazz) {
        I[] enums = enumClazz.getEnumConstants();
        IntStream.range(0, enums.length)
                .forEach(i -> {
                    final String name = baseName + "_" + enums[i].getString();
                    blocksToAssign[i] = deferredRegisterBlock.register(name,
                            blockIniter);
                    itemsToAssign[i] = deferredRegisterItem.register(name,
                            () -> new BlockItem(blocksToAssign[i].get(), itemProp));
                });
    }

    public <I extends Enum<I> & IStringSerializable, J extends Enum<J> & IStringSerializable> void registerSubblocksWithTwoEnum(RegistryObject<A>[] blocksToAssign,
                                                                                                                                DeferredRegister<Block> deferredRegisterBlock,
                                                                                                                                RegistryObject<BlockItem>[] itemsToAssign,
                                                                                                                                DeferredRegister<Item> deferredRegisterItem,
                                                                                                                                String baseName,
                                                                                                                                Class<I> enumClazzA,
                                                                                                                                Class<J> enumClazzB) {
        I[] enumsA = enumClazzA.getEnumConstants();
        J[] enumsB = enumClazzB.getEnumConstants();
        IntStream.range(0, enumsA.length)
                .forEach(i ->
                        IntStream.range(0, enumsB.length)
                                .forEach(j -> {
                                    final String name = baseName + "_" + enumsA[i].getString() + "_" + enumsB[j].getString();
                                    blocksToAssign[i * enumsB.length + j] = deferredRegisterBlock.register(name,
                                            blockIniter);
                                    itemsToAssign[i * enumsB.length + j] = deferredRegisterItem.register(name,
                                            () -> new BlockItem(blocksToAssign[i * enumsB.length + j].get(), itemProp));
                                }));
    }
}
