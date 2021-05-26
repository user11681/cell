package user11681.cell;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cell {
    public static final Minecraft client = Minecraft.getInstance();
    public static final TextureManager textureManager = client.getTextureManager();

    public static final String ID = "cell";

    public static final Logger logger = LogManager.getLogger();

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ID, path);
    }
}
