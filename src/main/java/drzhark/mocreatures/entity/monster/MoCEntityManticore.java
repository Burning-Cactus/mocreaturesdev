package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.MoCNearestAttackableTargetGoal;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class MoCEntityManticore extends MoCEntityMob {

    public int mouthCounter;
    public int tailCounter;
    public int wingFlapCounter;
    private boolean isPoisoning;
    private int poisontimer;

    public MoCEntityManticore(EntityType<? extends MoCEntityManticore> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(1, new MoCNearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getSubType() == 0) {
            setType((this.random.nextInt(2) * 2) + 2);
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        if (/*this.world.dimension.doesWaterVaporize()*/false) {
            setType(1);
//            this.isImmuneToFire = true;
            return true;
        }

        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getZ());
        BlockPos pos = new BlockPos(i, j, k);

        Biome currentbiome = MoCTools.Biomekind(this.level, pos);

        /*if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
            setType(3);
        }*/

        return true;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("bcmanticore.png");
            case 2:
                return MoCreatures.getTexture("bcmanticoredark.png");
            case 3:
                return MoCreatures.getTexture("bcmanticoreblue.png");
            case 4:
                return MoCreatures.getTexture("bcmanticoregreen.png");
            default:
                return MoCreatures.getTexture("bcmanticoregreen.png");
        }
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    /*protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
    }*/

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        //startArmSwingAttack();
        return super.doHurtTarget(entityIn);
    }

    public boolean getIsRideable() {
        return false;
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }

    @Override
    public int maxFlyingHeight() {
        return 10;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public void positionRider(Entity passenger) {
        double dist = (-0.1D);
        double newPosX = this.getX() + (dist * Math.sin(this.yBodyRot / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.cos(this.yBodyRot / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getPassengersRidingOffset() + passenger.getMyRidingOffset(), newPosZ);
        passenger.yRot = this.yRot;
    }

    @Override
    public double getPassengersRidingOffset() {
        return (this.getBbHeight() * 0.75D) - 0.1D;
    }

    /*@Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50D && !this.world.provider.doesWaterVaporize()) {
            setType(32);
        }
        return super.getCanSpawnHere();
    }*/

    /*@Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }*/

    private void openMouth() {
        this.mouthCounter = 1;
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    public boolean isOnAir() {
        return this.level.isEmptyBlock(new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(this.getY() - 0.2D), MathHelper
                .floor(this.getZ())));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
            this.mouthCounter = 0;
        }

        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }

        if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
            this.wingFlapCounter = 0;
        }
    }

    @Override
    public void aiStep() {
        //if (true) return;
        super.aiStep();

        /**
         * slow falling
         */
        /*if (!this.onGround && (this.motionY < 0.0D)) {
            this.motionY *= 0.6D;
        }*/

        if (isOnAir() && isFlyer() && this.random.nextInt(5) == 0) {
            this.wingFlapCounter = 1;
        }

        if (this.random.nextInt(200) == 0) {
            moveTail();
        }

        if (!this.level.isClientSide && isFlyer() && isOnAir()) {
            float myFlyingSpeed = MoCTools.getMyMovementSpeed(this);
            int wingFlapFreq = (int) (25 - (myFlyingSpeed * 10));
            if (!this.isVehicle() || wingFlapFreq < 5) {
                wingFlapFreq = 5;
            }
            if (this.random.nextInt(wingFlapFreq) == 0) {
                wingFlap();
            }
        }

        if (isFlyer()) {
            if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
                this.wingFlapCounter = 0;
            }
            /*if (this.wingFlapCounter != 0 && this.wingFlapCounter % 5 == 0 && this.world.isRemote) {
                StarFX();
            }*/
            if (this.wingFlapCounter == 5 && !this.level.isClientSide) {
                //System.out.println("playing sound");
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_WINGFLAP);
            }
        }

        if (getIsPoisoning()) {
            this.poisontimer++;
            if (this.poisontimer == 1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SCORPION_STING);
            }
            if (this.poisontimer > 50) {
                this.poisontimer = 0;
                setPoisoning(false);
            }
        }
        if (!this.level.isClientSide) {
            if (isFlyer() && this.random.nextInt(500) == 0) {
                wingFlap();
            }

            if (!this.isVehicle() && this.random.nextInt(200) == 0) {
                MoCTools.findMobRider(this);
            }
        }
    }

    @Override
    public void makeEntityJump() {
        wingFlap();
        super.makeEntityJump();
    }

    public void wingFlap() {
        if (this.isFlyer() && this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
//            if (!this.world.isRemote) {
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
//                        new TargetPoint(this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        /*if (animationType >= 23 && animationType < 60) //transform
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }*/
        if (animationType == 0) //tail animation
        {
            setPoisoning(true);
        } else if (animationType == 3) //wing flap
        {
            this.wingFlapCounter = 1;
        }
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    public void setPoisoning(boolean flag) {
//        if (flag && !this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
//                    new TargetPoint(this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//        }
        this.isPoisoning = flag;
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if (entity != this && entity instanceof LivingEntity && this.shouldAttackPlayers() && getIsAdult()) {
                setTarget((LivingEntity) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void doEnchantDamageEffects(LivingEntity entityLivingBaseIn, Entity entityIn) {
        boolean flag = (entityIn instanceof PlayerEntity);
        if (!getIsPoisoning() && this.random.nextInt(5) == 0 && entityIn instanceof LivingEntity) {
            setPoisoning(true);
            if (getSubType() == 4 || getSubType() == 2)// regular
            {
                if (flag) {
                    MoCreatures.poisonPlayer((PlayerEntity) entityIn);
                }
                ((LivingEntity) entityIn).addEffect(new EffectInstance(Effects.POISON, 70, 0));
            } else if (getSubType() == 3)// blue
            {
                if (flag) {
                    MoCreatures.freezePlayer((PlayerEntity) entityIn);
                }
                ((LivingEntity) entityIn).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 70, 0));

            } else if (getSubType() == 1)// red
            {
                if (flag && !this.level.isClientSide /*&& !this.world.dimension.doesWaterVaporize()*/) {
                    MoCreatures.burnPlayer((PlayerEntity) entityIn);
                    entityIn.setSecondsOnFire(15);
                }
            }
        } else {
            openMouth();
        }
        super.doEnchantDamageEffects(entityLivingBaseIn, entityIn);
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
    }

    @Override
    protected SoundEvent getDeathSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_LION_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        return MoCSoundEvents.ENTITY_LION_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_LION_AMBIENT;
    }

    /*@Override
    protected SoundEvent getDeathSound() {
        return "mocreatures:manticoredying";
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return "mocreatures:manticorehurt";
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return "mocreatures:manticore";
    }*/

    @Override
    public float getSizeFactor() {
        return 1.4F;
    }

//    @Override TODO:
//    protected Item getDropItem() {
//        boolean flag = (this.rand.nextInt(100) < MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.rareItemDropChance.get());
//
//        switch (getSubType()) {
//            case 1:
//                if (flag) {
//                    return MoCItems.SCORPSTINGNETHER;
//                }
//                return MoCItems.CHITINNETHER;
//            case 2:
//                if (flag) {
//                    return MoCItems.SCORPSTINGCAVE;
//                }
//                return MoCItems.CHITINCAVE;
//
//            case 3:
//                if (flag) {
//                    return MoCItems.SCORPSTINGFROST;
//                }
//                return MoCItems.CHITINFROST;
//            case 4:
//                if (flag) {
//                    return MoCItems.SCORPSTINGDIRT;
//                }
//                return MoCItems.CHITIN;
//
//            default:
//                return MoCItems.CHITIN;
//        }
//    }

//    @Override
//    protected void dropFewItems(boolean flag, int x) {
//        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());
//        int chance = MoCreatures.proxy.rareItemDropChance;
//        if (this.rand.nextInt(100) < chance) {
//            entityDropItem(new ItemStack(MoCItems.MOCEGG, 1, getType() + 61), 0.0F);
//        } else {
//            super.dropFewItems(flag, x);
//        }
//    }
}
