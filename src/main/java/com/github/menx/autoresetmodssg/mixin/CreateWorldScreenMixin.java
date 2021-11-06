package com.github.menx.autoresetmodssg.mixin;

import com.github.menx.autoresetmodssg.AutoResetSSG;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.world.Difficulty;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @Shadow
    private Difficulty currentDifficulty;
    @Shadow
    private TextFieldWidget levelNameField;

    @Shadow
    protected abstract void setMoreOptionsOpen(boolean moreOptionsOpen);
    @Shadow
    protected abstract void createLevel();

    @Inject(method = "init", at = @At("TAIL"))
    private void autoStartMixin(CallbackInfo info) {
        // If auto reset mode is on, set difficulty to easy, set world options and create world.
        if (AutoResetSSG.isPlaying) {
            currentDifficulty = Difficulty.EASY;
            int attemptId = AutoResetSSG.getNextAttempt();
            AutoResetSSG.LOGGER.log(Level.INFO, "Creating world #" + attemptId + " with seed: " + AutoResetSSG.seed);
            levelNameField.setText("Set seed speedrun #" + attemptId);
            setMoreOptionsOpen(true);
            setMoreOptionsOpen(false);
            createLevel();
        }
    }
}
