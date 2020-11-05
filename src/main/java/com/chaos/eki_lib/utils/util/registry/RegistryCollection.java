package com.chaos.eki_lib.utils.util.registry;

import com.chaos.eki_lib.objects.blocks.base.BaseBlock;
import javafx.util.Pair;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegistryCollection<T extends Block> {
    protected final String name;
    private final Pair<DeferredRegister<Block>, DeferredRegister<Item>> deferredRegisters;
    private final AbstractBlock.Properties blkProp;
    private final ItemGroup group;
    protected RegistryPair<T> BASE_BLOCK;
    protected RegistryPair<? extends StairsBlock> STAIRS_BLOCK;
    protected RegistryPair<? extends SlabBlock> SLAB_BLOCK;

    private RegistryCollection(String name,
                               AbstractBlock.Properties blockProperties,
                               ItemGroup group,
                               Pair<DeferredRegister<Block>, DeferredRegister<Item>> deferredRegisters) {
        this.name = name;
        this.deferredRegisters = deferredRegisters;
        this.blkProp = blockProperties;
        this.group = group;
    }

    public static RegistryCollection create(String name,
                                            AbstractBlock.Properties blockProperties,
                                            ItemGroup group,
                                            Pair<DeferredRegister<Block>, DeferredRegister<Item>> deferredRegisters) {
        return new RegistryCollection(name, blockProperties, group, deferredRegisters);
    }

    public RegistryCollection<T> init(Supplier<T> blkConstructor) {
        final Item.Properties item = new Item.Properties().group(this.group);
        final DeferredRegister<Block> blkReg = deferredRegisters.getKey();
        final DeferredRegister<Item> itemReg = deferredRegisters.getValue();
        this.BASE_BLOCK = new RegistryPair(
                blkReg.register(this.name, blkConstructor),
                item,
                itemReg);
        this.STAIRS_BLOCK = new RegistryPair(
                blkReg.register(this.name + "_stairs",
                        () -> new StairsBlock(()
                                -> this.BASE_BLOCK.getBlock().get().getDefaultState(), this.blkProp)),
                item,
                itemReg);
        this.SLAB_BLOCK = new RegistryPair(
                blkReg.register(this.name + "_slab",
                        () -> new SlabBlock(AbstractBlock.Properties.from(this.BASE_BLOCK.getBlock().get()))),
                item,
                itemReg);
        return this;
    }

    public class RegistryPair<B extends Block> {
        private final RegistryObject<B> BLOCK;
        private final RegistryObject<BlockItem> BLOCK_ITEM;

        public RegistryPair(RegistryObject<B> BLOCK,
                            Item.Properties prop,
                            DeferredRegister<Item> deferredRegister) {
            this.BLOCK = BLOCK;
            this.BLOCK_ITEM = deferredRegister.register(name, () -> new BlockItem(this.BLOCK.get(), prop));
        }

        public RegistryObject<B> getBlock() {
            return BLOCK;
        }

        public RegistryObject<BlockItem> getItem() {
            return BLOCK_ITEM;
        }
    }
}
