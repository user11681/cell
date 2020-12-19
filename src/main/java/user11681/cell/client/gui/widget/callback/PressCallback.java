package user11681.cell.client.gui.widget.callback;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import user11681.cell.client.gui.widget.Widget;

@Environment(EnvType.CLIENT)
public interface PressCallback<T extends Widget<T>> {
    void onPress(T widget);
}
