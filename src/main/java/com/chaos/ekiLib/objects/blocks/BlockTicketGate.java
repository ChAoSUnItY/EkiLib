package com.chaos.ekiLib.objects.blocks;

import com.chaos.ekiLib.objects.blocks.base.BlockHorizontalBase;
import com.chaos.ekiLib.utils.util.voxel_shapes.HorizontalVoxelShapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.stream.Stream;

public class BlockTicketGate extends BlockHorizontalBase {
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final HorizontalVoxelShapes UNOPENED_SHAPES = new HorizontalVoxelShapes(
            Stream.of(
                    Block.makeCuboidShape(0.5, 3, 0.25, 2.5, 12.75, 15.75),
                    Block.makeCuboidShape(0.5, 0, 2, 3, 3, 14),
                    Block.makeCuboidShape(15.5, 12.75, 0, 16, 15, 16),
                    Block.makeCuboidShape(15.75, 3, 0, 16, 12.75, 16),
                    Block.makeCuboidShape(15.5, 6, 0, 15.75, 11, 9.25),
                    Block.makeCuboidShape(15.5, 6, 15.75, 15.75, 11, 16),
                    Block.makeCuboidShape(2.75, 6, 0.25, 3, 11, 9.25),
                    Block.makeCuboidShape(15.5, 3, 0, 15.75, 6, 16),
                    Block.makeCuboidShape(2.75, 3, 0.25, 3, 6, 15.75),
                    Block.makeCuboidShape(2.75, 11, 0.25, 3, 12.75, 15.75),
                    Block.makeCuboidShape(15.5, 11, 0, 15.75, 12.75, 16),
                    Block.makeCuboidShape(0, 3, 0, 0.5, 12.75, 16),
                    Block.makeCuboidShape(15.5, 0, 2, 16, 3, 14),
                    Block.makeCuboidShape(0, 12.75, 0, 0.5, 15, 16),
                    Block.makeCuboidShape(0, 0, 2, 0.5, 3, 14),
                    Block.makeCuboidShape(0.5, 13, 12.75, 3, 13.25, 14),
                    Block.makeCuboidShape(0.5, 12.75, 5, 3, 13, 16),
                    Block.makeCuboidShape(0.5, 12.75, 2, 3, 13.25, 5),
                    Block.makeCuboidShape(0.5, 12.75, 0, 3, 13.5, 2),
                    Block.makeCuboidShape(0.5, 10.5, 0, 3, 12.75, 0.25),
                    Block.makeCuboidShape(0.5, 10.5, 15.75, 3, 12.75, 16),
                    Block.makeCuboidShape(0.5, 3, 0, 3, 8, 0.25),
                    Block.makeCuboidShape(0.5, 3, 15.75, 3, 8, 16),
                    Block.makeCuboidShape(0.5, 8, 0, 3, 10.5, 0.25),
                    Block.makeCuboidShape(0.5, 8, 15.75, 3, 10.5, 16),
                    Block.makeCuboidShape(9.25, 6, 9, 15.75, 11, 9.5),
                    Block.makeCuboidShape(2.5, 6, 9, 9, 11, 9.5)
            ),
            Stream.of(
                    Block.makeCuboidShape(13.5, 3, 0.25, 15.5, 12.75, 15.75),
                    Block.makeCuboidShape(13, 0, 2, 15.5, 3, 14),
                    Block.makeCuboidShape(0, 12.75, 0, 0.5, 15, 16),
                    Block.makeCuboidShape(0, 3, 0, 0.25, 12.75, 16),
                    Block.makeCuboidShape(0.25, 6, 6.75, 0.5, 11, 16),
                    Block.makeCuboidShape(0.25, 6, 0, 0.5, 11, 0.25),
                    Block.makeCuboidShape(13, 6, 6.75, 13.25, 11, 15.75),
                    Block.makeCuboidShape(0.25, 3, 0, 0.5, 6, 16),
                    Block.makeCuboidShape(13, 3, 0.25, 13.25, 6, 15.75),
                    Block.makeCuboidShape(13, 11, 0.25, 13.25, 12.75, 15.75),
                    Block.makeCuboidShape(0.25, 11, 0, 0.5, 12.75, 16),
                    Block.makeCuboidShape(15.5, 3, 0, 16, 12.75, 16),
                    Block.makeCuboidShape(0, 0, 2, 0.5, 3, 14),
                    Block.makeCuboidShape(15.5, 12.75, 0, 16, 15, 16),
                    Block.makeCuboidShape(15.5, 0, 2, 16, 3, 14),
                    Block.makeCuboidShape(13, 13, 2, 15.5, 13.25, 3.25),
                    Block.makeCuboidShape(13, 12.75, 0, 15.5, 13, 11),
                    Block.makeCuboidShape(13, 12.75, 11, 15.5, 13.25, 14),
                    Block.makeCuboidShape(13, 12.75, 14, 15.5, 13.5, 16),
                    Block.makeCuboidShape(13, 10.5, 15.75, 15.5, 12.75, 16),
                    Block.makeCuboidShape(13, 10.5, 0, 15.5, 12.75, 0.25),
                    Block.makeCuboidShape(13, 3, 15.75, 15.5, 8, 16),
                    Block.makeCuboidShape(13, 3, 0, 15.5, 8, 0.25),
                    Block.makeCuboidShape(13, 8, 15.75, 15.5, 10.5, 16),
                    Block.makeCuboidShape(13, 8, 0, 15.5, 10.5, 0.25),
                    Block.makeCuboidShape(0.25, 6, 6.5, 6.75, 11, 7),
                    Block.makeCuboidShape(7, 6, 6.5, 13.5, 11, 7)
            ),
            Stream.of(
                    Block.makeCuboidShape(0.25, 3, 0.5, 15.75, 12.75, 2.5),
                    Block.makeCuboidShape(2, 0, 0.5, 14, 3, 3),
                    Block.makeCuboidShape(0, 12.75, 15.5, 16, 15, 16),
                    Block.makeCuboidShape(0, 3, 15.75, 16, 12.75, 16),
                    Block.makeCuboidShape(6.75, 6, 15.5, 16, 11, 15.75),
                    Block.makeCuboidShape(0, 6, 15.5, 0.25, 11, 15.75),
                    Block.makeCuboidShape(6.75, 6, 2.75, 15.75, 11, 3),
                    Block.makeCuboidShape(0, 3, 15.5, 16, 6, 15.75),
                    Block.makeCuboidShape(0.25, 3, 2.75, 15.75, 6, 3),
                    Block.makeCuboidShape(0.25, 11, 2.75, 15.75, 12.75, 3),
                    Block.makeCuboidShape(0, 11, 15.5, 16, 12.75, 15.75),
                    Block.makeCuboidShape(0, 3, 0, 16, 12.75, 0.5),
                    Block.makeCuboidShape(2, 0, 15.5, 14, 3, 16),
                    Block.makeCuboidShape(0, 12.75, 0, 16, 15, 0.5),
                    Block.makeCuboidShape(2, 0, 0, 14, 3, 0.5),
                    Block.makeCuboidShape(2, 13, 0.5, 3.25, 13.25, 3),
                    Block.makeCuboidShape(0, 12.75, 0.5, 11, 13, 3),
                    Block.makeCuboidShape(11, 12.75, 0.5, 14, 13.25, 3),
                    Block.makeCuboidShape(14, 12.75, 0.5, 16, 13.5, 3),
                    Block.makeCuboidShape(15.75, 10.5, 0.5, 16, 12.75, 3),
                    Block.makeCuboidShape(0, 10.5, 0.5, 0.25, 12.75, 3),
                    Block.makeCuboidShape(15.75, 3, 0.5, 16, 8, 3),
                    Block.makeCuboidShape(0, 3, 0.5, 0.25, 8, 3),
                    Block.makeCuboidShape(15.75, 8, 0.5, 16, 10.5, 3),
                    Block.makeCuboidShape(0, 8, 0.5, 0.25, 10.5, 3),
                    Block.makeCuboidShape(6.5, 6, 9.25, 7, 11, 15.75),
                    Block.makeCuboidShape(6.5, 6, 2.5, 7, 11, 9)
            ),
            Stream.of(
                    Block.makeCuboidShape(0.25, 3, 13.5, 15.75, 12.75, 15.5),
                    Block.makeCuboidShape(2, 0, 13, 14, 3, 15.5),
                    Block.makeCuboidShape(0, 12.75, 0, 16, 15, 0.5),
                    Block.makeCuboidShape(0, 3, 0, 16, 12.75, 0.25),
                    Block.makeCuboidShape(0, 6, 0.25, 9.25, 11, 0.5),
                    Block.makeCuboidShape(15.75, 6, 0.25, 16, 11, 0.5),
                    Block.makeCuboidShape(0.25, 6, 13, 9.25, 11, 13.25),
                    Block.makeCuboidShape(0, 3, 0.25, 16, 6, 0.5),
                    Block.makeCuboidShape(0.25, 3, 13, 15.75, 6, 13.25),
                    Block.makeCuboidShape(0.25, 11, 13, 15.75, 12.75, 13.25),
                    Block.makeCuboidShape(0, 11, 0.25, 16, 12.75, 0.5),
                    Block.makeCuboidShape(0, 3, 15.5, 16, 12.75, 16),
                    Block.makeCuboidShape(2, 0, 0, 14, 3, 0.5),
                    Block.makeCuboidShape(0, 12.75, 15.5, 16, 15, 16),
                    Block.makeCuboidShape(2, 0, 15.5, 14, 3, 16),
                    Block.makeCuboidShape(12.75, 13, 13, 14, 13.25, 15.5),
                    Block.makeCuboidShape(5, 12.75, 13, 16, 13, 15.5),
                    Block.makeCuboidShape(2, 12.75, 13, 5, 13.25, 15.5),
                    Block.makeCuboidShape(0, 12.75, 13, 2, 13.5, 15.5),
                    Block.makeCuboidShape(0, 10.5, 13, 0.25, 12.75, 15.5),
                    Block.makeCuboidShape(15.75, 10.5, 13, 16, 12.75, 15.5),
                    Block.makeCuboidShape(0, 3, 13, 0.25, 8, 15.5),
                    Block.makeCuboidShape(15.75, 3, 13, 16, 8, 15.5),
                    Block.makeCuboidShape(0, 8, 13, 0.25, 10.5, 15.5),
                    Block.makeCuboidShape(15.75, 8, 13, 16, 10.5, 15.5),
                    Block.makeCuboidShape(9, 6, 0.25, 9.5, 11, 6.75),
                    Block.makeCuboidShape(9, 6, 7, 9.5, 11, 13.5)
            ));

