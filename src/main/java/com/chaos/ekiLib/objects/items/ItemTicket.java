package com.chaos.ekiLib.objects.items;

import com.chaos.ekiLib.EkiLib;
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

public class ItemTicket extends  ItemBase implements ITicket {
    public ItemTicket() {
        super(new Item.Properties().maxStackSize(1));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("eki_lib.tooltip.balance", stack.getTag().getInt("Number")).mergeStyle(TextFormatting.GRAY));
    }

    @Override
    public boolean isValid(ItemStack stack) {
        return stack.getTag().getInt("used_times") < 3 && stack.getTag().getInt("used_times") >= 0;
    }

    @Override
    public void cutTicket(PlayerEntity player, ItemStack stack) {

    }

    @Override
    public boolean isSmartCard() {
        return false;
    }
}
