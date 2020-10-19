package com.chaos.ekiLib.objects.blocks;

import com.chaos.ekiLib.objects.blocks.base.HorizontalBaseBlock;
import com.chaos.ekiLib.objects.items.StationTunerItem;
import com.chaos.ekiLib.tileentity.TicketVendorTileEntity;
import com.chaos.ekiLib.utils.handlers.TileEntityHandler;
import com.chaos.ekiLib.utils.util.UtilStationConverter;
import com.chaos.ekiLib.utils.util.voxel_shapes.HorizontalVoxelShapes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class TicketVendorBlock extends HorizontalBaseBlock {
    public static final HorizontalVoxelShapes SHAPES = new HorizontalVoxelShapes(
            Stream.of(
                    Block.makeCuboidShape(4, 6, 2.5, 13, 7.25, 4.75),
                    Block.makeCuboidShape(4, 0.8934, 4.697039999999999, 13, 3.6434, 6.697039999999999),
                    Block.makeCuboidShape(7.5, 0.25, 2, 13, 1.5, 3),
                    Block.makeCuboidShape(7.5, 1.5, 1, 13, 5, 2),
                    Block.makeCuboidShape(4, 0.25, 7, 13, 16, 16),
                    Block.makeCuboidShape(4, 5, 0, 7.5, 5.75, 1.5),
                    Block.makeCuboidShape(4, 0.25, 0, 7.5, 5, 2),
                    Block.makeCuboidShape(3.5, 0.25, 0, 4, 5, 15),
                    Block.makeCuboidShape(13, 0.25, 0, 13.5, 5, 15),
                    Block.makeCuboidShape(0, 0.25, 1, 3.5, 5, 15),
                    Block.makeCuboidShape(13.5, 0.25, 1, 16, 5, 15),
                    Block.makeCuboidShape(0, 5, 4.75, 3.5, 9, 15),
                    Block.makeCuboidShape(13.5, 5, 4.75, 16, 9, 15),
                    Block.makeCuboidShape(0, 9, 6.5, 3.5, 13, 15),
                    Block.makeCuboidShape(13.5, 9, 6.5, 16, 13, 15),
                    Block.makeCuboidShape(4, 13, 6, 13, 16, 7),
                    Block.makeCuboidShape(0, 13, 7, 3.5, 16, 15),
                    Block.makeCuboidShape(13.5, 13, 7, 16, 16, 15),
                    Block.makeCuboidShape(3.5, 13, 6, 4, 16, 7),
                    Block.makeCuboidShape(13, 13, 6, 13.5, 16, 7),
                    Block.makeCuboidShape(13, 0.25, 7, 13.5, 16, 16),
                    Block.makeCuboidShape(3.5, 0.25, 7, 4, 16, 16),
                    Block.makeCuboidShape(0, 0.25, 15, 3.5, 16, 16),
                    Block.makeCuboidShape(13.5, 0.25, 15, 16, 16, 16),
                    Block.makeCuboidShape(0, 0, 0, 16, 0.25, 16),
                    Block.makeCuboidShape(13, 2.20881, 4.049759999999999, 13.5, 12.20881, 6.299759999999999),
                    Block.makeCuboidShape(3.5, 2.20881, 4.049759999999999, 4, 12.20881, 6.299759999999999),
                    Block.makeCuboidShape(13.5, 2.40015, 4.511699999999999, 16, 13.90015, 6.761699999999999),
                    Block.makeCuboidShape(0, 2.40015, 4.511699999999999, 3.5, 13.90015, 6.761699999999999),
                    Block.makeCuboidShape(4, 5.20881, 4.299759999999999, 13, 12.20881, 5.299759999999999)
            ),
            Stream.of(
                    Block.makeCuboidShape(3, 6, 11.25, 12, 7.25, 13.5),
                    Block.makeCuboidShape(3, 0.8934, 9.30296, 12, 3.6434, 11.30296),
                    Block.makeCuboidShape(3, 0.25, 13, 8.5, 1.5, 14),
                    Block.makeCuboidShape(3, 1.5, 14, 8.5, 5, 15),
                    Block.makeCuboidShape(3, 0.25, 0, 12, 16, 9),
                    Block.makeCuboidShape(8.5, 5, 14.5, 12, 5.75, 16),
                    Block.makeCuboidShape(8.5, 0.25, 14, 12, 5, 16),
                    Block.makeCuboidShape(12, 0.25, 1, 12.5, 5, 16),
                    Block.makeCuboidShape(2.5, 0.25, 1, 3, 5, 16),
                    Block.makeCuboidShape(12.5, 0.25, 1, 16, 5, 15),
                    Block.makeCuboidShape(0, 0.25, 1, 2.5, 5, 15),
                    Block.makeCuboidShape(12.5, 5, 1, 16, 9, 11.25),
                    Block.makeCuboidShape(0, 5, 1, 2.5, 9, 11.25),
                    Block.makeCuboidShape(12.5, 9, 1, 16, 13, 9.5),
                    Block.makeCuboidShape(0, 9, 1, 2.5, 13, 9.5),
                    Block.makeCuboidShape(3, 13, 9, 12, 16, 10),
                    Block.makeCuboidShape(12.5, 13, 1, 16, 16, 9),
                    Block.makeCuboidShape(0, 13, 1, 2.5, 16, 9),
                    Block.makeCuboidShape(12, 13, 9, 12.5, 16, 10),
                    Block.makeCuboidShape(2.5, 13, 9, 3, 16, 10),
                    Block.makeCuboidShape(2.5, 0.25, 0, 3, 16, 9),
                    Block.makeCuboidShape(12, 0.25, 0, 12.5, 16, 9),
                    Block.makeCuboidShape(12.5, 0.25, 0, 16, 16, 1),
                    Block.makeCuboidShape(0, 0.25, 0, 2.5, 16, 1),
                    Block.makeCuboidShape(0, 0, 0, 16, 0.25, 16),
                    Block.makeCuboidShape(2.5, 2.20881, 9.70024, 3, 12.20881, 11.95024),
                    Block.makeCuboidShape(12, 2.20881, 9.70024, 12.5, 12.20881, 11.95024),
                    Block.makeCuboidShape(0, 2.40015, 9.2383, 2.5, 13.90015, 11.4883),
                    Block.makeCuboidShape(12.5, 2.40015, 9.2383, 16, 13.90015, 11.4883),
                    Block.makeCuboidShape(3, 5.20881, 10.70024, 12, 12.20881, 11.70024)
            ),
            Stream.of(
                    Block.makeCuboidShape(11.25, 6, 4, 13.5, 7.25, 13),
                    Block.makeCuboidShape(9.30296, 0.8934, 4, 11.30296, 3.6434, 13),
                    Block.makeCuboidShape(13, 0.25, 7.5, 14, 1.5, 13),
                    Block.makeCuboidShape(14, 1.5, 7.5, 15, 5, 13),
                    Block.makeCuboidShape(0, 0.25, 4, 9, 16, 13),
                    Block.makeCuboidShape(14.5, 5, 4, 16, 5.75, 7.5),
                    Block.makeCuboidShape(14, 0.25, 4, 16, 5, 7.5),
                    Block.makeCuboidShape(1, 0.25, 3.5, 16, 5, 4),
                    Block.makeCuboidShape(1, 0.25, 13, 16, 5, 13.5),
                    Block.makeCuboidShape(1, 0.25, 0, 15, 5, 3.5),
                    Block.makeCuboidShape(1, 0.25, 13.5, 15, 5, 16),
                    Block.makeCuboidShape(1, 5, 0, 11.25, 9, 3.5),
                    Block.makeCuboidShape(1, 5, 13.5, 11.25, 9, 16),
                    Block.makeCuboidShape(1, 9, 0, 9.5, 13, 3.5),
                    Block.makeCuboidShape(1, 9, 13.5, 9.5, 13, 16),
                    Block.makeCuboidShape(9, 13, 4, 10, 16, 13),
                    Block.makeCuboidShape(1, 13, 0, 9, 16, 3.5),
                    Block.makeCuboidShape(1, 13, 13.5, 9, 16, 16),
                    Block.makeCuboidShape(9, 13, 3.5, 10, 16, 4),
                    Block.makeCuboidShape(9, 13, 13, 10, 16, 13.5),
                    Block.makeCuboidShape(0, 0.25, 13, 9, 16, 13.5),
                    Block.makeCuboidShape(0, 0.25, 3.5, 9, 16, 4),
                    Block.makeCuboidShape(0, 0.25, 0, 1, 16, 3.5),
                    Block.makeCuboidShape(0, 0.25, 13.5, 1, 16, 16),
                    Block.makeCuboidShape(0, 0, 0, 16, 0.25, 16),
                    Block.makeCuboidShape(9.70024, 2.20881, 13, 11.95024, 12.20881, 13.5),
                    Block.makeCuboidShape(9.70024, 2.20881, 3.5, 11.95024, 12.20881, 4),
                    Block.makeCuboidShape(9.2383, 2.40015, 13.5, 11.4883, 13.90015, 16),
                    Block.makeCuboidShape(9.2383, 2.40015, 0, 11.4883, 13.90015, 3.5),
                    Block.makeCuboidShape(10.70024, 5.20881, 4, 11.70024, 12.20881, 13)
            ),
            Stream.of(
                    Block.makeCuboidShape(2.5, 6, 3, 4.75, 7.25, 12),
                    Block.makeCuboidShape(4.697039999999999, 0.8934, 3, 6.697039999999999, 3.6434, 12),
                    Block.makeCuboidShape(2, 0.25, 3, 3, 1.5, 8.5),
                    Block.makeCuboidShape(1, 1.5, 3, 2, 5, 8.5),
                    Block.makeCuboidShape(7, 0.25, 3, 16, 16, 12),
                    Block.makeCuboidShape(0, 5, 8.5, 1.5, 5.75, 12),
                    Block.makeCuboidShape(0, 0.25, 8.5, 2, 5, 12),
                    Block.makeCuboidShape(0, 0.25, 12, 15, 5, 12.5),
                    Block.makeCuboidShape(0, 0.25, 2.5, 15, 5, 3),
                    Block.makeCuboidShape(1, 0.25, 12.5, 15, 5, 16),
                    Block.makeCuboidShape(1, 0.25, 0, 15, 5, 2.5),
                    Block.makeCuboidShape(4.75, 5, 12.5, 15, 9, 16),
                    Block.makeCuboidShape(4.75, 5, 0, 15, 9, 2.5),
                    Block.makeCuboidShape(6.5, 9, 12.5, 15, 13, 16),
                    Block.makeCuboidShape(6.5, 9, 0, 15, 13, 2.5),
                    Block.makeCuboidShape(6, 13, 3, 7, 16, 12),
                    Block.makeCuboidShape(7, 13, 12.5, 15, 16, 16),
                    Block.makeCuboidShape(7, 13, 0, 15, 16, 2.5),
                    Block.makeCuboidShape(6, 13, 12, 7, 16, 12.5),
                    Block.makeCuboidShape(6, 13, 2.5, 7, 16, 3),
                    Block.makeCuboidShape(7, 0.25, 2.5, 16, 16, 3),
                    Block.makeCuboidShape(7, 0.25, 12, 16, 16, 12.5),
                    Block.makeCuboidShape(15, 0.25, 12.5, 16, 16, 16),
                    Block.makeCuboidShape(15, 0.25, 0, 16, 16, 2.5),
                    Block.makeCuboidShape(0, 0, 0, 16, 0.25, 16),
                    Block.makeCuboidShape(4.049759999999999, 2.20881, 2.5, 6.299759999999999, 12.20881, 3),
                    Block.makeCuboidShape(4.049759999999999, 2.20881, 12, 6.299759999999999, 12.20881, 12.5),
                    Block.makeCuboidShape(4.511699999999999, 2.40015, 0, 6.761699999999999, 13.90015, 2.5),
                    Block.makeCuboidShape(4.511699999999999, 2.40015, 12.5, 6.761699999999999, 13.90015, 16),
                    Block.makeCuboidShape(4.299759999999999, 5.20881, 3, 5.299759999999999, 12.20881, 12)
            ));

    public TicketVendorBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(handIn);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TicketVendorTileEntity) {
            TicketVendorTileEntity TVte = (TicketVendorTileEntity) te;
            if (stack.getItem() instanceof StationTunerItem && stack.hasTag()) {
                if (UtilStationConverter.hasStationInfo(stack.getTag())) {
                    TVte.setStation(UtilStationConverter.toStation(stack.getTag()));
                    player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.station_bind")
                            .append(new StringTextComponent(stack.getTag().getString(UtilStationConverter.NAME)).mergeStyle(TextFormatting.BOLD))
                            .mergeStyle(TextFormatting.GREEN), true);
                } else {
                    player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.invalid_item").mergeStyle(TextFormatting.RED), true);
                }
                return ActionResultType.SUCCESS;
            }

            if (!worldIn.isRemote) {
                NetworkHooks.openGui((ServerPlayerEntity) player, TVte, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TicketVendorTileEntity)
                InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) te);
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case SOUTH:
                return SHAPES.getSouth();
            case EAST:
                return SHAPES.getEast();
            case WEST:
                return SHAPES.getWest();
            case NORTH:
            default:
                return SHAPES.getNorth();
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityHandler.TICKET_VENDOR.get().create();
    }
}
