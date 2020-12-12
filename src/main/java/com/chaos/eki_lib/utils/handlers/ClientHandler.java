package com.chaos.eki_lib.utils.handlers;

import com.chaos.eki_lib.EkiLib;
import com.chaos.eki_lib.gui.screen.StationSelectionScreen;
import com.chaos.eki_lib.gui.screen.TicketVendorScreen;
import com.chaos.eki_lib.tileentity.renderer.StationNameplateTER;
import com.chaos.eki_lib.utils.util.UtilDimensionConverter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = EkiLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {
    public static KeyBinding[] keyBindings;

    static {
        keyBindings = new KeyBinding[1];

        keyBindings[0] = new KeyBinding("Open Eki Lib's Menu", GLFW.GLFW_KEY_L, "eki");

        for (KeyBinding key : keyBindings)
            ClientRegistry.registerKeyBinding(key);
    }

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(RegistryHandler.STATION_NAME_PLATE.get(), RenderType.getSolid());

        ClientRegistry.bindTileEntityRenderer(TileEntityHandler.STATION_NAMEPLATE.get(), StationNameplateTER::new);
        ScreenManager.registerFactory(ContainerHandler.TICKET_VENDOR_CONTAINER.get(), TicketVendorScreen::new);
        MinecraftForge.EVENT_BUS.addListener(ClientHandler::onClientTick);
    }

    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        if (keyBindings[0].isPressed()) {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(
                    new StationSelectionScreen(
                            null,
                            UtilDimensionConverter.dimensionKeyToID(mc.world.getDimension()).getAsInt(),
                            mc.getConnection().getWorld().getPlayerByUuid(mc.player.getUniqueID())));
        }
    }
}
