package com.chaos.ekiLib;

import com.chaos.ekiLib.commands.CommandEkiLib;
import com.chaos.ekiLib.station.StationWorldData;
import com.chaos.ekiLib.station.data.Station;
import com.chaos.ekiLib.utils.handlers.PacketHandler;
import com.chaos.ekiLib.utils.handlers.RegistryHandler;
import com.chaos.ekiLib.utils.handlers.StationHandler;
import com.chaos.ekiLib.utils.network.PacketInitStationHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
    public static final String VERSION = "1.16.3-1.0.0";
    public static final Logger LOGGER = LogManager.getLogger("Eki Lib");

    public EkiLib() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        RegistryHandler.ITEMS.register(bus);

        bus.addListener(this::setup);
        bus.addListener(this::enqueueIMC);
        bus.addListener(this::processIMC);
        bus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldLoaded);
        MinecraftForge.EVENT_BUS.addListener(this::onWorldSave);
        MinecraftForge.EVENT_BUS.addListener(this::onLogging);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    public void onWorldLoaded(WorldEvent.Load event) {
        if (!event.getWorld().isRemote() && event.getWorld() instanceof ServerWorld) {
            StationWorldData saver = StationWorldData.forWorld((ServerWorld) event.getWorld());

            for (Station station : saver.stations)
                LOGGER.info(station.getName() + " " + station.getFormmatedPosition());

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

    public void onLogging(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer().isServerWorld()) {
            PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketInitStationHandler(StationHandler.INSTANCE.getStations()));
        }
    }

    public void onServerStarting(FMLServerStartingEvent event) {
        CommandEkiLib.register(event.getServer().getCommandManager().getDispatcher());
    }

    public static final ItemGroup EkiLibGroup = new ItemGroup("eki_lib_tab") {
        @Override
        public ItemStack createIcon() {
            return RegistryHandler.STATION_TUNER.get().getDefaultInstance();
        }
    };
}
