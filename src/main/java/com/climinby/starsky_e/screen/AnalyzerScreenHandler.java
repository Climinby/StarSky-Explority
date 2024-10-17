package com.climinby.starsky_e.screen;

import com.climinby.starsky_e.SSENetworkingConstants;
import com.climinby.starsky_e.block.InkType;
import com.climinby.starsky_e.block.entity.AnalyzerBlockEntity;
import com.climinby.starsky_e.item.SampleItem;
import com.climinby.starsky_e.recipe.AnalysisRecipe;
import com.climinby.starsky_e.recipe.AnalysisResult;
import com.climinby.starsky_e.registry.SSERegistries;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnalyzerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private BlockPos pos;

    public AnalyzerScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, new SimpleInventory(8));
        pos = buf.readBlockPos();
    }
    public AnalyzerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(SSEScreenHandlers.ANALYZER_SCREEN_HANDLER, syncId);
        checkSize(inventory, 8);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 80, -4){
            @Override
            public boolean canInsert(ItemStack itemStack) {
                for (AnalysisRecipe analysisRecipe : SSERegistries.ANALYSIS_RECIPE) {
                    if (itemStack.getItem() == analysisRecipe.getMaterial()) {
                        int count = 0;
                        for (AnalysisResult result : analysisRecipe.getResults()) {
                            inventory.setStack(3 + count, result.getItemStack());
                            if (count == analysisRecipe.getResults().size() - 1) {
                                return true;
                            }
                            count++;
                        }
                    }
                }

                if(inventory instanceof AnalyzerBlockEntity) {
                    AnalyzerBlockEntity analyzer = (AnalyzerBlockEntity) inventory;
                    analyzer.sendCurrentSample();
                }
                return false;
            }

            @Override
            public boolean canTakeItems(PlayerEntity player) {
                for(int i = 0; i < 5; i++) {
                    inventory.setStack(3 + i, ItemStack.EMPTY);
                }

                if(inventory instanceof AnalyzerBlockEntity) {
                    AnalyzerBlockEntity analyzer = (AnalyzerBlockEntity) inventory;
                    analyzer.sendCurrentSample();
                }
                return true;
            }
        });
        this.addSlot(new Slot(inventory, 1, 144, -4){
            @Override
            public boolean canInsert(ItemStack itemStack) {
                if(inventory instanceof AnalyzerBlockEntity) {
                    AnalyzerBlockEntity analyzer = (AnalyzerBlockEntity) inventory;
                    for(InkType containedInkType : AnalyzerBlockEntity.INK_TYPES) {
                        if(itemStack.isOf(containedInkType.getItem())) {
                            if(analyzer.getInk() == 0) {
                                analyzer.setInkType(containedInkType.getItem());
                                return true;
                            } else if(itemStack.isOf(analyzer.getInkType())) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        });
        this.addSlot(new Slot(inventory, 2, 80, 50){
            @Override
            public boolean canInsert(ItemStack itemStack) { return false; }
        });
        for(int i = 0; i < 5; i++) {
            this.addSlot(new Slot(inventory, 3 + i, 44 + i * 18, 23){
                @Override
                public boolean canTakeItems(PlayerEntity player) { return false; }
                @Override
                public boolean canInsert(ItemStack itemStack) { return false; }
            });
        }
        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse((player));
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if(slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if(invSlot < this.inventory.size()) {
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if(!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if(originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public BlockPos getPos() {
        return pos;
    }
}
