package com.climinby.starsky_e.entity;

import com.climinby.starsky_e.sound.SSESoundEvents;
import com.climinby.starsky_e.util.SSENetworkingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.Random;

public class TsukiNoTamiEntity extends PathAwareEntity {
    private int sightLockFreezing = 0;
    private LivingEntity attacker = null;

    protected TsukiNoTamiEntity(EntityType<? extends TsukiNoTamiEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.sightLockFreezing != 0) this.sightLockFreezing--;

        if(this.attacker instanceof PlayerEntity playerTarget) {
            if(playerTarget.isCreative()) this.attacker = null;
        }
        if(this.attacker != null && !this.isInRange(this.attacker, 128.0)) this.attacker = null;
        if(this.getTarget() != this.attacker) {
            this.setTarget(this.attacker);
        }

        if(this.getTarget() != null) {
            LivingEntity target = this.getTarget();
            tryAttack(target);

            if(this.getHealth() > 10 && this.sightLockFreezing == 0) {
                int random = new Random().nextInt(3);
                if (random < 2) {
                    this.sightLock(target);
                }
                this.sightLockFreezing = 100;
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
                    this.attacker = attacker;
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
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 96.0F));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 2, false, false, this::isPlayerAttacker));
    }

    private boolean isPlayerAttacker(LivingEntity entity) {
        return entity.equals(this.attacker);
    }

    @Override
    public boolean tryAttack(Entity target) {
        float damage = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (this.isInRange(target, 2.5)) target.damage(this.getDamageSources().mobAttack(this), damage);
        return true;
    }

    private void sightLock(LivingEntity target) {
        target.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, this.getEyePos());
        if(!this.getWorld().isClient()) {
            if (target instanceof ServerPlayerEntity playerTarget) {
                Identifier soundId = Registries.SOUND_EVENT.getId(SSESoundEvents.ENTITY_TSUKI_NO_TAMI_SIGHT_LOCK);
                sendSoundOutput(playerTarget, soundId, SoundCategory.HOSTILE, 1.0F, 1.0F);
            }
        }
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
