package com.github.menx.autoresetmodssg.mixin;

import java.io.*;
import java.util.OptionalLong;
import com.github.menx.autoresetmodssg.AutoResetSSG;
import net.minecraft.client.gui.screen.world.MoreOptionsDialog;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoreOptionsDialog.class)
public abstract class MoreOptionsDialogMixin {
    @Shadow
    private TextFieldWidget seedTextField;

    // seedTextField is null after <init>
    @Inject(method = "setVisible", at = @At("TAIL"))
    private void autoStartMixin(CallbackInfo info) {
        // If auto reset mode is on, set the seed
        if (AutoResetSSG.isPlaying) {
            seedTextField.setText(String.valueOf(AutoResetSSG.seed));
        }
    }
}
