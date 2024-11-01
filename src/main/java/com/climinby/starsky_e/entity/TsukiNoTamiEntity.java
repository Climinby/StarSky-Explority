package com.climinby.starsky_e.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.Inject;

public class TsukiNoTamiEntity extends PathAwareEntity {
    private PlayerEntity attacker;

    protected TsukiNoTamiEntity(EntityType<? extends TsukiNoTamiEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getTarget() != null) {
            if(this.getTarget() instanceof PlayerEntity playerTarget) {
                if(playerTarget.isCreative()) this.setTarget(null);
            }
            tryAttack(this.getTarget());
            if(!this.isInRange(this.getTarget(), 128.0)) this.setTarget(null);
        } else {
            if(this.attacker != null) {
                if(this.attacker.isCreative()) {
                    this.attacker = null;
                } else {
                    this.setTarget(attacker);
                }
            }
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
                    if(!attackingPlayer.isCreative()) {
                        this.attacker = attackingPlayer;
                    }
                } else if(attackerEntity instanceof LivingEntity attacker) {
                    this.setAttacker(attacker);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.5, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 2, false, false, this::isPlayerAttacker));
    }

    private boolean isPlayerAttacker(LivingEntity entity) {
        return entity == attacker;
    }

    @Override
    public boolean tryAttack(Entity target) {
        float damage = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (this.isInRange(target, 2.5)) target.damage(this.getDamageSources().mobAttack(this), damage);
        return true;
    }
}
