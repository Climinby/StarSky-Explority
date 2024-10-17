package com.climinby.starsky_e.attribute.entity;

import com.climinby.starsky_e.entity.SSEEntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;

public class SSEEntityDefaultAttributes {
    public static void init() {
        FabricDefaultAttributeRegistry.register(SSEEntities.TSUKI_NO_TAMI_ENTITY, MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.5)
        );
    }
}
