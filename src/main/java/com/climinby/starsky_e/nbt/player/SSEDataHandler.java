package com.climinby.starsky_e.nbt.player;

import net.minecraft.nbt.NbtCompound;

public interface SSEDataHandler {
    NbtCompound getSSEData();
    void setSSEData(NbtCompound nbt);
}
