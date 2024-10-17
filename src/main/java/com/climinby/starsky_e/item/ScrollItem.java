package com.climinby.starsky_e.item;

import com.climinby.starsky_e.material.MaterialType;
import com.climinby.starsky_e.nbt.player.ResearchLevel;
import com.climinby.starsky_e.registry.SSERegistries;
import com.climinby.starsky_e.util.SSENetworkingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.io.Serializable;

public class ScrollItem extends Item implements Serializable {
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
        if(!world.isClient()) {
            ItemStack copiedMainHandItem = user.getMainHandStack().copy();
            int count = copiedMainHandItem.getCount();
            for(MaterialType material : SSERegistries.MATERIAL_TYPE) {
                if(material.getScrollItem().equals(this)) {
                    float level = ResearchLevel.getLevel(user, material);
                    if(level < 100.0F) {
                        ResearchLevel.addLevel(user, material, this.researchIncreasement * 100.0F);
                        sendSoundOutput((ServerPlayerEntity) user, Registries.SOUND_EVENT.getId(SoundEvents.ENTITY_BAT_TAKEOFF), SoundCategory.PLAYERS, 1.0F, 1.0F);
                        if(copiedMainHandItem.getItem() instanceof ScrollItem) {
                            copiedMainHandItem.setCount(count - 1);
                            user.setStackInHand(Hand.MAIN_HAND, copiedMainHandItem);
                        }
                    } else {
                        sendSoundOutput((ServerPlayerEntity) user, Registries.SOUND_EVENT.getId(SoundEvents.ENTITY_ENDERMAN_TELEPORT), SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    private static void sendSoundOutput(ServerPlayerEntity player, Identifier soundId, SoundCategory soundCategory, float volume, float pitch) {
        int soundCate = 0;
        switch(soundCategory) {
            case MASTER:
                break;
            case MUSIC:
                soundCate = 1;
                break;
            case RECORDS:
                soundCate = 2;
                break;
            case WEATHER:
                soundCate = 3;
                break;
            case BLOCKS:
                soundCate = 4;
                break;
            case HOSTILE:
                soundCate = 5;
                break;
            case NEUTRAL:
                soundCate = 6;
                break;
            case PLAYERS:
                soundCate = 7;
                break;
            case AMBIENT:
                soundCate = 8;
                break;
            case VOICE:
                soundCate = 9;
                break;
        }
        ServerPlayNetworking.send(player, SSENetworkingConstants.DATA_SOUND_TRIGGER,
                PacketByteBufs.create()
                        .writeIdentifier(soundId)
                        .writeInt(soundCate)
                        .writeFloat(volume)
                        .writeFloat(pitch)
        );
    }
}
