package user11681.cell.client.gui.widget.callback;

import net.minecraft.client.util.math.MatrixStack;
import user11681.cell.client.gui.widget.Widget;

public interface TooltipRenderer<T extends Widget<T>> {
    void render(final T widget, final MatrixStack matrices, final int mouseX, final int mouseY);
}
