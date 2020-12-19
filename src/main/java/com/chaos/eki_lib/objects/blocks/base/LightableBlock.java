package com.chaos.eki_lib.objects.blocks.base;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class LightableBlock extends BaseBlock {
    private final int light;

    public LightableBlock(Properties properties, int light) {
        super(properties);
        this.light = light;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    public static class LightableHorizontalBlock extends HorizontalBaseBlock {
        private final int light;

        public LightableHorizontalBlock(Properties properties, int light) {
            super(properties);
            this.light = light;
        }

        @Override
        public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
            return VoxelShapes.empty();
        }

        @Override
        public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
            return VoxelShapes.empty();
        }
    }

    public static class LightableDirectionalBlock extends DirectionalBaseBlock {
        private final int light;

        public LightableDirectionalBlock(Properties properties, int light) {
            super(properties);
            this.light = light;
        }

        @Override
        public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
            return VoxelShapes.empty();
        }

        @Override
        public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
            return VoxelShapes.empty();
        }
    }
}