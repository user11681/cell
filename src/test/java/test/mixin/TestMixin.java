package test.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import test.TestScreen;

@OnlyIn(Dist.CLIENT)
@Mixin(InventoryScreen.class)
abstract class TestMixin extends Screen {
    private TestMixin(ITextComponent title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void hijackScreen(CallbackInfo info) {
        this.minecraft.setScreen(new TestScreen());
    }
}
