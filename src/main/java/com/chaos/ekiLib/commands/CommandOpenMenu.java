package com.chaos.ekiLib.commands;

import com.chaos.ekiLib.screen.ScreenMenu;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class CommandOpenMenu {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("eki-menu")
                        .executes(
                                ctx -> {
                                    CommandSource src = ctx.getSource();

                                    Minecraft.getInstance()
                                            .displayGuiScreen(
                                                    new ScreenMenu(
                                                            src.getWorld().getDimensionKey(),
                                                            src.asPlayer()));
                                    return 1;
                                }));
    }
}
