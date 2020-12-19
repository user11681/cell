package user11681.cell.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public abstract class CellElement extends AbstractParentElement implements DrawableElement, TickableElement, Cloneable {
    public int x;
    public int y;

    public int width;
    public int height;

    public static void fill(final MatrixStack matrices, final int x1, final int y1, final int x2, final int y2, final float z, final int color) {
        fill(matrices.peek().getModel(), x1, y1, x2, y2, z, color);
    }

    public static void fill(final Matrix4f matrix, int x1, int y1, int x2, int y2, final float z, final int color) {
        int i;

        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }

        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }

        final float a = (color >> 24 & 255) / 255F;
        final float r = (color >> 16 & 255) / 255F;
        final float g = (color >> 8 & 255) / 255F;
        final float b = (color & 255) / 255F;
        final BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x1, y2, z).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x2, y2, z).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x2, y1, z).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x1, y1, z).color(r, g, b, a).next();
        bufferBuilder.end();

        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawHorizontalLine(final MatrixStack matrices, int x1, int x2, final int y, final int z, final int color) {
        if (x2 < x1) {
            final int i = x1;

            x1 = x2;
            x2 = i;
        }

        fill(matrices, x1, y, x2 + 1, y + 1, z, color);
    }

    public static void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, final int z, final int color) {
        if (y2 < y1) {
            final int i = y1;

            y1 = y2;
            y2 = i;
        }

        fill(matrices, x, y1 + 1, x + 1, y2, z, color);
    }

    @Override
    protected CellElement clone() {
        try {
            return (CellElement) super.clone();
        } catch (final CloneNotSupportedException exception) {
            throw new InternalError(exception);
        }
    }
}
