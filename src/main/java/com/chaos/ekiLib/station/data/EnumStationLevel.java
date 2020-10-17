package com.chaos.ekiLib.station.data;

import net.minecraft.util.text.TranslationTextComponent;

public enum EnumStationLevel {
    SPECIAL("special_class"), FIRST("first_class"), SECOND("second_class"), THRID("third_class"),
    SIMPLE("simple"), STAFFLESS("staffless"), SIGNAL("signal"), NON("non");

    private String key;

    public static final EnumStationLevel[] values = values();

    EnumStationLevel(String key) {
        this.key = key;
    }

    public String getKey() {
        return "eki_lib.station.level." + key;
    }

    public static TranslationTextComponent toTranslated(EnumStationLevel levelIn) {
        return new TranslationTextComponent(levelIn.getKey());
    }
}
