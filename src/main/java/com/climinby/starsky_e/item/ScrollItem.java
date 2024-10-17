package com.climinby.starsky_e.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ScrollItem extends Item {
    private final Item researchedItem;
    private final float researchIncreasement;

    public ScrollItem(Settings settings) {
        super(settings);
        researchedItem = null;
        researchIncreasement = 0F;
    }
    public ScrollItem(Item researchedItem, float researchIncreasement, Settings settings) {
        super(settings);
        this.researchedItem = researchedItem;
        this.researchIncreasement = researchIncreasement;
    }

    public Item getResearchedItem() {
        return researchedItem;
    }

    public float getResearchIncreasement() {
        return researchIncreasement;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }
}
