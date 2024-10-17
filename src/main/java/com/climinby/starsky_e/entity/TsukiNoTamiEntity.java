package com.climinby.starsky_e.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class TsukiNoTamiEntity extends PathAwareEntity {
    protected TsukiNoTamiEntity(EntityType<? extends TsukiNoTamiEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void handleStatus(byte status) {
        if(status == 2) {
            this.hurtTime = 10;
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(super.damage(source, amount)) {
            this.getWorld().sendEntityStatus(this, (byte) 2);
            return true;
        }
        return false;
    }
}
