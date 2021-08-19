package com.github.menx.autoresetmodssg.mixin;

import com.github.menx.autoresetmodssg.AutoResetSSG;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
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
            if (AutoResetSSG.isCreatingWorld)
                AutoResetSSG.isCreatingWorld = false;
            else
                minecraft.openScreen(new CreateWorldScreen(this));
        } else if (!this.minecraft.isDemo()) {
            // Add new button for starting auto resets.
            int y = this.height / 4 + Y_OFFSET;
            this.addButton(new ButtonWidget(this.width / 2 - 124, y, 20, 20, "", (buttonWidget) -> {
                AutoResetSSG.isPlaying = true;
                AutoResetSSG.LOGGER.log(Level.INFO, "Auto reset (ssg): on");
                minecraft.openScreen(new CreateWorldScreen(this));
            }));
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void ironBootsOverlayMixin(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int y = this.height / 4 + Y_OFFSET;
        this.minecraft.getTextureManager().bindTexture(IRON_BOOTS);
        blit((width/2)-122,y+2,0.0F,0.0F,16,16,16,16);
    }
}
