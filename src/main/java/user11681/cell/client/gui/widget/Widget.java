package user11681.cell.client.gui.widget;

import java.util.Arrays;
import java.util.List;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;
import user11681.cell.Cell;
import user11681.cell.client.gui.CellElement;
import user11681.cell.client.gui.widget.callback.PressCallback;
import user11681.cell.client.gui.widget.callback.TextProvider;
import user11681.cell.client.gui.widget.callback.TooltipProvider;
import user11681.cell.client.gui.widget.callback.TooltipRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@SuppressWarnings("unchecked")
@Environment(EnvType.CLIENT)
public abstract class Widget<T extends Widget<T>> extends CellElement {
    protected static final TextureManager textureManager = Cell.client.getTextureManager();
    protected static final SoundManager soundManager = Cell.client.getSoundManager();
    protected static final TextRenderer textRenderer = Cell.client.textRenderer;
    protected static final TextHandler textHandler = textRenderer.getTextHandler();

    public List<CellElement> children = ReferenceArrayList.wrap(new CellElement[0]);

    public Text text = LiteralText.EMPTY;
    public PressCallback<T> primaryAction;
    public PressCallback<T> secondaryAction;
    public PressCallback<T> tertiaryAction;
    public TooltipRenderer<T> tooltipRenderer;

    public boolean center = true;
    public boolean active = true;
    public boolean visible = true;
    public boolean hovered;
    public boolean focused;
    public boolean selected;
    public boolean wasHovered;
    public boolean modified;

    protected abstract void renderBackground(MatrixStack matrices, int mouseX, int mouseY, float delta);

    public T x(final int x) {
        this.x = x;

        this.modified = true;

        return (T) this;
    }

    public T y(final int y) {
        this.y = y;

        this.modified = true;

        return (T) this;
    }

    public T width(final int width) {
        this.width = width;

        this.modified = true;

        return (T) this;
    }

    public T height(final int height) {
        this.height = height;

        this.modified = true;

        return (T) this;
    }

    public T center(final boolean center) {
        this.center = center;

        return (T) this;
    }

    public T text(final String text) {
        this.text = new TranslatableText(text);

        this.modified = true;

        return (T) this;
    }

    public T text(final Text text) {
        this.text = text;

        this.modified = true;

        return (T) this;
    }

    public T primaryAction(final PressCallback<T> action) {
        this.primaryAction = action;

        this.modified = true;

        return (T) this;
    }

    public T secondaryAction(final PressCallback<T> action) {
        this.secondaryAction = action;

        this.modified = true;

        return (T) this;
    }

    public T tertiaryAction(final PressCallback<T> action) {
        this.tertiaryAction = action;

        this.modified = true;

        return (T) this;
    }

    public T tooltip(final String tooltip) {
        this.tooltipRenderer = (TextProvider<T>) (final T widget, final int mouseX, final int mouseY) -> new TranslatableText(tooltip);

        this.modified = true;

        return (T) this;
    }

    public T tooltip(final Text tooltip) {
        this.tooltipRenderer = (TextProvider<T>) (final T widget, final int mouseX, final int mouseY) -> tooltip;

        this.modified = true;

        return (T) this;
    }

    public T tooltip(final Text... tooltip) {
        this.tooltipRenderer = (TooltipProvider<T>) (final T widget, final int mouseX, final int mouseY) -> Arrays.asList(tooltip);

        this.modified = true;

        return (T) this;
    }

    public T tooltip(final List<Text> tooltip) {
        this.tooltipRenderer = (TooltipProvider<T>) (final T widget, final int mouseX, final int mouseY) -> tooltip;

        this.modified = true;

        return (T) this;
    }

    public T tooltip(final TooltipProvider<T> tooltipProvider) {
        this.tooltipRenderer = tooltipProvider;

        this.modified = true;

        return (T) this;
    }

    public T tooltip(final TextProvider<T> tooltipSupplier) {
        this.tooltipRenderer = tooltipSupplier;

        this.modified = true;

        return (T) this;
    }

    public int getX() {
        return this.center ? this.x - this.width / 2 : this.x;
    }

    public int getY() {
        return this.center ? this.y - this.height / 2 : this.y;
    }

    public int endX() {
        return this.center ? this.x + this.width / 2 : this.x + this.width;
    }

    public int endY() {
        return this.center ? this.y + this.height / 2 : this.y + this.height;
    }

    @Override
    protected T clone() {
        return (T) super.clone();
    }

