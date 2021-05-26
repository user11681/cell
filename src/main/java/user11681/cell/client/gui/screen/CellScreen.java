package user11681.cell.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import user11681.cell.Cell;
import user11681.cell.client.gui.DrawableElement;

@SuppressWarnings("unchecked")
@OnlyIn(Dist.CLIENT)
public abstract class CellScreen extends Screen implements DrawableElement {
    public final ReferenceArrayList<DrawableElement> elements = new ReferenceArrayList<>();

    protected CellScreen() {
        this(StringTextComponent.EMPTY);
    }

    protected CellScreen(ITextComponent title) {
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
    public List<? extends IGuiEventListener> children() {
        return this.elements;
    }

    protected <T extends AbstractButton> void removeButtons(T... buttons) {
        for (T button : buttons) {
            this.removeButton(button);
        }
    }

    protected <T extends Collection<U>, U extends AbstractButton> void removeButtons(T buttons) {
        this.buttons.removeAll(buttons);
    }

    protected <T extends AbstractButton> void removeButton(T button) {
        this.buttons.remove(button);
    }

    @Override
    protected <T extends Widget> T addButton(T button) {
        return super.addButton(button);
    }

    public void renderBackground(ResourceLocation identifier, int x, int y, int width, int height) {
        this.renderBackground(identifier, x, y, width, height, 64, 0);
    }

    public void renderBackground(ResourceLocation identifier, int x, int y, int width, int height, int chroma) {
        this.renderBackground(identifier, x, y, width, height, chroma, 0);
    }

    public void renderBackground(ResourceLocation identifier, int x, int y, int width, int height, int chroma, int alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuilder();
        float f = 1 << 5;
        float endX = x + width;
        float endY = y + height;

        Cell.textureManager.bind(identifier);
        RenderSystem.color4f(1, 1, 1, 1);

        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
        builder.vertex(x, endY, 0).color(chroma, chroma, chroma, 255).uv(0, endY / f + alpha).endVertex();
        builder.vertex(endX, endY, 0).color(chroma, chroma, chroma, 255).uv(endX / f, endY / f + alpha).endVertex();
        builder.vertex(endX, y, 0).color(chroma, chroma, chroma, 255).uv(endX / f, alpha).endVertex();
        builder.vertex(x, y, 0).color(chroma, chroma, chroma, 255).uv(0, alpha).endVertex();

        tessellator.end();
    }

    public void renderGuiItem(ItemStack itemStack, int x, int y, int z) {
        this.withZ(z, () -> this.itemRenderer.renderGuiItem(itemStack, x, y));
    }

    public void withZ(int z, Runnable runnable) {
        this.addZOffset(z);
        this.itemRenderer.blitOffset = this.getBlitOffset();
        runnable.run();
        this.addZOffset(-z);
        this.itemRenderer.blitOffset = this.getBlitOffset();
    }

    public void addZOffset(int z) {
        this.setBlitOffset(this.getBlitOffset() + z);
        this.itemRenderer.blitOffset += z;
    }
}
