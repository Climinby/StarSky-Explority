package com.climinby.starsky_e.entity;

import com.climinby.starsky_e.StarSkyExplority;
import com.climinby.starsky_e.sound.SSESoundEvents;
import com.climinby.starsky_e.util.SSENetworkingConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

public class LunarianEntity extends PathAwareEntity {
    private int sightLockCoolDown = 0;
    private LivingEntity attacker = null;
    private boolean isInWater = false;
    private boolean isFloating = false;
    private boolean isDiving = false;
    private boolean isBreathing = false;
    private int depth = 0;
    private BlockPos prePos = this.getBlockPos();
    private int maxSubmergeDepth = 42;
    private boolean canTeleport = false;
    private int teleportCoolDown = 80;
    private int attackCoolDown = 0;

    protected LunarianEntity(EntityType<? extends LunarianEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.attackCoolDown != 0) this.attackCoolDown--;
        if(this.attacker != null && this.sightLockCoolDown != 0) this.sightLockCoolDown--;
        else this.sightLockCoolDown = 100;
        this.getAir();

        if(this.attacker instanceof PlayerEntity playerTarget) {
            if(playerTarget.isCreative() || playerTarget.isSpectator()) {
                this.attacker = null;
                this.setTarget(null);
            }
        }
        if(this.attacker != null && !this.isInRange(this.attacker, 96.0)) {
            this.attacker = null;
            this.setTarget(null);
        }
        if(this.attacker != null) {
            Item offHandItem = this.attacker.getOffHandStack().getItem();
            Item mainHandItem = this.attacker.getMainHandStack().getItem();
            this.canTeleport = offHandItem == Items.SHIELD || mainHandItem == Items.SHIELD;
            if (this.getTarget() != this.attacker) {
                this.setTarget(this.attacker);
            }
        }

        if(this.getTarget() != null) {
            LivingEntity target = this.getTarget();
            if(this.attackCoolDown == 0) {
                tryAttack(target);
                this.attackCoolDown = 15;
            }

            if(this.getHealth() > 10 && this.sightLockCoolDown == 0) {
                int random = new Random().nextInt(3);
                if (random < 2) {
                    this.sightLock(target);
                }
                this.sightLockCoolDown = 100;
            }
        }

        if(this.attacker != null && this.attacker.isDead()) {
            this.attacker = null;
            this.setTarget(null);
        }

        //Projectile Reflecting
        List<ProjectileEntity> projectiles = this.getEntityWorld().getEntitiesByClass(ProjectileEntity.class, this.getBoundingBox().expand(2.5),
                projectileEntity -> true);
        this.projectileBounce(projectiles);