    @Override
    public List<? extends Element> children() {
        return this.children;
    }

    @Override
    public boolean changeFocus(final boolean lookForwards) {
        super.changeFocus(lookForwards);

        if (this.active && this.visible) {
            this.selected ^= true;

            this.onSelected();

            return this.selected;
        }

        return false;
    }

    public void onSelected() {}

    @Override
    public void tick() {}

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY, final float delta) {
        if (this.modified) {
            this.compute();
        }

        if (this.visible) {
            this.hovered = mouseX >= this.getX() && mouseX <= this.endX() && mouseY >= this.getY() && mouseY <= this.endY();
            this.focused = this.hovered || this.selected;

            this.renderWidget(matrices, mouseX, mouseY, delta);
            this.renderTooltip(matrices, mouseX, mouseY);

            this.wasHovered = this.hovered;
        } else {
            this.hovered = false;
        }
    }

    protected void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices, mouseX, mouseY, delta);
        this.renderForeground(matrices, mouseX, mouseY, delta);
    }

    public void renderForeground(final MatrixStack matrices, final int mouseX, final int mouseY, final float delta) {
        if (this.text != null) {
            drawCenteredText(matrices, textRenderer, this.text, this.getX() + this.width / 2, this.getY() + this.height / 2 - textRenderer.fontHeight / 2, 0xFFFFFF);
        }
    }

    public void renderTooltip(final MatrixStack matrices, final int mouseX, final int mouseY) {
        if (this.hovered && this.tooltipRenderer != null) {
            this.tooltipRenderer.render((T) this, matrices, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        super.mouseClicked(mouseX, mouseY, button);

        if (this.clicked(mouseX, mouseY)) {
            if (this.isValidPrimaryClick(button)) {
                this.onPrimaryPress();

                return true;
            }

            if (this.isValidSecondaryClick(button)) {
                this.onSecondaryPress();

                return true;
            }

            if (this.isValidTertiaryClick(button)) {
                this.onTertiaryPress();

                return true;
            }
        }

        return false;
    }

    protected boolean clicked(final double mouseX, final double mouseY) {
        return this.active && this.hovered;
    }

    @Override
    public boolean keyPressed(final int keyCode, final int scanCode, final int modifiers) {
        if (!super.keyPressed(keyCode, scanCode, modifiers) && this.selected) {
            if (this.isValidTertiaryKey(keyCode, scanCode, modifiers)) {
                this.onTertiaryPress();
            } else if (this.isValidSecondaryKey(keyCode, scanCode, modifiers)) {
                this.onSecondaryPress();
            } else if (this.isValidPrimaryKey(keyCode, scanCode, modifiers)) {
                this.onPrimaryPress();
            } else {
                return false;
            }

            return true;
        }

        return false;
    }

    public boolean isValidPrimaryClick(final int button) {
        return this.primaryAction != null && button == 0;
    }

    public boolean isValidSecondaryClick(final int button) {
        return this.secondaryAction != null && button == 1;
    }

    public boolean isValidTertiaryClick(final int button) {
        return this.tertiaryAction != null && button == 2;
    }

    private boolean isValidSecondaryKey(final int keyCode, final int scanCode, final int modifiers) {
        return this.secondaryAction != null && this.isValidKey(keyCode, scanCode, modifiers) && (modifiers & GLFW.GLFW_MOD_SHIFT) != 0;
    }

    private boolean isValidTertiaryKey(final int keyCode, final int scanCode, final int modifiers) {
        return this.tertiaryAction != null && this.isValidKey(keyCode, scanCode, modifiers) && (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;
    }

    public boolean isValidPrimaryKey(final int keyCode, final int scanCode, final int modifiers) {
        return this.primaryAction != null && this.isValidKey(keyCode, scanCode, modifiers);
    }

    public boolean isValidKey(final int keyCode, final int scanCode, final int modifiers) {
        return keyCode == GLFW.GLFW_KEY_SPACE || keyCode == GLFW.GLFW_KEY_ENTER;
    }

    protected void onPress() {
        this.playSound();
    }

    protected void onPrimaryPress() {
        this.onPress();

        this.primaryAction.onPress((T) this);
    }

    protected void onSecondaryPress() {
        this.onPress();

        this.secondaryAction.onPress((T) this);
    }

    protected void onTertiaryPress() {
        this.onPress();

        this.tertiaryAction.onPress((T) this);
    }

    public void playSound() {
        soundManager.play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1));
    }

    protected void compute() {
        this.modified = false;
    }
}
