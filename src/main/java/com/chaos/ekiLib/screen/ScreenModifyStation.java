package com.chaos.ekiLib.screen;

import com.chaos.ekiLib.api.EkiLibApi;
import com.chaos.ekiLib.station.data.EnumStationLevel;
import com.chaos.ekiLib.station.data.Station;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Optional;

public class ScreenModifyStation extends ScreenBase {
    private TextFieldWidget textFieldStationName;
    private TextFieldWidget textFieldStationOperator;
    private Button buttonCreate;
    private EnumStationLevel stationLevel;
    private Optional<Station> station = Optional.empty();
    private BlockPos pos;

    public ScreenModifyStation(int dimensionType, PlayerEntity player) {
        super(new TranslationTextComponent("eki.screen.modify_station"), dimensionType, player);
        this.stationLevel = EnumStationLevel.THRID;
        this.pos = player.getPosition();
    }

    public ScreenModifyStation(int dimensionType, PlayerEntity player, Station station) {
        this(dimensionType, player);
        this.station = Optional.of(station);
    }

    @Override
    public void tick() {
        super.tick();
        this.textFieldStationName.tick();
        this.textFieldStationOperator.tick();
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.addButton(new Button(this.width / 2 - 110, this.height / 2 + 50, 100, 20, new StringTextComponent("Back"),
                v -> minecraft.displayGuiScreen(new ScreenMenu(this.dimID, this.player))));
        this.buttonCreate = this.addButton(new Button(this.width / 2 + 10, this.height / 2 + 50, 100, 20, new StringTextComponent("Create"),
                v -> EkiLibApi.addStations(
                        new Station(this.textFieldStationName.getText(), this.pos, this.textFieldStationOperator.getText(), this.stationLevel, this.dimID))));
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
        this.textFieldStationOperator =
                new TextFieldWidget(
                        font, this.width / 2 - 100, this.height / 2 - 50, 200, 20, new StringTextComponent(""));
        this.textFieldStationOperator.setResponder(this::buttonRespond);
        this.children.add(this.textFieldStationOperator);

        this.station.ifPresent(sta -> {
            this.textFieldStationName.setText(sta.getName());
            this.pos = sta.getPosition();
            this.textFieldStationOperator.setText(sta.getOperator());
            this.stationLevel = sta.getLevel();
            this.dimID = sta.getDimensionID();
        });

        this.buttonRespond(this.textFieldStationName.getText());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.textFieldStationName.render(matrixStack, mouseX, mouseY, partialTicks);
        this.textFieldStationOperator.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, this.height / 2 - 130, 16777215);
        drawCenteredString(matrixStack, this.font, "Name", this.width / 2, this.height / 2 - 110, 16777215);
        drawString(matrixStack, this.font, String.format("Position: (%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ()), this.width / 2 + 110, this.height / 2 - 110, 16777215);
        drawCenteredString(matrixStack, this.font, "Operator", this.width / 2, this.height / 2 - 60, 16777215);
        drawString(matrixStack, this.font, String.format("Station Level: %s", this.stationLevel.getFormattedName()), this.width / 2 + 110, this.height / 2 - 90, 16777215);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.textFieldStationName.mouseClicked(mouseX, mouseY, button);
        this.textFieldStationOperator.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void buttonRespond(String s) {
        this.buttonCreate.active = !this.textFieldStationName.getText().isEmpty();
    }
}
