package user11681.cell.client.gui.widget.scalable;

import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import user11681.cell.Cell;

public class ScalableTextureInfo {
    public final int[][][] middles = new int[5][4][2];
    public final int[][][] corners = new int[4][4][2];
    public final int[][] border = new int[4][2];

    public AbstractTexture texture;

    public int u;
    public int v;

    public ScalableTextureInfo(final int u, final int v, final int u0, final int u1, final int u2, final int v0, final int v1, final int v2, final Identifier textureId) {
        this.setSlices(u, v, u0, u1, u2, v0, v1, v2, textureId);
    }

    public ScalableTextureInfo(final int u0, final int u1, final int u2, final int v0, final int v1, final int v2, final Identifier textureId) {
        this.setSlices(u0, u1, u2, v0, v1, v2, textureId);
    }

    public ScalableTextureInfo(final int u, final int v, final int u0, final int u1, final int u2, final int v0, final int v1, final int v2, final AbstractTexture texture) {
        this.setSlices(u, v, u0, u1, u2, v0, v1, v2, texture);
    }

    public ScalableTextureInfo(final int u0, final int u1, final int u2, final int v0, final int v1, final int v2, final AbstractTexture texture) {
        this.setSlices(u0, u1, u2, v0, v1, v2, texture);
    }

    public ScalableTextureInfo(final int u, final int v, final int u0, final int u1, final int u2, final int v0, final int v1, final int v2) {
        this.setSlices(u, v, u0, u1, u2, v0, v1, v2);
    }

    public ScalableTextureInfo(final int u0, final int u1, final int u2, final int v0, final int v1, final int v2) {
        this.setSlices(u0, u1, u2, v0, v1, v2);
    }

    public ScalableTextureInfo() {}

    public void setSlices(final int u, final int v, final int u0, final int u1, final int u2, final int v0, final int v1, final int v2, final Identifier textureId) {
        this.u = u;
        this.v = v;

        this.setSlices(u0, u1, u2, v0, v1, v2, textureId);
    }

    public void setSlices(final int u0, final int u1, final int u2, final int v0, final int v1, final int v2, final Identifier textureId) {
        final TextureManager textureManager = Cell.client.getTextureManager();
        final AbstractTexture texture = textureManager.getTexture(textureId);

        if (texture == null) {
            this.texture = new ResourceTexture(textureId);
            textureManager.registerTexture(textureId, this.texture);
            this.setSlices(u0, u1, u2, v0, v1, v2, this.texture);
        } else {
            this.texture = texture;
            this.setSlices(u0, u1, u2, v0, v1, v2, texture);
        }
    }

    public void setSlices(final int u, final int v, final int u0, final int u1, final int u2, final int v0, final int v1, final int v2, final AbstractTexture texture) {
        this.u = u;
        this.v = v;

        this.setSlices(u0, u1, u2, v0, v1, v2, texture);
    }

    public void setSlices(final int u0, final int u1, final int u2, final int v0, final int v1, final int v2, final AbstractTexture texture) {
        this.texture = texture;

        this.setSlices(u0, u1, u2, v0, v1, v2);
    }

    public void setSlices(final int u, final int v, final int u0, final int u1, final int u2, final int v0, final int v1, final int v2) {
        this.u = u;
        this.v = v;

        this.setSlices(u0, u1, u2, v0, v1, v2);
    }

    public void setSlices(final int u0, final int u1, final int u2, final int v0, final int v1, final int v2) {
        this.corners[2][3][0] = this.corners[2][1][0] = this.corners[0][3][0] = this.corners[0][1][0] = u0;
        this.corners[3][2][0] = this.corners[3][0][0] = this.corners[1][2][0] = this.corners[1][0][0] = u1;
        this.corners[3][3][0] = this.corners[3][1][0] = this.corners[1][3][0] = this.corners[1][1][0] = u2;
        this.corners[1][3][1] = this.corners[1][2][1] = this.corners[0][3][1] = this.corners[0][2][1] = v0;
        this.corners[3][1][1] = this.corners[3][0][1] = this.corners[2][1][1] = this.corners[2][0][1] = v1;
        this.corners[3][3][1] = this.corners[3][2][1] = this.corners[2][3][1] = this.corners[2][2][1] = v2;

        this.middles[4][2][0] = this.middles[4][0][0] = this.middles[2][2][0] = this.middles[2][0][0] = this.middles[1][3][0] = this.middles[1][1][0] = this.middles[0][2][0] = this.middles[0][0][0] = u0;
        this.middles[4][3][0] = this.middles[4][1][0] = this.middles[3][2][0] = this.middles[3][0][0] = this.middles[2][3][0] = this.middles[2][1][0] = this.middles[0][3][0] = this.middles[0][1][0] = u1;
        this.middles[3][3][0] = this.middles[3][1][0] = u2;
        this.middles[3][1][1] = this.middles[3][0][1] = this.middles[2][1][1] = this.middles[2][0][1] = this.middles[1][1][1] = this.middles[1][0][1] = this.middles[0][3][1] = this.middles[0][2][1] = v0;
        this.middles[4][1][1] = this.middles[4][0][1] = this.middles[3][3][1] = this.middles[3][2][1] = this.middles[2][3][1] = this.middles[2][2][1] = this.middles[1][3][1] = this.middles[1][2][1] = v1;
        this.middles[4][3][1] = this.middles[4][2][1] = v2;
    }
}
