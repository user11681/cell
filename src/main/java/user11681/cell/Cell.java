package user11681.cell;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cell {
    public static final MinecraftClient client = MinecraftClient.getInstance();
    public static final TextureManager textureManager = client.getTextureManager();

    public static final String ID = "cell";

    public static final Logger logger = LogManager.getLogger();

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }
}
