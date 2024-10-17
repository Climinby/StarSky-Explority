package com.climinby.starsky_e.block.entity;

import com.climinby.starsky_e.ImplementedInventory;
import com.climinby.starsky_e.SSENetworkingConstants;
import com.climinby.starsky_e.block.InkType;
import com.climinby.starsky_e.entity.SSEBlockEntities;
import com.climinby.starsky_e.item.SampleItem;
import com.climinby.starsky_e.recipe.AnalysisRecipe;
import com.climinby.starsky_e.recipe.AnalysisResult;
import com.climinby.starsky_e.registry.SSERegistries;
import com.climinby.starsky_e.screen.AnalyzerScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnalyzerBlockEntity extends BlockEntity implements
        NamedScreenHandlerFactory, ImplementedInventory, SidedInventory, ExtendedScreenHandlerFactory/*,
        BlockEntityTicker<AnalyzerBlockEntity>*/ {
    public static final List<InkType> INK_TYPES = new ArrayList<>();
    public static final String IS_PREVIEW_KEY = "is_preview";
    public static final String INK_KEY = "Ink";
    public static final String INK_TYPE_KEY = "InkType";
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(8, ItemStack.EMPTY);
    private int ink;
    private Item inkType;

    public AnalyzerBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 0, Items.INK_SAC);
    }
    public AnalyzerBlockEntity(BlockPos pos, BlockState state, int ink, Item inkType) {
        super(SSEBlockEntities.ANALYZER_BLOCK_ENTITY, pos, state);
        this.ink = ink;
        this.inkType = inkType;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.starsky_explority.analyzer");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
        ink = nbt.getInt(INK_KEY);
        Identifier itemId = new Identifier(nbt.getString(INK_TYPE_KEY));
        for(InkType containedInkType : INK_TYPES) {
            if(Registries.ITEM.getId(containedInkType.getItem()).equals(itemId)) {
                inkType = containedInkType.getItem();
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
        nbt.putInt(INK_KEY, ink);
        nbt.putString(INK_TYPE_KEY, Registries.ITEM.getId(inkType).toString());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        sendInk();
        sendInkType();
        return new AnalyzerScreenHandler(syncId, playerInventory, this);
    }

    public static void tick(World world, BlockPos pos, BlockState state, AnalyzerBlockEntity analyzer) {
        if(!world.isClient()) {
            ItemStack itemStack = analyzer.items.get(1);
            if(!itemStack.isEmpty()) {
                for(InkType containedInkType : INK_TYPES) {
                    if(itemStack.getItem() == containedInkType.getItem()) {
                        if(analyzer.ink <= 100 - containedInkType.getIncre()) {
                            analyzer.addInk((containedInkType.getIncre()));
                            int count = itemStack.getCount();
                            if(count == 1) {
                                analyzer.items.set(1, ItemStack.EMPTY);
                            } else {
                                itemStack.setCount(count - 1);
                                analyzer.items.set(1, itemStack);
                            }
                        }
                    }
                }
            }

            itemStack = analyzer.items.get(0);
            if(itemStack.isEmpty()) {
                for(int i = 0; i < 5; i++) {
                    analyzer.items.set(3 + i, ItemStack.EMPTY);
                }
            }
            analyzer.sendInk();
            analyzer.sendInkType();
            analyzer.sendCurrentSample();
        }
    }

    @Override
    public int size() {
        return ImplementedInventory.super.size();
    }

    @Override
    public boolean isEmpty() {
        return ImplementedInventory.super.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return ImplementedInventory.super.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int count) {
        return ImplementedInventory.super.removeStack(slot, count);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return ImplementedInventory.super.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ImplementedInventory.super.setStack(slot, stack);
    }

    @Override
    public void clear() {
        ImplementedInventory.super.clear();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return ImplementedInventory.super.canPlayerUse(player);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[] {0, 1, 2};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if(!(dir == Direction.DOWN)) {
            if(slot == 0) {
                for (AnalysisRecipe analysisRecipe : SSERegistries.ANALYSIS_RECIPE) {
                    if (stack.getItem() == analysisRecipe.getMaterial()) {
                        int count = 0;
                        for(AnalysisResult result : analysisRecipe.getResults()) {
                            items.set(3 + count, result.getItemStack());
                            if(count == analysisRecipe.getResults().size() - 1) {
                                return true;
                            }
                            count++;
                        }
                    }
                }
            }
            if(slot == 1) {
                for(InkType inkType : INK_TYPES) {
                    if(stack.isOf(inkType.getItem())) {
                        if(stack.isOf(this.inkType)) {
                            return true;
                        } else if(ink == 0) {
                            setInkType(stack.getItem());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if(dir == Direction.DOWN) {
            if(slot == 2) return true;
        }
        return false;
    }

    public void setInk(int ink) {
        if(ink >= 100 || ink < 0) return;
        this.ink = ink;
        markDirty();
        sendInk();
    }

    public void addInk(int ink) {
        if(ink >= 100 || ink < 0) return;
        if(this.ink + ink >= 100) this.ink = 100;
        else setInk(this.ink + ink);
        markDirty();
        sendInk();
    }

    public void consumeInk(int ink) {
        if(ink >= 100 || ink < 0) return;
        if(this.ink - ink < 0) return;
        this.ink = this.ink - ink;
        markDirty();
        sendInk();
    }

    public int getInk() {
        return ink;
    }

    public void setInkType(Item newInkType) {
        for(InkType inkType : INK_TYPES) {
            if(newInkType == inkType.getItem()) {
                this.inkType = newInkType;
                sendInkType();
            }
        }
        markDirty();
    }

    public Item getInkType() {
        return inkType;
    }

    public static void registerInkType(Item newType, int increasement, Identifier texture) {
        if(increasement <= 0 || increasement > 100) {
            throw new RuntimeException("The increasement of a registering ink type must be between 0 and 100 (cannot be 0)");
        }

        for(InkType containedType : INK_TYPES) {
            if(newType == containedType.getItem()) {
                containedType.setIncre(increasement);
                return;
            }
        }
        INK_TYPES.add(new InkType(newType, increasement, texture));
    }

    public void sendInk() {
        for(ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, pos)) {
            ServerPlayNetworking.send(player, SSENetworkingConstants.DATA_ANALYZER_INK, PacketByteBufs.create().writeInt(ink).writeBlockPos(pos));
        }
    }

    public void sendInkType() {
        for(ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, pos)) {
            ServerPlayNetworking.send(player, SSENetworkingConstants.DATA_ANALYZER_INK_TYPE, PacketByteBufs.create().writeItemStack(new ItemStack(inkType)).writeBlockPos(pos));
        }
    }

    public void sendCurrentSample() {
        for(ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, pos)) {
            ServerPlayNetworking.send(player, SSENetworkingConstants.DATA_ANALYZER_CURRENT_SAMPLE, PacketByteBufs.create().writeItemStack(items.get(0)).writeBlockPos(pos));
        }
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }
}
