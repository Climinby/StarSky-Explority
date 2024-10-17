package com.climinby.starsky_e.item;

import com.climinby.starsky_e.block.SSEBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class CollectorItem extends ToolItem {
    private final ToolMaterial material;

    public CollectorItem(ToolMaterial material, Settings settings) {
        super(material, settings);
        this.material = material;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        Block block = state.getBlock();
        return block == Blocks.SAND || block == Blocks.RED_SAND
                || block == Blocks.DIRT || block == Blocks.GRASS_BLOCK
                || block == Blocks.GRAVEL || block == SSEBlocks.MOON_SOIL;
    }
    public Item getRegionOf(BlockState state) {
        Block block = state.getBlock();
        if(block == Blocks.SAND || block == Blocks.RED_SAND
                || block == Blocks.DIRT || block == Blocks.GRASS_BLOCK
                || block == Blocks.GRAVEL
        ) {
            return SSEItems.SAMPLE_EARTH;
        }
        if(block == SSEBlocks.MOON_SOIL) {
            return SSEItems.SAMPLE_MOON;
        }
        return SSEItems.SAMPLE_EMPTY;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack itemStack, BlockState state) {
        if (isSuitableFor(state)) {
            if(material == ToolMaterials.IRON) {
                return 0.5F;
            } else if(material == ToolMaterials.DIAMOND) {
                return 0.85F;
            }
            return 1.2F;
        }
        return super.getMiningSpeedMultiplier(itemStack, state);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient()) {
            if (isSuitableFor(state)) {
                world.getServer().execute(() -> {
                    List<ItemEntity> droppedItems = world.getEntitiesByClass(
                            ItemEntity.class, new Box(pos), item -> true
                    );
                    for(ItemEntity itemEntity : droppedItems) {
                        itemEntity.discard();
                    }
                    if(new Random().nextInt(20) < 2) {
                        ItemStack droppedSample = new ItemStack(getRegionOf(state));
                        Block.dropStack(world, pos, droppedSample);
                    }
                    stack.damage(1, miner, (entity) -> entity.sendToolBreakStatus(miner.getActiveHand()));
                });
                return true;
            } else {
                stack.damage(1, miner, (entity) -> entity.sendToolBreakStatus(miner.getActiveHand()));
                super.postMine(stack, world, state, pos, miner);
                return true;
            }
        }
        return true;
    }
}

