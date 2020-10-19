package com.chaos.ekiLib.gui.container;

import com.chaos.ekiLib.data.Tags;
import com.chaos.ekiLib.tileentity.TicketVendorTileEntity;
import com.chaos.ekiLib.utils.handlers.ContainerHandler;
import com.chaos.ekiLib.utils.handlers.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class TicketVendorContainer extends Container {
    private final TicketVendorSlot slot;
    private final TicketVendorTileEntity TVte;

    public TicketVendorContainer(final IInventory inventory, final int id, final TicketVendorTileEntity TVte) {
        super(ContainerHandler.TICKET_VENDOR_CONTAINER.get(), id);
        this.TVte = TVte;
        this.slot = new TicketVendorSlot(TVte, 0, 140, 50);
        this.addSlot(this.slot);

        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 142));
        }
    }

    public TicketVendorContainer(final int ID, final PlayerInventory inv, final PacketBuffer buf) {
        this(inv, ID, getTicketVendorTileEntity(inv, buf));
    }

    public TicketVendorTileEntity getVendor() {
        return this.TVte;
    }

    private static TicketVendorTileEntity getTicketVendorTileEntity(final PlayerInventory inv, final PacketBuffer buf) {
        Objects.requireNonNull(inv, "PlayerInventory cannot be null");
        Objects.requireNonNull(buf, "PacketBuffer cannot be null");
        final TileEntity te = inv.player.world.getTileEntity(buf.readBlockPos());
        if (te instanceof TicketVendorTileEntity)
            return (TicketVendorTileEntity) te;
        throw new IllegalStateException("TileEntity is not correct at this moment. Required : " + TicketVendorTileEntity.class.getTypeName() + ", but GOT : " + te.getClass().getTypeName());
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (this.mergeItemStack(itemstack1, 0, 1, false)) { //Forge Fix Shift Clicking in beacons with stacks larger then 1.
                return ItemStack.EMPTY;
            } else if (index >= 1 && index < 28) {
                if (!this.mergeItemStack(itemstack1, 28, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 28 && index < 37) {
                if (!this.mergeItemStack(itemstack1, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 1, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.DUMMY, playerIn, RegistryHandler.TICKET_VENDOR.get());
    }

    @OnlyIn(Dist.CLIENT)
    public class TicketVendorSlot extends Slot {
        public TicketVendorSlot(IInventory inv, int index, int x, int y) {
            super(inv, index, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem().isIn(Tags.ticketTag);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }
    }
}
