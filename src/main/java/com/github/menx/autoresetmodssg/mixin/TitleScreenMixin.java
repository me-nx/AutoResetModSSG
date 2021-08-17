package com.github.menx.autoresetmodssg.mixin;

import com.github.menx.autoresetmodssg.AutoResetSSG;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    private static final Identifier IRON_BOOTS = new Identifier("textures/item/iron_boots.png");
    private static final int Y_OFFSET = 72;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void initMixin(CallbackInfo info) {
        // If auto reset mode is on, instantly switch to create world menu.
        if (AutoResetSSG.isPlaying) {
            client.openScreen(new CreateWorldScreen(this));
        } else if (!this.client.isDemo()) {
            // Add new button for starting auto resets.
            int y = this.height / 4 + Y_OFFSET;
            this.addButton(new ButtonWidget(this.width / 2 - 124, y, 20, 20, new LiteralText(""), (buttonWidget) -> {
                AutoResetSSG.isPlaying = true;
                client.openScreen(new CreateWorldScreen(this));
            }));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void ironBootsOverlayMixin(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int y = this.height / 4 + Y_OFFSET;
        this.client.getTextureManager().bindTexture(IRON_BOOTS);
        drawTexture(matrices,(width/2)-122,y+2,0.0F,0.0F,16,16,16,16);
    }
}
