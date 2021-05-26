package user11681.cell.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.CharacterManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;
import user11681.cell.Cell;
import user11681.cell.client.gui.CellElement;
import user11681.cell.client.gui.widget.callback.PressCallback;
import user11681.cell.client.gui.widget.callback.TextProvider;
import user11681.cell.client.gui.widget.callback.TooltipProvider;
import user11681.cell.client.gui.widget.callback.TooltipRenderer;

@SuppressWarnings("unchecked")
@OnlyIn(Dist.CLIENT)
public abstract class Widget<T extends Widget<T>> extends CellElement {
    protected static final SoundHandler soundManager = Cell.client.getSoundManager();
    protected static final FontRenderer textRenderer = Cell.client.font;
    protected static final CharacterManager textHandler = textRenderer.getSplitter();

    public List<CellElement> children = ReferenceArrayList.wrap(new CellElement[0]);

    public ITextComponent text = StringTextComponent.EMPTY;
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

    public T x(int x) {
        this.x = x;
        this.modified = true;

        return (T) this;
    }

    public T y(int y) {
        this.y = y;
        this.modified = true;

        return (T) this;
    }

    public T width(int width) {
        this.width = width;
        this.modified = true;

        return (T) this;
    }

    public T height(int height) {
        this.height = height;
        this.modified = true;

        return (T) this;
    }

    public T center(boolean center) {
        this.center = center;

        return (T) this;
    }

    public T text(String text) {
        return this.text(new TranslationTextComponent(text));
    }

    public T text(ITextComponent text) {
        this.text = text;
        this.modified = true;

        return (T) this;
    }

    public T primaryAction(PressCallback<T> action) {
        this.primaryAction = action;
        this.modified = true;

        return (T) this;
    }

    public T secondaryAction(PressCallback<T> action) {
        this.secondaryAction = action;
        this.modified = true;

        return (T) this;
    }

    public T tertiaryAction(PressCallback<T> action) {
        this.tertiaryAction = action;

        this.modified = true;

        return (T) this;
    }

    public T tooltip(String tooltip) {
        return this.tooltip((T widget, int mouseX, int mouseY) -> new TranslationTextComponent(tooltip));
    }

    public T tooltip(ITextComponent... tooltip) {
        return this.tooltip(Arrays.asList(tooltip));
    }

    public T tooltip(List<ITextComponent> tooltip) {
        return this.tooltip((T widget, int mouseX, int mouseY) -> tooltip);
    }

    public T tooltip(TooltipProvider<T> tooltipProvider) {
        return this.tooltip((TooltipRenderer<T>) tooltipProvider);
    }

    public T tooltip(TextProvider<T> textProvider) {
        return this.tooltip((TooltipRenderer<T>) textProvider);
    }

    public T tooltip(TooltipRenderer<T> renderer) {
        this.tooltipRenderer = renderer;
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
    public List<? extends IGuiEventListener> children() {
        return this.children;
    }

    @Override
    public boolean changeFocus(boolean lookForwards) {
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
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
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

    public void renderForeground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.text != null) {
            drawCenteredString(matrices, textRenderer, this.text, this.getX() + this.width / 2, this.getY() + this.height / 2 - textRenderer.lineHeight / 2, 0xFFFFFF);
        }
    }

    public void renderTooltip(MatrixStack matrices, int mouseX, int mouseY) {
        if (this.hovered && this.tooltipRenderer != null) {
            this.tooltipRenderer.render((T) this, matrices, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
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

    protected boolean clicked(double mouseX, double mouseY) {
        return this.active && this.hovered;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
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

    public boolean isValidPrimaryClick(int button) {
        return this.primaryAction != null && button == 0;
    }

    public boolean isValidSecondaryClick(int button) {
        return this.secondaryAction != null && button == 1;
    }

    public boolean isValidTertiaryClick(int button) {
        return this.tertiaryAction != null && button == 2;
    }

    private boolean isValidSecondaryKey(int keyCode, int scanCode, int modifiers) {
        return this.secondaryAction != null && this.isValidKey(keyCode, scanCode, modifiers) && (modifiers & GLFW.GLFW_MOD_SHIFT) != 0;
    }

    private boolean isValidTertiaryKey(int keyCode, int scanCode, int modifiers) {
        return this.tertiaryAction != null && this.isValidKey(keyCode, scanCode, modifiers) && (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;
    }

    public boolean isValidPrimaryKey(int keyCode, int scanCode, int modifiers) {
        return this.primaryAction != null && this.isValidKey(keyCode, scanCode, modifiers);
    }

    public boolean isValidKey(int keyCode, int scanCode, int modifiers) {
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
        soundManager.play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1));
    }

    protected void compute() {
        this.modified = false;
    }
}
