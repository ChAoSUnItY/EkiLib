package com.chaos.eki_lib;

import com.chaos.eki_lib.station.StationWorldData;
import com.chaos.eki_lib.utils.handlers.*;
import com.chaos.eki_lib.utils.network.PacketInitStationHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(EkiLib.MODID)
public class EkiLib {
    public static final String MODID = "eki_lib";
    public static final Logger LOGGER = LogManager.getLogger("Eki Lib");

    public EkiLib() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        RegistryHandler.ITEMS.register(bus);
        RegistryHandler.BLOCKS.register(bus);
        TileEntityHandler.TILE_ENTITY_TYPES.register(bus);
        ContainerHandler.CONTAINER_TYPES.register(bus);

        bus.addListener(this::setup);
        bus.addListener(this::enqueueIMC);
        bus.addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldLoaded);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldSave);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldUnload);
        MinecraftForge.EVENT_BUS.addListener(this::onLogging);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    public void onWorldLoaded(WorldEvent.Load event) {
        if (!event.getWorld().isRemote() && event.getWorld() instanceof ServerWorld) {
            StationWorldData saver = StationWorldData.forWorld((ServerWorld) event.getWorld());

            StationHandler.INSTANCE.init(saver.stations);
        }
    }

    public void onWorldSave(WorldEvent.Save event) {
        if (!event.getWorld().isRemote() && event.getWorld() instanceof ServerWorld) {
            StationWorldData saver = StationWorldData.forWorld((ServerWorld) event.getWorld());
            saver.stations = StationHandler.INSTANCE.getStations();
            saver.markDirty();
        }
    }

    public void onWorldUnload(WorldEvent.Unload event) {
        if (!event.getWorld().isRemote() && event.getWorld() instanceof ServerWorld) {
            StationWorldData saver = StationWorldData.forWorld((ServerWorld) event.getWorld());
            saver.stations = StationHandler.INSTANCE.getStations();
            saver.markDirty();
        }
    }

    public void onLogging(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer().isServerWorld()) {
            PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketInitStationHandler(StationHandler.INSTANCE.getStations()));
        }
    }

    public void onServerStarting(FMLServerStartingEvent event) {
    }

    public static final ItemGroup EkiLibGroup = new ItemGroup("eki_lib_tab") {
        @Override
        public ItemStack createIcon() {
            return RegistryHandler.STATION_TUNER.get().getDefaultInstance();
        }
    };
}
