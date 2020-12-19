package com.chaos.eki_lib.station;

import com.chaos.eki_lib.EkiLib;
import com.chaos.eki_lib.station.data.Station;
import com.chaos.eki_lib.utils.util.UtilStationConverter;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.List;
import java.util.function.Supplier;

public class StationWorldData extends WorldSavedData implements Supplier<StationWorldData> {
    public static final String LIST_NAME = "Station Save Data";
    public List<Station> stations = Lists.newArrayList();

    public StationWorldData() {
        super(EkiLib.MODID);
    }

    public StationWorldData(String name) {
        super(name);
    }

    @Override
    public void read(CompoundNBT nbt) {
        ListNBT list = nbt.getList(LIST_NAME, Constants.NBT.TAG_COMPOUND);
        list.forEach(compound -> stations.add(new StationWorldDataFixer((CompoundNBT) compound).tryFix().getStation()));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT list = new ListNBT();
        stations.forEach(station -> list.add(UtilStationConverter.toNBT(station)));
        compound.put(LIST_NAME, list);
        return compound;
    }

    public static StationWorldData forWorld(ServerWorld world) {
        DimensionSavedDataManager storage = world.getSavedData();
        Supplier<StationWorldData> sup = new StationWorldData();

        return storage.getOrCreate(sup, EkiLib.MODID);
    }

    @Override
    public StationWorldData get() {
        return this;
    }

    public static class StationWorldDataFixer {
        private final CompoundNBT nbt;

        public StationWorldDataFixer(CompoundNBT oldStationData) {
            nbt = oldStationData;
        }

        public StationWorldDataFixer tryFix() {
            if (nbt.contains("Dimension ID", Constants.NBT.TAG_INT)) {
                nbt.putString(UtilStationConverter.DIMENSION, convertIDtoResourceLocation(nbt.getInt("Dimension ID")).toString());
                nbt.remove("Dimension ID");
            }
            return this;
        }

        public Station getStation() {
            return UtilStationConverter.toStation(nbt);
        }

        public ResourceLocation convertIDtoResourceLocation(int id) {
            switch (id) {
                case -1:
                    return World.THE_NETHER.getLocation();
                case 0:
                    return World.OVERWORLD.getLocation();
                case 1:
                default:
                    return World.THE_END.getLocation();
            }
        }
    }
}
