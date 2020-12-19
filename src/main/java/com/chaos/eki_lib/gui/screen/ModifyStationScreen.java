package com.chaos.eki_lib.gui.screen;

import com.chaos.eki_lib.api.EkiLibApi;
import com.chaos.eki_lib.station.data.EnumStationLevel;
import com.chaos.eki_lib.station.data.Station;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ModifyStationScreen extends BaseScreen {
    private TextFieldWidget textFieldStationName;
    private Button buttonCreate;
    private EnumStationLevel stationLevel;
    private Optional<Station> station = Optional.empty();
    private BlockPos pos;

    public ModifyStationScreen(Screen previous, ResourceLocation dimension, PlayerEntity player) {
        super(new TranslationTextComponent("eki_lib.screen.modify_station"), previous, dimension, player);
        this.stationLevel = EnumStationLevel.THRID;
        this.pos = player.getPosition();
    }

    public ModifyStationScreen(Screen previous, ResourceLocation dimension, PlayerEntity player, Station station) {
        this(previous, dimension, player);
        this.station = Optional.of(station);
    }

    @Override
    public void tick() {
        super.tick();
        this.textFieldStationName.tick();
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.addButton(new Button(this.width / 2 - 110, this.height / 2 + 50, 100, 20, new TranslationTextComponent("eki_lib.screen.back"),
                v -> minecraft.displayGuiScreen(this.previous)));
        this.buttonCreate = this.addButton(new Button(this.width / 2 + 10, this.height / 2 + 50, 100, 20, new TranslationTextComponent("eki_lib.screen.create_modify"),
                v -> {
                    Station station = new Station(this.textFieldStationName.getText(), this.pos, this.stationLevel, this.dimension);
                    if (this.station.isPresent())
                        EkiLibApi.replaceStations(station);
                    else
                        EkiLibApi.addStations(station);
                    EkiLibApi.markDirty();
                    IFormattableTextComponent boldPart = new StringTextComponent(this.textFieldStationName.getText()).mergeStyle(TextFormatting.BOLD);
                    this.player.sendStatusMessage(new TranslationTextComponent("eki_lib.screen.create_accept").append(boldPart), true);
                    this.closeScreen();
                }));
        this.addButton(new Button(this.width / 2 + 110, this.height / 2 - 80, 20, 20, new StringTextComponent("<"),
                v -> this.stationLevel = EnumStationLevel.values[(this.stationLevel.ordinal() - 1 < 0 ? EnumStationLevel.values.length - 1 : this.stationLevel.ordinal() - 1) % EnumStationLevel.values.length]));
        this.addButton(new Button(this.width / 2 + 130, this.height / 2 - 80, 20, 20, new StringTextComponent(">"),
                v -> this.stationLevel = EnumStationLevel.values[(this.stationLevel.ordinal() + 1) % EnumStationLevel.values.length]));
        super.init();
        this.textFieldStationName =
                new TextFieldWidget(
                        font, this.width / 2 - 100, this.height / 2 - 100, 200, 20, new StringTextComponent(""));
        this.textFieldStationName.setResponder(this::buttonRespond);
        this.children.add(this.textFieldStationName);

        this.station.ifPresent(sta -> {
            this.textFieldStationName.setText(sta.getName());
            this.pos = sta.getPosition();
            this.stationLevel = sta.getLevel();
            this.dimension = sta.getDimension();
        });

        this.buttonRespond(this.textFieldStationName.getText());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.textFieldStationName.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, this.height / 2 - 130, 16777215);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("eki_lib.station.name"), this.width / 2, this.height / 2 - 110, 16777215);
        drawString(matrixStack, this.font, new TranslationTextComponent("eki_lib.station.pos", pos.getX(), pos.getY(), pos.getZ()), this.width / 2 + 110, this.height / 2 - 110, 16777215);
        drawString(matrixStack, this.font, new TranslationTextComponent("eki_lib.station.level", EnumStationLevel.toTranslated(this.stationLevel)), this.width / 2 + 110, this.height / 2 - 90, 16777215);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.textFieldStationName.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        super.onClose();
    }

    private void buttonRespond(String s) {
        this.buttonCreate.active = !this.textFieldStationName.getText().isEmpty();
    }
}
