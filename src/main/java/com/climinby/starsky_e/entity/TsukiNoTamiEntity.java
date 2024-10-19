package com.climinby.starsky_e.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TsukiNoTamiEntity extends PathAwareEntity implements Angerable {
    private int angerTime = 0;
    private UUID targetUuid = null;

    protected TsukiNoTamiEntity(EntityType<? extends TsukiNoTamiEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.hasAngerTime()) {
            angerTime--;
        }
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(super.damage(source, amount)) {
            Entity attackerEntity = source.getAttacker();
            if(attackerEntity != null) {
                if(attackerEntity instanceof PlayerEntity attackingPlayer) {
                    this.setAttacking(attackingPlayer);
                    this.setAttacker(attackingPlayer);
                } else if(attackerEntity instanceof LivingEntity attacker) {
                    this.setAttacker(attacker);
                }
            }
            chooseRandomAngerTime();
            return true;
        }
        return false;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));

        this.targetSelector.add(1, new RevengeGoal(this, TsukiNoTamiEntity.class, RabbitEntity.class));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isPlayerAggressor));
        this.targetSelector.add(3, new UniversalAngerGoal<>(this, false));
    }

    private boolean isPlayerAggressor(LivingEntity entity) {
        return true;
    }

    @Override
    public int getAngerTime() {
        return angerTime;
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Override
    public @Nullable UUID getAngryAt() {
        return targetUuid;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.targetUuid = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.angerTime = this.random.nextInt(30) + 150;
    }
}
