package test;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import user11681.cell.client.gui.screen.CellScreen;
import user11681.cell.client.gui.widget.scalable.ScalableWidget;
import user11681.cell.client.gui.widget.scalable.ScalableWidgets;

@SuppressWarnings("ConstantConditions")
public class TestScreen extends CellScreen {
    public TestScreen() {
        super(LiteralText.EMPTY);
    }

    @Override
    protected void init() {
        super.init();

        ScalableWidget widget = ScalableWidgets.button()
            .x(this.width / 2)
            .y(this.height / 2)
            .width(200)
            .height(20)
            .tooltip((ScalableWidget widge, int mouseX, int mouseY) -> new TranslatableText("%s, %s", mouseX, mouseY))
            .primaryAction((ScalableWidget widge) -> widge.text(new LiteralText("left click")))
            .secondaryAction((ScalableWidget widge) -> widge.text(new LiteralText("right click")))
            .tertiaryAction((ScalableWidget widge) -> widge.width(widge.width + 10).height(widge.height + 4).text(new LiteralText("middle click")));

        this.add(widget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        super.render(matrices, mouseX, mouseY, delta);
    }
}
