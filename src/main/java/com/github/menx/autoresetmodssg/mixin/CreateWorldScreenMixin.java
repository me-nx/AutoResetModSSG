package com.github.menx.autoresetmodssg.mixin;

import com.github.menx.autoresetmodssg.AutoResetSSG;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @Shadow
    private TextFieldWidget levelNameField;
    @Shadow
    private TextFieldWidget seedField;

    @Shadow
    protected abstract void createLevel();

    @Inject(method = "init", at = @At("TAIL"))
    private void autoStartMixin(CallbackInfo info) {
        // If auto reset mode is on, set difficulty to easy, set world options and create world.
        if (AutoResetSSG.isPlaying && !AutoResetSSG.isCreatingWorld) {
            AutoResetSSG.isCreatingWorld = true;
            levelNameField.setText("Set seed speedrun #" + AutoResetSSG.getNextAttempt());
            seedField.setText(String.valueOf(AutoResetSSG.seed));
            createLevel();
        }
    }
}
