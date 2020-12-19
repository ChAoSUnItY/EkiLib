package com.chaos.eki_lib.objects.blocks;

import com.chaos.eki_lib.objects.blocks.base.HorizontalBaseBlock;
import com.chaos.eki_lib.objects.items.StationTunerItem;
import com.chaos.eki_lib.tileentity.StationNameplateTileEntity;
import com.chaos.eki_lib.utils.handlers.TileEntityHandler;
import com.chaos.eki_lib.utils.util.UtilCastMagic;
import com.chaos.eki_lib.utils.util.UtilStationConverter;
import com.chaos.eki_lib.utils.util.voxel_shapes.HorizontalVoxelShapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class StationNameplateBlock extends HorizontalBaseBlock {
    public static final HorizontalVoxelShapes SHAPES = new HorizontalVoxelShapes(
            Stream.of(
                    Block.makeCuboidShape(-6.0, 6.0, 15.0, 22.0, 16.0, 16.75),
                    Block.makeCuboidShape(-6.0, 6.0, 14.5, -5.0, 16.0, 15.0),
                    Block.makeCuboidShape(-6.0, 6.0, 16.75, 22.0, 16.0, 17.25),
                    Block.makeCuboidShape(21.0, 6.0, 14.5, 22.0, 16.0, 15.0),
                    Block.makeCuboidShape(-5.0, 6.0, 14.5, 21.0, 6.75, 15.0),
                    Block.makeCuboidShape(-5.0, 15.25, 14.5, 21.0, 16.0, 15.0)
            ),
            Stream.of(
                    Block.makeCuboidShape(-6.0, 6.0, -0.75, 22.0, 16.0, 1.0),
                    Block.makeCuboidShape(21.0, 6.0, 1.0, 22.0, 16.0, 1.5),
                    Block.makeCuboidShape(-6.0, 6.0, -1.25, 22.0, 16.0, -0.75),
                    Block.makeCuboidShape(-6.0, 6.0, 1.0, -5.0, 16.0, 1.5),
                    Block.makeCuboidShape(-5.0, 6.0, 1.0, 21.0, 6.75, 1.5),
                    Block.makeCuboidShape(-5.0, 15.25, 1.0, 21.0, 16.0, 1.5)
            ),
            Stream.of(
                    Block.makeCuboidShape(-0.75, 6.0, -6.0, 1.0, 16.0, 22.0),
                    Block.makeCuboidShape(1.0, 6.0, -6.0, 1.5, 16.0, -5.0),
                    Block.makeCuboidShape(-1.25, 6.0, -6.0, -0.75, 16.0, 22.0),
                    Block.makeCuboidShape(1.0, 6.0, 21.0, 1.5, 16.0, 22.0),
                    Block.makeCuboidShape(1.0, 6.0, -5.0, 1.5, 6.75, 21.0),
                    Block.makeCuboidShape(1.0, 15.25, -5.0, 1.5, 16.0, 21.0)
            ),
            Stream.of(
                    Block.makeCuboidShape(15.0, 6.0, -6.0, 16.75, 16.0, 22.0),
                    Block.makeCuboidShape(14.5, 6.0, 21.0, 15.0, 16.0, 22.0),
                    Block.makeCuboidShape(16.75, 6.0, -6.0, 17.25, 16.0, 22.0),
                    Block.makeCuboidShape(14.5, 6.0, -6.0, 15.0, 16.0, -5.0),
                    Block.makeCuboidShape(14.5, 6.0, -5.0, 15.0, 6.75, 21.0),
                    Block.makeCuboidShape(14.5, 15.25, -5.0, 15.0, 16.0, 21.0)
            ));

    public StationNameplateBlock() {
        super(Block.Properties.create(Material.BARRIER)
                .hardnessAndResistance(2.0f, 3.0f)
                .sound(SoundType.METAL));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (player.getHeldItem(handIn).getItem() instanceof StationTunerItem && te instanceof StationNameplateTileEntity) {
            StationNameplateTileEntity SNte = (StationNameplateTileEntity) te;
            if (!SNte.checkStation())
                SNte.setStationPos(null);

            ItemStack stack = player.getHeldItem(handIn);
            if (!stack.hasTag()) {
                player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.invalid_item").applyTextStyle(TextFormatting.RED), true);
                return ActionResultType.SUCCESS;
            }

            CompoundNBT nbt = stack.getTag();
            if (UtilCastMagic.castItem(StationTunerItem.class, stack.getItem()).getStation(nbt, worldIn).isPresent()) {
                SNte.setStationPos(UtilStationConverter.toBlockPos(nbt, UtilStationConverter.POSITION));
                player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.station_bind")
                        .appendSibling(new StringTextComponent(SNte.getStation().getName()).applyTextStyle(TextFormatting.BOLD))
                        .applyTextStyle(TextFormatting.GREEN), true);
            } else {
                player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.invalid_item").applyTextStyle(TextFormatting.RED), true);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.getByDirection(state.get(FACING));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityHandler.STATION_NAMEPLATE.get().create();
    }
}
