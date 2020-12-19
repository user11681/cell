package user11681.cell.client.gui.widget;

import java.text.NumberFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CellSlider extends SliderWidget {
    protected static final NumberFormat FLOAT_FORMAT = NumberFormat.getNumberInstance();

    public Text label;

    public ChangeAction onChange;

    protected double min;
    protected double max;
    protected double range;
    protected double scaledValue;
    protected double lost;

    public boolean discrete;

    public CellSlider(final int x, final int y, final int width, final int height, final double min, final double max) {
        this(x, y, width, height, min, min, max);
    }

    public CellSlider(final int x, final int y, final int width, final int height, final double value, final double min, final double max) {
        this(x, y, width, height, value, min, max, LiteralText.EMPTY);
    }

    public CellSlider(final int x, final int y, final int width, final int height, final double value, final double min, final double max, final Text label) {
        super(x, y, width, height, label, (value - min) / (max - min));

        this.label = label;
        this.min = min;
        this.max = max;
        this.range = max - min;
        this.scaledValue = value;
        this.discrete = true;

        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        final Object formattedValue = this.discrete || Math.abs(this.scaledValue) >= 100 ? (long) this.scaledValue : FLOAT_FORMAT.format(this.scaledValue);

        this.setMessage(this.label == LiteralText.EMPTY ? new TranslatableText("%s", formattedValue) : new TranslatableText("%s:%s", this.label, formattedValue));
    }

    @Override
    protected void applyValue() {
        this.scaledValue = this.min + this.value * this.range;

        if (this.onChange != null) {
            this.onChange.accept(this);
        }
    }

    public double getValue() {
        return this.scaledValue;
    }

    public void setValue(final double value) {
        this.value = (value - this.min) / this.range;
        this.scaledValue = value;
        this.updateMessage();
    }

    @Override
    public boolean mouseScrolled(final double mouseX, final double mouseY, final double amount) {
        double addition = Screen.hasShiftDown()
                        ? 0.05 * this.range
                        : Screen.hasControlDown()
                        ? 1
                        : 0.01 * this.range;

        if (this.discrete) {
            final double newAddition = Math.ceil(Math.abs(addition)) + (long) this.lost;

            this.lost += addition - newAddition;

            addition = newAddition;
        }

        this.setValue(MathHelper.clamp(this.scaledValue + Math.signum(amount) * addition, this.min, this.max));

        return true;
    }

    public double min() {
        return this.min;
    }

    public double max() {
        return this.max;
    }

    public void setMin(final double min) {
        this.range = this.max - (this.min = min);
    }

    public void setMax(final double max) {
        this.range = (this.max = max) - this.min;
    }

    public interface ChangeAction {
        void accept(CellSlider slider);
    }
}
