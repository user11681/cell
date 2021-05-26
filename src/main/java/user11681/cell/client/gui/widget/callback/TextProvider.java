package user11681.cell.client.gui.widget.callback;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;
import user11681.cell.Cell;
import user11681.cell.client.gui.widget.Widget;

public interface TextProvider<T extends Widget<T>> extends TooltipRenderer<T> {
    ITextComponent get(T widget, int mouseX, int mouseY);

    @Override
    default void render(T widget, MatrixStack matrices, int mouseX, int mouseY) {
        Cell.client.screen.renderTooltip(matrices, this.get(widget, mouseX, mouseY), mouseX, mouseY);
    }
}
