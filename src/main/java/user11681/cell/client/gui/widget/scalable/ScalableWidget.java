package user11681.cell.client.gui.widget.scalable;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import user11681.cell.client.gui.CellElement;
import user11681.cell.client.gui.widget.Widget;

@Environment(EnvType.CLIENT)
public class ScalableWidget extends Widget<ScalableWidget> {
    public ScalableTextureInfo texture;

    public int textureWidth = 256;
    public int textureHeight = 256;

    public float r = 1;
    public float g = 1;
    public float b = 1;
    public float a = 1;

    public ScalableWidget textureWidth(int textureWidth) {
        this.textureWidth = textureWidth;

        return this;
    }

    public ScalableWidget textureHeight(int textureHeight) {
        this.textureHeight = textureHeight;

        return this;
    }

    public ScalableWidget texture(ScalableTextureInfo texture) {
        this.texture = texture;

        return this;
    }

    public void color3f(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1;
    }

    public void color4f(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public void renderBackground(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.texture.texture.bindTexture();
        this.resetColor();

        RenderSystem.enableBlend();
        this.renderCorners(matrices);
        this.renderMiddles(matrices);
        RenderSystem.disableBlend();

        if (this.focused && this.active && (this.primaryAction != null || this.secondaryAction != null)) {
            this.drawBorder(matrices);
        }
    }

    protected void renderCorners(MatrixStack matrices) {
        ScalableTextureInfo info = this.texture;
        int[][][] corners = info.corners;

        for (int i = 0, length = corners.length; i < length; ++i) {
            int[][] corner = corners[i];
            int[] topLeft = corner[0];
            int u = topLeft[0];
            int v = topLeft[1];
            int width = corner[1][0] - u;
            int height = corner[2][1] - v;

            drawTexture(matrices, this.getX() + i % 2 * (this.width - width), this.getY() + i / 2 * (this.height - height), this.getZOffset(), info.u + u, info.v + v, width, height, this.textureHeight, this.textureWidth);
        }
    }

    protected void renderMiddles(MatrixStack matrices) {
        ScalableTextureInfo info = this.texture;
        int[][][] middles = info.middles;
        int[][][] corners = info.corners;
        int middleWidth = this.width - corners[0][1][0] + corners[1][0][0] - corners[1][1][0];
        int middleHeight = this.height - corners[0][2][1] + corners[2][0][1] - corners[2][2][1];

        for (int i = 0, length = middles.length; i < length; ++i) {
            int[][] middle = middles[i];
            int u = middle[0][0];
            int v = middle[0][1];
            int endU = middle[1][0];
            int endV = middle[2][1];
            int maxWidth = endU - u;
            int maxHeight = endV - v;
            int absoluteU = info.u + u;
            int absoluteV = info.v + v;
            int remainingHeight = i % 4 == 0 ? maxHeight : middleHeight;
            int endX = i % 2 == 0 ? this.getX() + middle[0][0] + middleWidth : (i == 1 ? this.getX() + middle[1][0] : this.getX() + middles[1][1][0] + middleWidth + middle[1][0] - middle[0][0]);

            for (int drawnHeight, endY = i % 4 != 0 ? this.getY() + middle[0][1] + middleHeight : (i == 0 ? this.getY() + middle[2][1] : this.getY() + middles[0][2][1] + middleHeight + middle[2][1] - middle[0][1]); remainingHeight > 0; remainingHeight -= drawnHeight) {
                int remainingWidth = i % 2 == 0 ? middleWidth : maxWidth;
                drawnHeight = Math.min(remainingHeight, maxHeight);

                for (int drawnWidth, y = endY - remainingHeight; remainingWidth > 0; remainingWidth -= drawnWidth) {
                    drawnWidth = Math.min(remainingWidth, maxWidth);

                    drawTexture(matrices, endX - remainingWidth, y, this.getZOffset(), absoluteU, absoluteV, drawnWidth, drawnHeight, this.textureHeight, this.textureWidth);
                }
            }
        }
    }

    protected void drawBorder(MatrixStack matrices) {
        int endX = this.getX() + this.width - 1;
        int endY = this.getY() + this.height;

        CellElement.drawHorizontalLine(matrices, this.getX(), endX, this.getY(), this.getZOffset(), -1);
        CellElement.drawVerticalLine(matrices, this.getX(), this.getY(), endY, this.getZOffset(), -1);
        CellElement.drawVerticalLine(matrices, endX, this.getY(), endY, this.getZOffset(), -1);
        CellElement.drawHorizontalLine(matrices, this.getX(), endX, endY - 1, this.getZOffset(), -1);
    }

    protected void detectBorder() {}

    protected void resetColor() {
        if (this.active) {
            RenderSystem.setShaderColor(this.r, this.g, this.b, this.a);
        } else {
            float chroma = 160F / 255;

            RenderSystem.setShaderColor(this.r * chroma, this.g * chroma, this.b * chroma, this.a);
        }
    }
}
