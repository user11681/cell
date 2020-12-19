package user11681.cell.asm.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import user11681.cell.asm.TestScreen;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
abstract class TestMixin extends Screen {
    private TestMixin(final Text title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void hijackScreen(final CallbackInfo info) {
        this.client.openScreen(new TestScreen());
    }
}
