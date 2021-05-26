package user11681.cell.client.gui.widget.callback;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.util.text.ITextComponent;
import user11681.cell.Cell;
import user11681.cell.client.gui.widget.Widget;

public interface TooltipProvider<T extends Widget<T>> extends TooltipRenderer<T> {
    List<ITextComponent> get(T widget, int mouseX, int mouseY);

    @Override
    default void render(T widget, MatrixStack matrices, int mouseX, int mouseY) {
        Cell.client.screen.renderComponentTooltip(matrices, this.get(widget, mouseX, mouseY), mouseX, mouseY);
    }
}
