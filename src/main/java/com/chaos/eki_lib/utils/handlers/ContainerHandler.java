package com.chaos.eki_lib.utils.handlers;

import com.chaos.eki_lib.EkiLib;
import com.chaos.eki_lib.gui.container.TicketVendorContainer;
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