    public static final HorizontalVoxelShapes OPENED_SHAPES = new HorizontalVoxelShapes(
            Stream.of(
                    Block.makeCuboidShape(2.75, 6, 9.25, 3.25, 11, 15.75),
                    Block.makeCuboidShape(15.25, 6, 9.25, 15.75, 11, 15.75),
                    Block.makeCuboidShape(0.5, 3, 0.25, 2.5, 12.75, 15.75),
                    Block.makeCuboidShape(0.5, 0, 2, 3, 3, 14),
                    Block.makeCuboidShape(15.5, 12.75, 0, 16, 15, 16),
                    Block.makeCuboidShape(15.75, 3, 0, 16, 12.75, 16),
                    Block.makeCuboidShape(15.5, 6, 0, 15.75, 11, 9.25),
                    Block.makeCuboidShape(15.5, 6, 15.75, 15.75, 11, 16),
                    Block.makeCuboidShape(2.75, 6, 0.25, 3, 11, 9.25),
                    Block.makeCuboidShape(15.5, 3, 0, 15.75, 6, 16),
                    Block.makeCuboidShape(2.75, 3, 0.25, 3, 6, 15.75),
                    Block.makeCuboidShape(15.5, 11, 0, 15.75, 12.75, 16),
                    Block.makeCuboidShape(2.75, 11, 0.25, 3, 12.75, 15.75),
                    Block.makeCuboidShape(15.5, 0, 2, 16, 3, 14),
                    Block.makeCuboidShape(0, 3, 0, 0.5, 12.75, 16),
                    Block.makeCuboidShape(0, 12.75, 0, 0.5, 15, 16),
                    Block.makeCuboidShape(0, 0, 2, 0.5, 3, 14),
                    Block.makeCuboidShape(0.5, 13, 12.75, 3, 13.25, 14),
                    Block.makeCuboidShape(0.5, 12.75, 5, 3, 13, 16),
                    Block.makeCuboidShape(0.5, 12.75, 2, 3, 13.25, 5),
                    Block.makeCuboidShape(0.5, 12.75, 0, 3, 13.5, 2),
                    Block.makeCuboidShape(0.5, 10.5, 0, 3, 12.75, 0.25),
                    Block.makeCuboidShape(0.5, 10.5, 15.75, 3, 12.75, 16),
                    Block.makeCuboidShape(0.5, 3, 0, 3, 8, 0.25),
                    Block.makeCuboidShape(0.5, 3, 15.75, 3, 8, 16),
                    Block.makeCuboidShape(0.5, 8, 0, 3, 10.5, 0.25),
                    Block.makeCuboidShape(0.5, 8, 15.75, 3, 10.5, 16)
            ),
            Stream.of(
                    Block.makeCuboidShape(12.75, 6, 0.25, 13.25, 11, 6.75),
                    Block.makeCuboidShape(0.25, 6, 0.25, 0.75, 11, 6.75),
                    Block.makeCuboidShape(13.5, 3, 0.25, 15.5, 12.75, 15.75),
                    Block.makeCuboidShape(13, 0, 2, 15.5, 3, 14),
                    Block.makeCuboidShape(0, 12.75, 0, 0.5, 15, 16),
                    Block.makeCuboidShape(0, 3, 0, 0.25, 12.75, 16),
                    Block.makeCuboidShape(0.25, 6, 6.75, 0.5, 11, 16),
                    Block.makeCuboidShape(0.25, 6, 0, 0.5, 11, 0.25),
                    Block.makeCuboidShape(13, 6, 6.75, 13.25, 11, 15.75),
                    Block.makeCuboidShape(0.25, 3, 0, 0.5, 6, 16),
                    Block.makeCuboidShape(13, 3, 0.25, 13.25, 6, 15.75),
                    Block.makeCuboidShape(0.25, 11, 0, 0.5, 12.75, 16),
                    Block.makeCuboidShape(13, 11, 0.25, 13.25, 12.75, 15.75),
                    Block.makeCuboidShape(0, 0, 2, 0.5, 3, 14),
                    Block.makeCuboidShape(15.5, 3, 0, 16, 12.75, 16),
                    Block.makeCuboidShape(15.5, 12.75, 0, 16, 15, 16),
                    Block.makeCuboidShape(15.5, 0, 2, 16, 3, 14),
                    Block.makeCuboidShape(13, 13, 2, 15.5, 13.25, 3.25),
                    Block.makeCuboidShape(13, 12.75, 0, 15.5, 13, 11),
                    Block.makeCuboidShape(13, 12.75, 11, 15.5, 13.25, 14),
                    Block.makeCuboidShape(13, 12.75, 14, 15.5, 13.5, 16),
                    Block.makeCuboidShape(13, 10.5, 15.75, 15.5, 12.75, 16),
                    Block.makeCuboidShape(13, 10.5, 0, 15.5, 12.75, 0.25),
                    Block.makeCuboidShape(13, 3, 15.75, 15.5, 8, 16),
                    Block.makeCuboidShape(13, 3, 0, 15.5, 8, 0.25),
                    Block.makeCuboidShape(13, 8, 15.75, 15.5, 10.5, 16),
                    Block.makeCuboidShape(13, 8, 0, 15.5, 10.5, 0.25)
            ),
            Stream.of(
                    Block.makeCuboidShape(0.25, 6, 2.75, 6.75, 11, 3.25),
                    Block.makeCuboidShape(0.25, 6, 15.25, 6.75, 11, 15.75),
                    Block.makeCuboidShape(0.25, 3, 0.5, 15.75, 12.75, 2.5),
                    Block.makeCuboidShape(2, 0, 0.5, 14, 3, 3),
                    Block.makeCuboidShape(0, 12.75, 15.5, 16, 15, 16),
                    Block.makeCuboidShape(0, 3, 15.75, 16, 12.75, 16),
                    Block.makeCuboidShape(6.75, 6, 15.5, 16, 11, 15.75),
                    Block.makeCuboidShape(0, 6, 15.5, 0.25, 11, 15.75),
                    Block.makeCuboidShape(6.75, 6, 2.75, 15.75, 11, 3),
                    Block.makeCuboidShape(0, 3, 15.5, 16, 6, 15.75),
                    Block.makeCuboidShape(0.25, 3, 2.75, 15.75, 6, 3),
                    Block.makeCuboidShape(0, 11, 15.5, 16, 12.75, 15.75),
                    Block.makeCuboidShape(0.25, 11, 2.75, 15.75, 12.75, 3),
                    Block.makeCuboidShape(2, 0, 15.5, 14, 3, 16),
                    Block.makeCuboidShape(0, 3, 0, 16, 12.75, 0.5),
                    Block.makeCuboidShape(0, 12.75, 0, 16, 15, 0.5),
                    Block.makeCuboidShape(2, 0, 0, 14, 3, 0.5),
                    Block.makeCuboidShape(2, 13, 0.5, 3.25, 13.25, 3),
                    Block.makeCuboidShape(0, 12.75, 0.5, 11, 13, 3),
                    Block.makeCuboidShape(11, 12.75, 0.5, 14, 13.25, 3),
                    Block.makeCuboidShape(14, 12.75, 0.5, 16, 13.5, 3),
                    Block.makeCuboidShape(15.75, 10.5, 0.5, 16, 12.75, 3),
                    Block.makeCuboidShape(0, 10.5, 0.5, 0.25, 12.75, 3),
                    Block.makeCuboidShape(15.75, 3, 0.5, 16, 8, 3),
                    Block.makeCuboidShape(0, 3, 0.5, 0.25, 8, 3),
                    Block.makeCuboidShape(15.75, 8, 0.5, 16, 10.5, 3),
                    Block.makeCuboidShape(0, 8, 0.5, 0.25, 10.5, 3)
            ),
            Stream.of(
                    Block.makeCuboidShape(9.25, 6, 12.75, 15.75, 11, 13.25),
                    Block.makeCuboidShape(9.25, 6, 0.25, 15.75, 11, 0.75),
                    Block.makeCuboidShape(0.25, 3, 13.5, 15.75, 12.75, 15.5),
                    Block.makeCuboidShape(2, 0, 13, 14, 3, 15.5),
                    Block.makeCuboidShape(0, 12.75, 0, 16, 15, 0.5),
                    Block.makeCuboidShape(0, 3, 0, 16, 12.75, 0.25),
                    Block.makeCuboidShape(0, 6, 0.25, 9.25, 11, 0.5),
                    Block.makeCuboidShape(15.75, 6, 0.25, 16, 11, 0.5),
                    Block.makeCuboidShape(0.25, 6, 13, 9.25, 11, 13.25),
                    Block.makeCuboidShape(0, 3, 0.25, 16, 6, 0.5),
                    Block.makeCuboidShape(0.25, 3, 13, 15.75, 6, 13.25),
                    Block.makeCuboidShape(0, 11, 0.25, 16, 12.75, 0.5),
                    Block.makeCuboidShape(0.25, 11, 13, 15.75, 12.75, 13.25),
                    Block.makeCuboidShape(2, 0, 0, 14, 3, 0.5),
                    Block.makeCuboidShape(0, 3, 15.5, 16, 12.75, 16),
                    Block.makeCuboidShape(0, 12.75, 15.5, 16, 15, 16),
                    Block.makeCuboidShape(2, 0, 15.5, 14, 3, 16),
                    Block.makeCuboidShape(12.75, 13, 13, 14, 13.25, 15.5),
                    Block.makeCuboidShape(5, 12.75, 13, 16, 13, 15.5),
                    Block.makeCuboidShape(2, 12.75, 13, 5, 13.25, 15.5),
                    Block.makeCuboidShape(0, 12.75, 13, 2, 13.5, 15.5),
                    Block.makeCuboidShape(0, 10.5, 13, 0.25, 12.75, 15.5),
                    Block.makeCuboidShape(15.75, 10.5, 13, 16, 12.75, 15.5),
                    Block.makeCuboidShape(0, 3, 13, 0.25, 8, 15.5),
                    Block.makeCuboidShape(15.75, 3, 13, 16, 8, 15.5),
                    Block.makeCuboidShape(0, 8, 13, 0.25, 10.5, 15.5),
                    Block.makeCuboidShape(15.75, 8, 13, 16, 10.5, 15.5)
            ));

    public BlockTicketGate() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
        this.setDefaultState(this.getDefaultState().with(OPEN, false));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        worldIn.setBlockState(pos, state.with(OPEN, !state.get(OPEN)));
        return ActionResultType.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(OPEN))
            switch (state.get(FACING)) {
                case SOUTH:
                    return OPENED_SHAPES.getSouth();
                case EAST:
                    return OPENED_SHAPES.getEast();
                case WEST:
                    return OPENED_SHAPES.getWest();
                case NORTH:
                default:
                    return OPENED_SHAPES.getNorth();
            }
        else
            switch (state.get(FACING)) {
                case SOUTH:
                    return UNOPENED_SHAPES.getSouth();
                case EAST:
                    return UNOPENED_SHAPES.getEast();
                case WEST:
                    return UNOPENED_SHAPES.getWest();
                case NORTH:
                default:
                    return UNOPENED_SHAPES.getNorth();
            }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(OPEN);
    }
}
