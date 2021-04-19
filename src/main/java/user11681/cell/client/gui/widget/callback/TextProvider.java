package user11681.cell.client.gui.widget.callback;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import user11681.cell.Cell;
import user11681.cell.client.gui.widget.Widget;

public interface TextProvider<T extends Widget<T>> extends TooltipRenderer<T> {
    Text get(T widget, int mouseX, int mouseY);

    @Override
    default void render(T widget, MatrixStack matrices, int mouseX, int mouseY) {
        Cell.client.currentScreen.renderTooltip(matrices, this.get(widget, mouseX, mouseY), mouseX, mouseY);
    }
}
