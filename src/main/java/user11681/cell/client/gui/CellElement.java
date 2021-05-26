package user11681.cell.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public abstract class CellElement extends FocusableGui implements DrawableElement, ITickable, Cloneable {
    public int x;
    public int y;

    public int width;
    public int height;

    public static void fill(MatrixStack matrices, int x1, int y1, int x2, int y2, float z, int color) {
        fill(matrices.last().pose(), x1, y1, x2, y2, z, color);
    }

    public static void fill(Matrix4f matrix, int x1, int y1, int x2, int y2, float z, int color) {
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
        final BufferBuilder bufferBuilder = Tessellator.getInstance().getBuilder();

        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();

        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x1, y2, z).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix, x2, y2, z).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix, x2, y1, z).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix, x1, y1, z).color(r, g, b, a).endVertex();
        bufferBuilder.end();

        WorldVertexBufferUploader.end(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawHorizontalLine(MatrixStack matrices, int x1, int x2, int y, int z, int color) {
        if (x2 < x1) {
            final int i = x1;

            x1 = x2;
            x2 = i;
        }

        fill(matrices, x1, y, x2 + 1, y + 1, z, color);
    }

    public static void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, int z, int color) {
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
        } catch (CloneNotSupportedException exception) {
            throw new InternalError(exception);
        }
    }
}
