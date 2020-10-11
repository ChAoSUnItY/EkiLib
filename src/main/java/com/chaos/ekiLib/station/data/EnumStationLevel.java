package com.chaos.ekiLib.station.data;

import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EnumStationLevel {
    SPECIAL("special_class"), FIRST("first_class"), SECOND("second_class"), THRID("third_class"),
    SIMPLE("simple"), STAFFLESS("staffless"), SIGNAL("signal"), NON("non");

    String key;

    public static final EnumStationLevel[] values = values();
    public static final List<TranslationTextComponent> formattedNames = Stream.of(values)
            .map(v -> new TranslationTextComponent(v.key))
            .collect(Collectors.toList());

    EnumStationLevel(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public TextComponent getRawName() {
        return formattedNames.get(ordinal());
    }

    public String getFormattedName() {
        return formattedNames.get(ordinal()).getString();
    }
}
