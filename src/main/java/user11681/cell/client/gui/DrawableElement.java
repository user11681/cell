package user11681.cell.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.texture.TextureManager;

@Environment(EnvType.CLIENT)
public interface DrawableElement extends Drawable, Element, TickableElement {
    TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
}
