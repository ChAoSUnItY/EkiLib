package com.chaos.eki_lib.tileentity;

import com.chaos.eki_lib.api.EkiLibApi;
import com.chaos.eki_lib.gui.container.TicketVendorContainer;
import com.chaos.eki_lib.station.data.Station;
import com.chaos.eki_lib.utils.handlers.TileEntityHandler;
import com.chaos.eki_lib.utils.util.UtilDimensionConverter;
import com.chaos.eki_lib.utils.util.UtilStationConverter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class TicketVendorTileEntity extends LockableLootTileEntity {
    protected BlockPos stationPos;
    private NonNullList<ItemStack> vendorContent = NonNullList.withSize(1, ItemStack.EMPTY);
    private IItemHandlerModifiable items = createHandler();
    private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

    public TicketVendorTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TicketVendorTileEntity() {
        this(TileEntityHandler.TICKET_VENDOR.get());
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("eki_lib.container.example_chest");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new TicketVendorContainer(player, id, this);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.vendorContent;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.vendorContent = itemsIn;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (!this.checkLootAndWrite(compound))
            ItemStackHelper.saveAllItems(compound, this.vendorContent);
        if (hasStation())
            compound.putIntArray("stationPos", UtilStationConverter.toINTarray(this.stationPos));
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.vendorContent = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(nbt))
            ItemStackHelper.loadAllItems(nbt, this.vendorContent);
        if (nbt.contains("stationPos"))
            this.stationPos = UtilStationConverter.toBlockPos(nbt, "stationPos");
        else
            this.stationPos = null;
    }

    public Station getStation() {
        return EkiLibApi.getStationByPosition(this.stationPos, UtilDimensionConverter.getDimensionID(this.world)).get();
    }

    public void setStationPos(@Nullable BlockPos stationPos) {
        this.stationPos = stationPos;
        this.markDirty();
    }

    public boolean hasStation() {
        return this.stationPos != null;
    }

    public boolean checkStation() {
        if (hasStation())
            return EkiLibApi.hasStation(this.stationPos);
        return false;
    }

    public BlockPos getStationPos() {
        return this.stationPos;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.pos, 42, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(this.world.getBlockState(this.pos), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state, tag);
    }

    @Override
    public CompoundNBT getTileData() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemHandler.cast();
        return super.getCapability(cap, side);
    }

    private IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }

    @Override
    public void remove() {
        super.remove();
        if (itemHandler != null)
            itemHandler.invalidate();
    }
}
