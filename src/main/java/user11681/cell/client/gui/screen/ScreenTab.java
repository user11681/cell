package user11681.cell.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import user11681.cell.client.gui.widget.callback.PressCallback;
import user11681.cell.client.gui.widget.Widget;
import user11681.cell.client.gui.widget.scalable.ScalableWidget;
import user11681.cell.client.gui.widget.scalable.ScalableWidgets;

@Environment(EnvType.CLIENT)
public abstract class ScreenTab extends CellScreen {
    protected final List<ScreenTab> tabs;
    protected final List<Widget<?>> tabButtons;
    protected final int index;

    protected Widget<?> tab;

    public ScreenTab(Text title, List<ScreenTab> tabs) {
        super(title);

        this.tabs = tabs;
        this.tabButtons = new ArrayList<>();

        final int index = tabs.indexOf(this);

        this.index = index < 0 ? tabs.size() : index;
    }

    protected Text getLabel() {
        return this.title;
    }

    @Override
    public void init() {
        super.init();

        if (this.displayTabs()) {
            for (int index = 0, size = this.tabs.size(); index < size; index++) {
                ScalableWidget button = this.add(this.tabs.get(index).getButton());

                this.tabButtons.add(button);

                if (index == this.index) {
                    this.tab = button;
                    button.active = false;
                }
            }
        }
    }

    protected ScalableWidget getButton() {
        return ScalableWidgets.button()
            .text(this.getLabel())
            .x(this.width / 24)
            .y(this.height / 16 + this.index * Math.max(this.height / 16, 30))
            .width(Math.max(96, Math.round(width / 7.5F)))
            .height(20)
            .primaryAction(this.setTabAction(index));
    }

    protected boolean displayTabs() {
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean mouseScrolled(double x, double y, double dWheel) {
        if (dWheel != 0) {
            final int index = MathHelper.clamp((int) (this.index - dWheel), 0, this.tabs.size() - 1);

            if (index != this.index) {
                this.setTab(index);

                return true;
            }
        }

        return super.mouseScrolled(x, y, dWheel);
    }

    public void setTab(int tab) {
        client.openScreen(this.tabs.get(tab));
    }

    public void refresh() {
        this.tabButtons.clear();

        this.init(this.client, this.width, this.height);
    }

    public int getTop(int rows) {
        return this.getTop(this.height / 16, rows);
    }

    public int getTop(int sep, int rows) {
        return (this.height - (rows - 1) * sep) / 2;
    }

    public int getHeight(int rows, int row) {
        return this.getTop(rows) + row * this.height / 16;
    }

    public int getHeight(int sep, int rows, int row) {
        return this.getTop(rows, sep) + row * sep;
    }

    protected <T extends Widget<T>> PressCallback<T> setTabAction(int index) {
        return (T button) -> this.setTab(index);
    }
}
