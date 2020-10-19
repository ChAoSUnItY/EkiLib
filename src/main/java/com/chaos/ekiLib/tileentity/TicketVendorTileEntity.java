package com.chaos.ekiLib.tileentity;

import com.chaos.ekiLib.api.EkiLibApi;
import com.chaos.ekiLib.gui.container.TicketVendorContainer;
import com.chaos.ekiLib.station.data.Station;
import com.chaos.ekiLib.utils.handlers.TileEntityHandler;
import com.chaos.ekiLib.utils.util.UtilDimensionConverter;
import com.chaos.ekiLib.utils.util.UtilStationConverter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class TicketVendorTileEntity extends LockableLootTileEntity {
    protected Station station;
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
    protected void setItems(NonNullList<ItemStack> itemsIn) {
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
        compound.put("targetStation", this.station != null ? UtilStationConverter.toNBT(this.station) : new CompoundNBT());
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.vendorContent = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(nbt))
            ItemStackHelper.loadAllItems(nbt, this.vendorContent);
        if (nbt.getCompound("targetStation").size() != 0)
            this.station = UtilStationConverter.toStation(nbt.getCompound("targetStation"));
        else
            this.station = null;
    }

    public void setStation(@Nullable Station station) {
        this.station = station;
        this.markDirty();
    }

    public boolean hasStation() {
        return this.station != null;
    }

    public boolean checkStation() {
        return EkiLibApi.getStationList(UtilDimensionConverter.dimensionKeyToID(this.world.getDimensionKey()).getAsInt()).contains(this.station);
    }

    public Station getStation() {
        return this.station;
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
