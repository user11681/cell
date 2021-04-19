package user11681.cell.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import java.util.Collection;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import user11681.cell.Cell;
import user11681.cell.client.gui.DrawableElement;

@SuppressWarnings("unchecked")
@Environment(EnvType.CLIENT)
public abstract class CellScreen extends Screen implements DrawableElement {
    public final ReferenceArrayList<DrawableElement> elements = new ReferenceArrayList<>();

    protected CellScreen() {
        this(LiteralText.EMPTY);
    }

    protected CellScreen(Text title) {
        super(title);
    }

    @Override
    public void tick() {
        super.tick();

        for (DrawableElement element : this.elements) {
            element.tick();
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        for (DrawableElement element : this.elements) {
            element.render(matrices, mouseX, mouseY, delta);
        }
    }

    protected <T extends DrawableElement> T add(T element) {
        this.elements.add(element);

        return element;
    }

    protected <T extends DrawableElement> void add(T... elements) {
        for (T element : elements) {
            this.add(element);
        }
    }

    protected <T extends Collection<U>, U extends DrawableElement> void add(T elements) {
        for (U element : elements) {
            this.add(element);
        }
    }

    @Override
    public List<? extends Element> children() {
        return this.elements;
    }

    protected <T extends AbstractButtonWidget> void removeButtons(T... buttons) {
        for (T button : buttons) {
            this.removeButton(button);
        }
    }

    protected <T extends Collection<U>, U extends AbstractButtonWidget> void removeButtons(T buttons) {
        this.buttons.removeAll(buttons);
    }

    protected <T extends AbstractButtonWidget> void removeButton(T button) {
        this.buttons.remove(button);
    }

    @Override
    protected <T extends AbstractButtonWidget> T addButton(T button) {
        return super.addButton(button);
    }

    public void renderBackground(Identifier identifier, int x, int y, int width, int height) {
        this.renderBackground(identifier, x, y, width, height, 64, 0);
    }

    public void renderBackground(Identifier identifier, int x, int y, int width, int height, int chroma) {
        this.renderBackground(identifier, x, y, width, height, chroma, 0);
    }

    public void renderBackground(Identifier identifier, int x, int y, int width, int height, int chroma, int alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        float f = 1 << 5;
        float endX = x + width;
        float endY = y + height;

        Cell.textureManager.bindTexture(identifier);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        builder.vertex(x, endY, 0).color(chroma, chroma, chroma, 255).texture(0, endY / f + alpha).next();
        builder.vertex(endX, endY, 0).color(chroma, chroma, chroma, 255).texture(endX / f, endY / f + alpha).next();
        builder.vertex(endX, y, 0).color(chroma, chroma, chroma, 255).texture(endX / f, alpha).next();
        builder.vertex(x, y, 0).color(chroma, chroma, chroma, 255).texture(0, alpha).next();

        tessellator.draw();
    }

    public void renderGuiItem(ItemStack itemStack, int x, int y, int z) {
        this.withZ(z, () -> this.itemRenderer.renderGuiItemIcon(itemStack, x, y));
    }

    public void withZ(int z, Runnable runnable) {
        this.addZOffset(z);
        this.itemRenderer.zOffset = this.getZOffset();
        runnable.run();
        this.addZOffset(-z);
        this.itemRenderer.zOffset = this.getZOffset();
    }

    public void addZOffset(int z) {
        this.zOffset += z;
        this.itemRenderer.zOffset += z;
    }
}
