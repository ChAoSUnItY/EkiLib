package com.chaos.ekiLib.utils.handlers;

import com.chaos.ekiLib.EkiLib;
import com.chaos.ekiLib.gui.container.TicketVendorContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerHandler {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, EkiLib.MODID);

    public static final RegistryObject<ContainerType<TicketVendorContainer>> TICKET_VENDOR_CONTAINER = CONTAINER_TYPES.register("ticket_vendor_container",
            () -> IForgeContainerType.create(TicketVendorContainer::new));
}
