package com.chaos.eki_lib.objects.blocks;

import com.chaos.eki_lib.objects.blocks.base.HorizontalBaseBlock;
import com.chaos.eki_lib.utils.util.voxel_shapes.HorizontalVoxelShapes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class SideSlabBlock<T extends Block> extends HorizontalBaseBlock {
    public static final HorizontalVoxelShapes SHAPES = new HorizontalVoxelShapes(
            Block.makeCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0),
            Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0),
            Block.makeCuboidShape(0.0, 0.0, 0.0, 8.0, 16.0, 16.0),
            Block.makeCuboidShape(8.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    );

    public SideSlabBlock(RegistryObject<T> parentBlock) {
        super(AbstractBlock.Properties.from(parentBlock.get()));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.getByDirection(state.get(FACING));
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (newState.getBlock() == state.getBlock())
            return;
    }
}
