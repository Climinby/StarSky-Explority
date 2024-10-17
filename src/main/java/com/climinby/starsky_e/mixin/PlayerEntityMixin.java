package com.climinby.starsky_e.nbt.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class PlayerEntityMixin implements SSEDataHandler {
    private final NbtCompound customNbt = new NbtCompound();
    private static final String SSE_NBT_KEY = "SSENbt";

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeSSEDataToNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        nbt.put(SSE_NBT_KEY, customNbt);
    }

    @Inject(method = "readNbt", at = @At("RETURN"))
    private void readSSEDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains(SSE_NBT_KEY)) {
            customNbt.copyFrom(nbt.getCompound(SSE_NBT_KEY));
        }
    }

    public NbtCompound getCustomNbt() {
        return customNbt;
    }

    @Override
    public NbtCompound getSSEData() {
        return customNbt;
    }

    @Override
    public void setSSEData(NbtCompound nbt) {
        customNbt.copyFrom(nbt);
    }
}
