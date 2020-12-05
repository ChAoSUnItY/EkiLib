package com.chaos.eki_lib.objects.items;

import com.chaos.eki_lib.api.EkiLibApi;
import com.chaos.eki_lib.utils.util.UtilDimensionConverter;
import com.chaos.eki_lib.utils.util.UtilStationConverter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StationTunerItem extends BaseItem {
    public StationTunerItem() {
        super(new Item.Properties().setNoRepair().maxStackSize(1));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!stack.hasTag())
            tooltip.add(new TranslationTextComponent(
                    getTranslationKey() + ".tooltip",
                    null,
                    null,
                    null,
                    null
            ));
        else if (stack.getTag().contains(UtilStationConverter.POSITION)) {
            int[] bindStationPos = stack.getTag().getIntArray(UtilStationConverter.POSITION);
            tooltip.add(new TranslationTextComponent(
                    getTranslationKey() + ".tooltip",
                    spreadArgs(
                            EkiLibApi.getStationByPosition(
                                    UtilStationConverter.toBlockPos(stack.getTag().getIntArray(UtilStationConverter.POSITION)),
                                    UtilDimensionConverter.getDimensionID(worldIn)
                            ).get().getName(),
                            stack.getTag().getIntArray(UtilStationConverter.POSITION)
                    )
            ).mergeStyle(TextFormatting.GRAY));
        }
    }

    private Object[] spreadArgs(Object... obj) {
        Object[] objArr = new Object[4];
        objArr[0] = obj[0];
        for (int i = 1; i < 4; i++)
            objArr[i] = ((int[]) obj[1])[i - 1];
        return objArr;
    }
}
