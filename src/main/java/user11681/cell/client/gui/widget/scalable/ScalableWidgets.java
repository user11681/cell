package user11681.cell.client.gui.widget.scalable;

import net.minecraft.util.Identifier;

public final class ScalableWidgets {
    private static final Identifier widgets = new Identifier("textures/gui/widgets.png");
    private static final Identifier advancementWidgets = new Identifier("textures/gui/advancements/widgets.png");
    private static final Identifier window = new Identifier("textures/gui/advancements/window.png");

    private static final ScalableTextureInfo inactiveButtonInfo = new ScalableTextureInfo(0, 46, 1, 199, 200, 1, 19, 20, widgets);
    private static final ScalableTextureInfo windowInfo = new ScalableTextureInfo(14, 238, 252, 22, 126, 140, window);

    public static ScalableWidget yellowRectangle() {
        return longRectangle(0);
    }

    public static ScalableWidget blueRectangle() {
        return longRectangle(1);
    }

    public static ScalableWidget grayRectangle() {
        return longRectangle(2);
    }

    public static ScalableWidget yellowSpikedRectangle() {
        return spikedRectangle(0);
    }

    public static ScalableWidget yellowRoundedRectangle() {
        return roundedRectangle(0);
    }

    public static ScalableWidget whiteRectangle() {
        return rectangle(1);
    }

    public static ScalableWidget whiteSpikedRectangle() {
        return spikedRectangle(1);
    }

    public static ScalableWidget whiteRoundedRectangle() {
        return roundedRectangle(1);
    }

    public static ScalableWidget window() {
        return new ScalableWidget().texture(windowInfo);
    }

    public static ScalableWidget button() {
        return button(1);
    }

    public static ScalableWidget longRectangle(final int index) {
        return new ScalableWidget().texture(new ScalableTextureInfo(0, 3 + index * 26, 2, 198, 200, 2, 18, 20, advancementWidgets));
    }

    public static ScalableWidget rectangle(final int index) {
        return new ScalableWidget().texture(new ScalableTextureInfo(1, 129 + index * 26, 2, 22, 24, 2, 22, 24, advancementWidgets));
    }

    public static ScalableWidget spikedRectangle(final int index) {
        return new ScalableWidget().texture(new ScalableTextureInfo(26, 128 + index * 26, 10, 16, 26, 10, 16, 26, advancementWidgets));
    }

    public static ScalableWidget roundedRectangle(final int index) {
        return new ScalableWidget().texture(new ScalableTextureInfo(54, 129 + index * 26, 7, 15, 22, 4, 21, 26, advancementWidgets));
    }

    public static ScalableWidget button(final int index) {
        return new ScalableWidget().texture(new ScalableTextureInfo(0, 46 + index * 20, 2, 198, 200, 2, 17, 20, widgets));
    }
}