        //In-water Conduction
        if(this.isTouchingWater()) {
            this.isInWater = true;
        } else {
            this.isInWater = false;
        }
        if(this.isInWater) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 30, 2, false, false, false));
        } else {
            StatusEffectInstance dolphinsGrace = this.getStatusEffect(StatusEffects.DOLPHINS_GRACE);
            if(dolphinsGrace != null && !dolphinsGrace.isAmbient()) {
                this.removeStatusEffect(StatusEffects.DOLPHINS_GRACE);
            }
        }
        if(this.isTouchingWater() && this.getTarget() != null && this.getTarget().isTouchingWater()) {
            LivingEntity target = this.getTarget();
            if (!this.getBlockPos().equals(this.prePos)) {
                int depth = 0;
                BlockPos pos = this.getBlockPos().add(0, 1, 0);
                BlockState blockState = this.getWorld().getBlockState(pos);
                while (!(blockState.getBlock() instanceof AirBlock)) {
                    depth++;
                    pos = pos.add(0, 1, 0);
                    blockState = this.getWorld().getBlockState(pos);
                }
                this.depth = depth;
                this.maxSubmergeDepth = (int) ((double) this.getAir() * 0.3);
                if (depth >= maxSubmergeDepth - 1) {
                    this.setTarget(null);
                    isDiving = false;
                    isFloating = true;
                }
            }
            if (!this.isSubmergedInWater() && isFloating) {
                this.isBreathing = true;
                this.depth = 0;
                isFloating = false;
            }
            if(!this.isFloating) {
                if (!this.isBreathing && target.getPos().subtract(this.getPos()).getY() < 0.0) {
                    if (!(depth >= maxSubmergeDepth - 1)) {
                        this.isDiving = true;
                    }
                } else {
                    isDiving = false;
                }
            }
            this.updateWaterState();
        } else {
            this.isDiving = false;
            this.isFloating = false;
            this.updateWaterState();
        }
        if(this.isBreathing) this.isBreathing = !(this.getAir() == this.getMaxAir());
        if(this.getTarget() != null) {
            if(this.isTouchingWater()) {
                this.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, this.getTarget().getEyePos());
                if(this.isSubmergedInWater()) {
                    this.setSwimming(true);
                }
            }
        }

        //Teleport
        if(canTeleport && canTeleportToBack()) {
            if(this.teleportCoolDown == 0) {
                this.teleportation();
                this.teleportCoolDown = 80;
            } else {
                this.teleportCoolDown--;
            }
        }

        this.prePos = this.getBlockPos();
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
                    if(!attackingPlayer.isCreative() && !attackingPlayer.isSpectator()) {
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
        if (this.isInRange(target, 2.9)) target.damage(this.getDamageSources().mobAttack(this), damage);
        return true;
    }

    @Override
    protected Identifier getLootTableId() {
        return new Identifier(StarSkyExplority.MOD_ID, "entities/lunarian");
    }

    @Override
    protected void dropXp() {
        super.dropXp();
    }

    @Override
    public int getXpToDrop() {
        super.getXpToDrop();
        int rand = new Random().nextInt(10);
        if(rand < 1) return 4;
        else if(rand < 3) return 5;
        else if(rand < 7) return 6;
        else if(rand < 9) return 7;
        return 8;
    }

    private void sightLock(LivingEntity target) {
        target.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, this.getEyePos());
        if(!this.getWorld().isClient()) {
            if (target instanceof ServerPlayerEntity playerTarget) {
                Identifier soundId = Registries.SOUND_EVENT.getId(SSESoundEvents.ENTITY_LUNARIAN_SIGHT_LOCK);
                sendSoundOutput(playerTarget, soundId, SoundCategory.HOSTILE, 2.0F, 1.0F);
            }
        }
    }

    private void projectileBounce(Entity projectile) {
        if(isProjectile(projectile)) {
            double velocity = projectile.getVelocity().length();
            Vec3d pos = this.getEyePos();
            Vec3d projPos = projectile.getPos();
            Vec3d unit = projPos.subtract(pos).normalize();
            Vec3d newVelocity = unit.multiply(velocity);
            projectile.setVelocity(newVelocity);
        }
    }

    private void projectileBounce(List<? extends Entity> projectiles) {
        for(Entity projectile : projectiles) {
            this.projectileBounce(projectile);
        }
    }

    private boolean isProjectile(Entity entity) {
        return entity instanceof ProjectileEntity;
    }

    @Override
    protected boolean updateWaterState() {
        boolean b = super.updateWaterState();
        if(this.isTouchingWater()) {
            if (this.isDiving) {
                this.addVelocity(0.0, -0.04, 0.0);
            } else if (this.isFloating) {
                this.addVelocity(0.0, 0.01, 0.0);
            }
        }
        return b;
    }

    private boolean canTeleportToBack() {
        if(this.attacker != null) {
            Vec3d attackerPos = this.attacker.getPos();
            double rYaw = this.attacker.getYaw() * Math.PI / 180.0;
            Vec3d facingDir = new Vec3d(-Math.sin(rYaw), 0, Math.cos(rYaw));
            Vec3d backPos = attackerPos.subtract(facingDir);
            BlockPos backBlockPos = new BlockPos(
                    (int) Math.round(backPos.getX()),
                    (int) Math.round(backPos.getY()) + 1,
                    (int) Math.round(backPos.getZ())
            );
            BlockState backBlock = this.getWorld().getBlockState(backBlockPos);
            return !backBlock.shouldSuffocate(this.getWorld(), backBlockPos);
        }
        return false;
    }

    private void teleportation() {
        Vec3d attackerPos = this.attacker.getPos();
        double rYaw = this.attacker.getYaw() * Math.PI / 180;
        Vec3d facingDir = new Vec3d(-Math.sin(rYaw), 0, Math.cos(rYaw));
        Vec3d desPos = attackerPos.subtract(facingDir);
        Identifier soundId = Registries.SOUND_EVENT.getId(SSESoundEvents.ENTITY_LUNARIAN_TELEPORT);
        if(!this.getWorld().isClient()) {
            for (PlayerEntity player : this.getWorld().getPlayers()) {
                sendSoundOutput((ServerPlayerEntity) player, soundId, SoundCategory.HOSTILE, 2.0F, 1.0F);
            }
        }
        this.teleport(desPos.x, desPos.y, desPos.z);
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
