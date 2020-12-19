package com.chaos.eki_lib.objects.items;

import com.chaos.eki_lib.api.EkiLibApi;
import com.chaos.eki_lib.station.data.Station;
import com.chaos.eki_lib.utils.util.UtilDimensionHelper;
import com.chaos.eki_lib.utils.util.UtilStationConverter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class StationTunerItem extends BaseItem {
    public StationTunerItem() {
        super(new Item.Properties().setNoRepair().maxStackSize(1));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT compound = stack.getTag();
        Optional<Station> station = getStation(compound, worldIn);
        if (!station.isPresent())
            tooltip.add(new TranslationTextComponent(
                    getTranslationKey() + ".tooltip",
                    null,
                    null,
                    null,
                    null
            ));
        else {
            tooltip.add(new TranslationTextComponent(
                    getTranslationKey() + ".tooltip",
                    spreadArgs(
                            EkiLibApi.getStationByPosition(
                                    UtilStationConverter.toBlockPos(compound.getIntArray(UtilStationConverter.POSITION)),
                                    UtilDimensionHelper.getDimension(worldIn)
                            ).get().getName(),
                            compound.getIntArray(UtilStationConverter.POSITION)
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

    public Optional<Station> getStation(CompoundNBT compound, World world) {
        if (compound == null || !compound.contains(UtilStationConverter.POSITION))
            return Optional.empty();
        return EkiLibApi.getStationByPosition(
                UtilStationConverter.toBlockPos(compound.getIntArray(UtilStationConverter.POSITION)),
                UtilDimensionHelper.getDimension(world)
        );
    }
}
