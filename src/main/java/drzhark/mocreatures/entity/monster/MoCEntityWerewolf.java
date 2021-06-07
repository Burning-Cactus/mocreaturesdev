package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.registry.MoCItems;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityWerewolf extends MoCEntityMob {

    private boolean transforming;
    private int tcounter;
    private int textCounter;
    private static final DataParameter<Boolean> IS_HUMAN = EntityDataManager.defineId(MoCEntityWerewolf.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_HUNCHED = EntityDataManager.defineId(MoCEntityWerewolf.class, DataSerializers.BOOLEAN);
    
    public MoCEntityWerewolf(EntityType<? extends MoCEntityWerewolf> type, World world) {
        super(type, world);
        this.transforming = false;
        this.tcounter = 0;
        setHumanForm(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_HUMAN, Boolean.valueOf(false));
        this.entityData.define(IS_HUNCHED, Boolean.valueOf(false));
    }

    @Override
    public void setHealth(float par1) {
        if (this.getIsHumanForm() && par1 > 15F) {
            par1 = 15F;
        }
        super.setHealth(par1);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            int k = this.random.nextInt(100);
            if (k <= 28) {
                setType(1);
            } else if (k <= 56) {
                setType(2);
            } else if (k <= 85) {
                setType(3);
            } else {
                setType(4);
//                this.isImmuneToFire = true; TODO: Fire immunity
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (this.getIsHumanForm()) {
            return MoCreatures.getTexture("wereblank.png");
        }

        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("wolfblack.png");
            case 2:
                return MoCreatures.getTexture("wolfbrown.png");
            case 3:
                return MoCreatures.getTexture("wolftimber.png");
            case 4:
                if (!MoCConfig.CLIENT_CONFIG.animateTextures.get()) {
                    return MoCreatures.getTexture("wolffire1.png");
                }
                this.textCounter++;
                if (this.textCounter < 10) {
                    this.textCounter = 10;
                }
                if (this.textCounter > 39) {
                    this.textCounter = 10;
                }
                String NTA = "wolffire";
                String NTB = "" + this.textCounter;
                NTB = NTB.substring(0, 1);
                String NTC = ".png";

                return MoCreatures.getTexture(NTA + NTB + NTC);
            default:
                return MoCreatures.getTexture("wolfbrown.png");
        }
    }

    public boolean getIsHumanForm() {
        return this.entityData.get(IS_HUMAN);
    }

    public void setHumanForm(boolean flag) {
        this.entityData.set(IS_HUMAN, flag);
    }

    public boolean getIsHunched() {
        return this.entityData.get(IS_HUNCHED);
    }

    public void setHunched(boolean flag) {
        this.entityData.set(IS_HUNCHED, flag);
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if (getIsHumanForm()) {
            setTarget(null);
            return false;
        }
        if (this.getSubType() == 4 && entityIn instanceof LivingEntity) {
            ((LivingEntity) entityIn).setSecondsOnFire(10);
        }
        return super.doHurtTarget(entityIn);
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if (!getIsHumanForm() && (entity instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) entity;
            ItemStack stack = entityplayer.getMainHandItem();
            if (!stack.isEmpty()) {
                i = 1F;
                if (stack.getItem() == MoCItems.SILVERSWORD) {
                    i = 10F;
                }
                if (stack.getItem() instanceof SwordItem) {
                    String swordMaterial = ((SwordItem) stack.getItem()).getTier().toString(); //TODO: Figure out if this material check works
                    String swordName = ((SwordItem) stack.getItem()).getDescription().toString();
                    if (swordMaterial.toLowerCase().contains("silver") || swordName.toLowerCase().contains("silver")) {
                        i = ((SwordItem) stack.getItem()).getDamage() * 3F;
                    }
                } else if (stack.getItem() instanceof ToolItem) {
                    String toolMaterial = ((ToolItem) stack.getItem()).getTier().toString();
                    String toolName = ((ToolItem) stack.getItem()).getDescription().toString();
                    if (toolMaterial.toLowerCase().contains("silver") || toolName.toLowerCase().contains("silver")) {
                        i = ((SwordItem) stack.getItem()).getDamage() * 2F;
                    }
                } else if (stack.getItem().getDescription().toString().toLowerCase().contains("silver")) {
                    i = 6F;
                }
            }
        }
        return super.hurt(damagesource, i);
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsHumanForm() && super.shouldAttackPlayers();
    }

//    @Override
//    protected Item getDropItem() {
//        int i = this.rand.nextInt(12);
//        if (getIsHumanForm()) {
//            switch (i) {
//                case 0: // '\0'
//                    return Items.WOODEN_SHOVEL;
//
//                case 1: // '\001'
//                    return Items.WOODEN_AXE;
//
//                case 2: // '\002'
//                    return Items.WOODEN_SWORD;
//
//                case 3: // '\003'
//                    return Items.WOODEN_HOE;
//
//                case 4: // '\004'
//                    return Items.WOODEN_PICKAXE;
//            }
//            return Items.STICK;
//        }
//        switch (i) {
//            case 0: // '\0'
//                return Items.IRON_HOE;
//
//            case 1: // '\001'
//                return Items.IRON_SHOVEL;
//
//            case 2: // '\002'
//                return Items.IRON_AXE;
//
//            case 3: // '\003'
//                return Items.IRON_PICKAXE;
//
//            case 4: // '\004'
//                return Items.IRON_SWORD;
//
//            case 5: // '\005'
//                return Items.STONE_HOE;
//
//            case 6: // '\006'
//                return Items.STONE_SHOVEL;
//
//            case 7: // '\007'
//                return Items.STONE_AXE;
//
//            case 8: // '\b'
//                return Items.STONE_PICKAXE;
//
//            case 9: // '\t'
//                return Items.STONE_SWORD;
//        }
//        return Items.GOLDEN_APPLE;
//    }

    @Override
    protected SoundEvent getDeathSound() {
        if (getIsHumanForm()) {
            return MoCSoundEvents.ENTITY_WEREWOLF_DEATH_HUMAN;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_DEATH;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (getIsHumanForm()) {
            if (!this.transforming)
                return MoCSoundEvents.ENTITY_WEREWOLF_HURT_HUMAN;
            return null;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_HURT;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (getIsHumanForm()) {
            return MoCSoundEvents.ENTITY_WEREWOLF_AMBIENT_HUMAN;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_AMBIENT;
        }
    }

    public boolean IsNight() {
        return !this.level.isDay();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            if (((IsNight() && getIsHumanForm()) || (!IsNight() && !getIsHumanForm())) && (this.random.nextInt(250) == 0)) {
                this.transforming = true;
            }
            if (getIsHumanForm() && (this.getTarget() != null)) {
                setTarget(null);
            }
            if (this.getTarget() != null && !getIsHumanForm()) {
                boolean hunch = (this.distanceToSqr(this.getTarget()) > 12D);
                setHunched(hunch);
            }

            if (this.transforming && (this.random.nextInt(3) == 0)) {
                this.tcounter++;
                if ((this.tcounter % 2) == 0) {
                    this.setPos(this.getX()+0.3D, this.getY() + (this.tcounter/30), this.getZ());
                    hurt(DamageSource.mobAttack(this), 1);
                }
                if ((this.tcounter % 2) != 0) {
                    this.setPos(this.getX()-0.3D, this.getY(), this.getZ());
                }
                if (this.tcounter == 10) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_WEREWOLF_TRANSFORM);
                }
                if (this.tcounter > 30) {
                    Transform();
                    this.tcounter = 0;
                    this.transforming = false;
                }
            }
            //so entity doesn't despawn that often
            if (this.random.nextInt(300) == 0) {
                this.noActionTime -= 100 * this.level.getDifficulty().getId();
                if (this.noActionTime  < 0) {
                    this.noActionTime  = 0;
                }
            }
        }
    }

    private void Transform() {
        if (this.deathTime > 0) {
            return;
        }
        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(getBoundingBox().minY) + 1;
        int k = MathHelper.floor(this.getZ());
        float f = 0.1F;
        for (int l = 0; l < 30; l++) {
            double d = i + this.level.random.nextFloat();
            double d1 = j + this.level.random.nextFloat();
            double d2 = k + this.level.random.nextFloat();
            double d3 = d - i;
            double d4 = d1 - j;
            double d5 = d2 - k;
            double d6 = MathHelper.sqrt((d3 * d3) + (d4 * d4) + (d5 * d5));
            d3 /= d6;
            d4 /= d6;
            d5 /= d6;
            double d7 = 0.5D / ((d6 / f) + 0.1D);
            d7 *= (this.level.random.nextFloat() * this.level.random.nextFloat()) + 0.3F;
            d3 *= d7;
            d4 *= d7;
            d5 *= d7;
            this.level.addParticle(ParticleTypes.EXPLOSION, (d + (i * 1.0D)) / 2D, (d1 + (j * 1.0D)) / 2D, (d2 + (k * 1.0D)) / 2D,
                    d3, d4, d5);
        }

        if (getIsHumanForm()) {
            setHumanForm(false);
            this.setHealth(40);
            this.transforming = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        } else {
            setHumanForm(true);
            this.setHealth(15);
            this.transforming = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        setHumanForm(nbttagcompound.getBoolean("HumanForm"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putBoolean("HumanForm", getIsHumanForm());
    }

    @Override
    public float getSpeed() {
        if (getIsHumanForm()) {
            return 0.1F;
        }
        if (getIsHunched()) {
            return 0.35F;
        }
        return 0.2F;
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (getSubType() == 4) {
//            this.isImmuneToFire = true; TODO: Fire immunity
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }
}
