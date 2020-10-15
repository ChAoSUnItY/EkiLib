package com.chaos.ekiLib.commands;

import com.chaos.ekiLib.api.EkiLibApi;
import com.chaos.ekiLib.objects.items.ItemStationTuner;
import com.chaos.ekiLib.screen.ScreenMenu;
import com.chaos.ekiLib.screen.ScreenStationSelection;
import com.chaos.ekiLib.station.data.Station;
import com.chaos.ekiLib.utils.util.UtilDimensionConverter;
import com.chaos.ekiLib.utils.util.UtilStationConverter;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class CommandEkiLib {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("eki")
                        .then(Commands.literal("menu")
                                .executes(ctx -> {
                                    CommandSource src = ctx.getSource();

                                    Minecraft.getInstance()
                                            .displayGuiScreen(
                                                    new ScreenMenu(
                                                            src.getWorld().getDimensionKey(),
                                                            src.asPlayer()));
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("bind")
                                .executes(ctx -> {
                                    CommandSource src = ctx.getSource();

                                    Minecraft.getInstance()
                                            .displayGuiScreen(
                                                    new ScreenStationSelection(
                                                            UtilDimensionConverter.dimensionKeyToID(src.getWorld().getDimensionKey()).getAsInt(),
                                                            src.asPlayer(),
                                                            true));

                                    return Command.SINGLE_SUCCESS;
                                }))
        );
    }
}
