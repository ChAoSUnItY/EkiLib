package com.chaos.eki_lib.objects.blocks;

import com.chaos.eki_lib.api.EkiLibApi;
import com.chaos.eki_lib.objects.blocks.base.HorizontalBaseBlock;
import com.chaos.eki_lib.objects.items.StationTunerItem;
import com.chaos.eki_lib.objects.items.TicketItem;
import com.chaos.eki_lib.tileentity.TicketGateTileEntity;
import com.chaos.eki_lib.utils.handlers.RegistryHandler;
import com.chaos.eki_lib.utils.handlers.TileEntityHandler;
import com.chaos.eki_lib.utils.util.UtilDimensionConverter;
import com.chaos.eki_lib.utils.util.UtilDistanceHelper;
import com.chaos.eki_lib.utils.util.UtilStationConverter;
import com.chaos.eki_lib.utils.util.voxel_shapes.HorizontalVoxelShapes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;

public class TicketGateBlock extends HorizontalBaseBlock {
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final VoxelShape COLLISION_SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 24, 16);
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

    public TicketGateBlock() {
        super(AbstractBlock.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
        this.setDefaultState(this.getDefaultState().with(OPEN, false));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(handIn);
        TileEntity te = worldIn.getTileEntity(pos);
        if (!worldIn.isRemote && te instanceof TicketGateTileEntity) {
            TicketGateTileEntity TGte = (TicketGateTileEntity) te;
            if (!TGte.checkStation())
                TGte.setStationPos(null);

            if (stack.getItem() instanceof StationTunerItem) {
                if (!stack.hasTag()) {
                    player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.invalid_item").mergeStyle(TextFormatting.RED), true);
                    return ActionResultType.SUCCESS;
                }

                CompoundNBT nbt = stack.getTag();
                if (nbt.contains(UtilStationConverter.POSITION)) {
                    TGte.setStationPos(UtilStationConverter.toBlockPos(nbt, UtilStationConverter.POSITION));
                    TGte.markDirty();
                    player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.station_bind")
                            .append(new StringTextComponent(TGte.getStation().getName()).mergeStyle(TextFormatting.BOLD))
                            .mergeStyle(TextFormatting.GREEN), true);
                } else {
                    player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.invalid_item").mergeStyle(TextFormatting.RED), true);
                }
                return ActionResultType.SUCCESS;
            } else if (!state.get(OPEN) && stack.getItem() instanceof TicketItem) {
                TicketItem ticket = (TicketItem) stack.getItem();
                if (!stack.hasTag()) {
                    player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.invalid_item").mergeStyle(TextFormatting.RED), true);
                    return ActionResultType.PASS;
                }

                if (stack.getTag().contains("value")) {
                    if (hit.getFace() == Direction.UP || hit.getFace() == state.get(FACING)) {
                        CompoundNBT nbt = stack.getTag();
                        if (nbt.contains("startPos")) {
                            BlockPos fromPos = UtilStationConverter.toBlockPos(nbt.getIntArray("startPos"));
                            double requiredPrice = UtilDistanceHelper.calculatePrice(
                                    fromPos, TGte.hasStationPos() ? TGte.getStationPos() : pos);
                            double value = nbt.getDouble("value");
                            if (value >= requiredPrice) {
                                switch (ticket.getType()) {
                                    case 2:
                                        stack.getTag().putDouble("value", nbt.getDouble("value") - requiredPrice);
                                        stack.getTag().remove("startPos");
                                        open(player, worldIn, pos, state);
                                        break;
                                    case 1:
                                        stack.shrink(1);
                                    default:
                                        open(player, worldIn, pos, state);
                                }
                                ITextComponent
                                        subtext = new TranslationTextComponent("eki_lib.message.unknown_station").mergeStyle(TextFormatting.ITALIC, TextFormatting.GRAY),
                                        from = nbt.contains("fromStation") ? new StringTextComponent(nbt.getString("fromStation")).mergeStyle(TextFormatting.BOLD) : new TranslationTextComponent("eki_lib.message.unknown_station").mergeStyle(TextFormatting.ITALIC, TextFormatting.GRAY);
                                if (TGte.hasStationPos())
                                    player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.exit", new StringTextComponent(TGte.getStation().getName()).mergeStyle(TextFormatting.BOLD), from).mergeStyle(TextFormatting.GREEN), true);
                                else
                                    player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.exit", subtext, from).mergeStyle(TextFormatting.GREEN), true);

                                open(player, worldIn, pos, state);
                            } else {
                                player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.balance_shortage", requiredPrice, value), true);
                            }
                        } else {
                            nbt.putIntArray("startPos", UtilStationConverter.toINTarray(TGte.hasStationPos() ? TGte.getStationPos() : pos));
                            if (TGte.hasStationPos())
                                nbt.putString("fromStation", EkiLibApi.getStationByPosition(TGte.getStationPos(), UtilDimensionConverter.getDimensionID(worldIn)).get().getName());
                            if (ticket.getType() == 2) {
                                stack.setTag(nbt);
                            } else {
                                ItemStack cutTicket = new ItemStack(RegistryHandler.CUT_TICKET.get(), 1);
                                cutTicket.setTag(nbt);
                                player.setHeldItem(handIn, cutTicket);
                            }
                            open(player, worldIn, pos, state);
                        }
                        return ActionResultType.SUCCESS;
                    }
                } else {
                    player.sendStatusMessage(new TranslationTextComponent("eki_lib.message.invalid_item").mergeStyle(TextFormatting.RED), true);
                }
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(OPEN)) {
            worldIn.setBlockState(pos, state.with(OPEN, false));
            worldIn.notifyNeighborsOfStateChange(pos, this);
            worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1F, 1F);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(OPEN))
            return VoxelShapes.empty();
        return COLLISION_SHAPE;
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

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityHandler.TICKET_GATE.get().create();
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock())
            super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    public void open(PlayerEntity playerIn, World worldIn, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, state.with(OPEN, true));
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, 5 * 20);
        worldIn.playSound(null, pos, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1, 1);
    }
}
