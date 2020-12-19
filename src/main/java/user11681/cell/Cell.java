package user11681.cell;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cell {
    public static final MinecraftClient client = MinecraftClient.getInstance();

    public static final String ID = "cell";
    public static final String NAME = "cell";

    public static final Logger logger = LogManager.getLogger();

    public static Identifier id(final String path) {
        return new Identifier(ID, path);
    }
}