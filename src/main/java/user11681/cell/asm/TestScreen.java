package user11681.cell.asm;

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

        final ScalableWidget widget = ScalableWidgets.button()
            .x(this.width / 2)
            .y(this.height / 2)
            .width(200)
            .height(20)
            .tooltip((final ScalableWidget widge, final int mouseX, final int mouseY) -> new TranslatableText("%s, %s", mouseX, mouseY))
            .primaryAction((final ScalableWidget widge) -> widge.text(new LiteralText("left click")))
            .secondaryAction((final ScalableWidget widge) -> widge.text(new LiteralText("right click")))
            .tertiaryAction((final ScalableWidget widge) -> widge.width(widge.width + 10).height(widge.height + 4).text(new LiteralText("middle click")));

        this.add(widget);
    }

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY, final float delta) {
        this.renderBackground(matrices);

        super.render(matrices, mouseX, mouseY, delta);
    }
}
