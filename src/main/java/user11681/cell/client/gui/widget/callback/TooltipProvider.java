package user11681.cell.client.gui.widget.callback;

import java.util.List;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import user11681.cell.Cell;
import user11681.cell.client.gui.widget.Widget;

public interface TooltipProvider<T extends Widget<T>> extends TooltipRenderer<T> {
    List<Text> get(T widget, int mouseX, int mouseY);

    @Override
    default void render(final T widget, final MatrixStack matrices, final int mouseX, final int mouseY) {
        Cell.client.currentScreen.renderTooltip(matrices, this.get(widget, mouseX, mouseY), mouseX, mouseY);
    }
}
