package user11681.cell.client.gui.widget.scalable;

import net.minecraft.util.Identifier;

public final class ScalableWidgets {
    private static final Identifier widgets = new Identifier("textures/gui/widgets.png");
    private static final Identifier advancementWidgets = new Identifier("textures/gui/advancements/widgets.png");
    private static final Identifier window = new Identifier("textures/gui/advancements/window.png");

    private static final ScalableTextureInfo inactiveButtonInfo = new ScalableTextureInfo().texture(widgets).position(0, 46).slices(1, 199, 200, 1, 19, 20);
    private static final ScalableTextureInfo windowInfo = new ScalableTextureInfo().texture(window).slices(14, 238, 252, 22, 126, 140);

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

    public static ScalableWidget longRectangle(int index) {
        return new ScalableWidget().texture(new ScalableTextureInfo().texture(advancementWidgets).position(0, 3 + index * 26).slices(2, 198, 200, 2, 18, 20));
    }

    public static ScalableWidget rectangle(int index) {
        return new ScalableWidget().texture(new ScalableTextureInfo().texture(advancementWidgets).position(1, 129 + index * 26).slices(2, 22, 24, 2, 22, 24));
    }

    public static ScalableWidget spikedRectangle(int index) {
        return new ScalableWidget().texture(new ScalableTextureInfo().texture(advancementWidgets).position(26, 128 + index * 26).slices(10, 16, 26, 10, 16, 26));
    }

    public static ScalableWidget roundedRectangle(int index) {
        return new ScalableWidget().texture(new ScalableTextureInfo().texture(advancementWidgets).position(54, 129 + index * 26).slices(7, 15, 22, 4, 21, 26));
    }

    public static ScalableWidget button(int index) {
        return new ScalableWidget().texture(new ScalableTextureInfo().texture(widgets).position(0, 46 + index * 20).slices(2, 198, 200, 2, 17, 20));
    }
}
