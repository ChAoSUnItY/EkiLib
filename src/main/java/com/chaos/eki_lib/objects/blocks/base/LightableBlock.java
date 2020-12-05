package com.chaos.eki_lib.objects.blocks.base;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class LightableBlock extends BaseBlock {
    public LightableBlock(Properties properties, int light) {
        super(setLightLevel(properties, light));
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return getEmptyRenderShape();
    }

    private static Properties setLightLevel(Properties p, int l) {
        return p.setLightLevel(s -> l);
    }

    private static VoxelShape getEmptyRenderShape() {
        return VoxelShapes.empty();
    }

    public static class LightableHorizontalBlock extends HorizontalBaseBlock {
        public LightableHorizontalBlock(Properties properties, int light) {
            super(setLightLevel(properties, light));
        }

        @Override
        public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
            return getEmptyRenderShape();
        }
    }

    public static class LightableDirectionalBlock extends DirectionalBaseBlock {
        public LightableDirectionalBlock(Properties properties, int light) {
            super(setLightLevel(properties, light));
        }

        @Override
        public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
            return getEmptyRenderShape();
        }
    }
}
