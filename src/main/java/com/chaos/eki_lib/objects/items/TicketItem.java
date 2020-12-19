package com.chaos.eki_lib.objects.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TicketItem extends BaseItem {
    protected final int type;

    public TicketItem(int type) {
        super(new Item.Properties().maxStackSize(1).maxDamage(1));
        this.type = type;
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(
                "eki_lib.tooltip.balance",
                stack.getTag().getDouble("value")
        ).mergeStyle(TextFormatting.GRAY));
    }

    public int getType() {
        return type;
    }
}
