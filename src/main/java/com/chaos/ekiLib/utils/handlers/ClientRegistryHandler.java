package com.chaos.ekiLib.utils.handlers;

import com.chaos.ekiLib.EkiLib;
import com.chaos.ekiLib.screen.ScreenStationSelection;
import com.chaos.ekiLib.utils.util.UtilDimensionConverter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = EkiLib.MODID, value = Dist.CLIENT)
public class ClientRegistryHandler {
    public static KeyBinding[] keyBindings;

    static {
        keyBindings = new KeyBinding[1];

        keyBindings[0] = new KeyBinding("Open Eki Lib's Menu", GLFW.GLFW_KEY_L, "eki");

        for (KeyBinding key : keyBindings)
            ClientRegistry.registerKeyBinding(key);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (keyBindings[0].isPressed()) {
            EkiLib.LOGGER.info("Pressed by fucker.");
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(
                    new ScreenStationSelection(
                            null,
                            UtilDimensionConverter.dimensionKeyToID(mc.world.getDimensionKey()).getAsInt(),
                            mc.getConnection().getWorld().getPlayerByUuid(mc.player.getUniqueID())));
        }
    }
}
