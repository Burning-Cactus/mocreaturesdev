package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.MoCNearestAttackableTargetGoal;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MoCEntityHorseMob extends MoCEntityMob {

    public int mouthCounter;
    public int textCounter;
    public int standCounter;
    public int tailCounter;
    public int eatingCounter;
    public int wingFlapCounter;

    public MoCEntityHorseMob(EntityType<? extends MoCEntityHorseMob> type, World world) {
        super(type, world);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new MoCNearestAttackableTargetGoal(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public void selectType() {
        if (/*this.world.getDimension().doesWaterVaporize()*/false) {
            setType(38);
//            this.isImmuneToFire = true; TODO: Fire immunity can't be set through the entity class, will probably need to use an event handler.
        } else {
            if (getSubType() == 0) {
                int j = this.random.nextInt(100);
                if (j <= (40)) {
                    setType(23); //undead
                } else if (j <= (80)) {
                    setType(26); //skeleton horse
                } else {
                    setType(32); //bat
                }
            }
        }
    }

    /**
     * Overridden for the dynamic nightmare texture. * 
     * 23 Undead 
     * 24 Undead Unicorn 
     * 25 Undead Pegasus
     * 26 skeleton 
     * 27 skeleton unicorn 
     * 28 skeleton pegasus
     * 30 bug horse
     * 32 Bat Horse 
     * 38 nightmare
     */
    @Override
    public ResourceLocation getTexture() {

        switch (getSubType()) {
            case 23://undead horse

                if (!MoCConfig.CLIENT_CONFIG.animateTextures.get()) {
                    return MoCreatures.getTexture("horseundead.png");
                }
                String baseTex = "horseundead";
                int max = 79;

                if (this.random.nextInt(3) == 0) {
                    this.textCounter++;
                }
                if (this.textCounter < 10) {
                    this.textCounter = 10;
                }
                if (this.textCounter > max) {
                    this.textCounter = 10;
                }

                String iteratorTex = "" + this.textCounter;
                iteratorTex = iteratorTex.substring(0, 1);
                String decayTex = "" + (getEdad() / 100);
                decayTex = decayTex.substring(0, 1);
                return MoCreatures.getTexture(baseTex + decayTex + iteratorTex + ".png");

            case 26:
                return MoCreatures.getTexture("horseskeleton.png");

            case 32:
                return MoCreatures.getTexture("horsebat.png");

            case 38:
                if (!MoCConfig.CLIENT_CONFIG.animateTextures.get()) {
                    return MoCreatures.getTexture("horsenightmare1.png");
                }
                this.textCounter++;
                if (this.textCounter < 10) {
                    this.textCounter = 10;
                }
                if (this.textCounter > 59) {
                    this.textCounter = 10;
                }
                String NTA = "horsenightmare";
                String NTB = "" + this.textCounter;
                NTB = NTB.substring(0, 1);
                String NTC = ".png";

                return MoCreatures.getTexture(NTA + NTB + NTC);

            default:
                return MoCreatures.getTexture("horseundead.png");
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_HORSE_DEATH_UNDEAD;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        stand();
        return MoCSoundEvents.ENTITY_HORSE_HURT_UNDEAD;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        if (this.random.nextInt(10) == 0) {
            stand();
        }
        return MoCSoundEvents.ENTITY_HORSE_AMBIENT_UNDEAD;
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

        if (this.standCounter > 0 && ++this.standCounter > 20) {
            this.standCounter = 0;
        }

        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }

        if (this.eatingCounter > 0 && ++this.eatingCounter > 50) {
            this.eatingCounter = 0;
        }

        if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
            this.wingFlapCounter = 0;
            //TODO flap sound!
        }
    }

    @Override
    public boolean isFlyer() {
        return this.getSubType() == 25 //undead pegasus
                || this.getSubType() == 32 // bat horse
                || this.getSubType() == 28; // skeleton pegasus
    }

    /**
     * Has an unicorn? to render it and buckle entities!
     *
     * @return
     */
    public boolean isUnicorned() {
        return this.getSubType() == 24 || this.getSubType() == 27 || this.getSubType() == 32;
    }

    @Override
    public void aiStep() {

        super.aiStep();

        if (isOnAir() && isFlyer() && this.random.nextInt(5) == 0) {
            this.wingFlapCounter = 1;
        }

        if (this.random.nextInt(200) == 0) {
            moveTail();
        }

        if (!isOnAir() && (!this.isVehicle()) && this.random.nextInt(250) == 0) {
            stand();
        }

        if (this.level.isClientSide && getSubType() == 38 && this.random.nextInt(50) == 0) {
            LavaFX();
        }

        if (this.level.isClientSide && getSubType() == 23 && this.random.nextInt(50) == 0) {
            UndeadFX();
        }

        if (!this.level.isClientSide) {
            if (isFlyer() && this.random.nextInt(500) == 0) {
                wingFlap();
            }

            if (!isOnAir() && (!this.isVehicle()) && this.random.nextInt(300) == 0) {
                setEating();
            }

            if (!this.isVehicle() && this.random.nextInt(100) == 0) {
                MoCTools.findMobRider(this);
                /*List list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 4D, 4D));
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = (Entity) list.get(i);
                    if (!(entity instanceof EntityMob)) {
                        continue;
                    }
                    EntityMob entitymob = (EntityMob) entity;
                    if (entitymob.getRidingEntity() == null
                            && (entitymob instanceof EntitySkeleton || entitymob instanceof EntityZombie || entitymob instanceof MoCEntitySilverSkeleton)) {
                        entitymob.mountEntity(this);
                        break;
                    }
                }*/
            }
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    private void setEating() {
        this.eatingCounter = 1;
    }

    private void stand() {
        this.standCounter = 1;
    }

    public void wingFlap() {
        this.wingFlapCounter = 1;
    }

//    @Override
//    protected Item getDropItem() {
//        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
//        if (this.getSubType() == 32 && MoCreatures.proxy.rareItemDropChance < 25) {
//            flag = (this.rand.nextInt(100) < 25);
//        }
//
//        if (flag && (this.getSubType() == 36 || (this.getSubType() >= 50 && this.getSubType() < 60))) //unicorn
//        {
//            return MoCItems.UNICORNHORN;
//        }
//
//        if (this.getSubType() == 38 && flag && this.world.getDimension().doesWaterVaporize()) //nightmare
//        {
//            return MoCItems.HEARTFIRE;
//        }
//        if (this.getSubType() == 32 && flag) //bat horse
//        {
//            return MoCItems.HEARTDARKNESS;
//        }
//        if (this.getSubType() == 26)//skely
//        {
//            return Items.BONE;
//        }
//        if ((this.getSubType() == 23 || this.getSubType() == 24 || this.getSubType() == 25)) {
//            if (flag) {
//                return MoCItems.HEARTUNDEAD;
//            }
//            return Items.ROTTEN_FLESH;
//        }
//
//        if (this.getSubType() == 21 || this.getSubType() == 22) {
//            return Items.GHAST_TEAR;
//        }
//
//        return Items.LEATHER;
//    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if (entityIn instanceof PlayerEntity && !shouldAttackPlayers()) {
            return false;
        }
        if (this.onGround && !isOnAir()) {
            stand();
        }
        openMouth();
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_HORSE_MAD);
        return super.doHurtTarget(entityIn);
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);

        if ((this.getSubType() == 23) || (this.getSubType() == 24) || (this.getSubType() == 25)) {
            MoCTools.spawnSlimes(this.level, this);
        }

    }

    @Override
    public double getPassengersRidingOffset() {
        return (this.getBbHeight() * 0.75D) - 0.1D;
    }

    @Override
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason reason) {
        if (this.getY() < 50D /*&& !this.world.dimension.doesWaterVaporize()*/) {
            setType(32);
        }
        return super.checkSpawnRules(worldIn, reason);
    }

    public void UndeadFX() {
//        MoCreatures.proxy.UndeadFX(this);
    }

    public void LavaFX() {
//        MoCreatures.proxy.LavaFX(this);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        if (getSubType() == 23 || getSubType() == 24 || getSubType() == 25) {
            return CreatureAttribute.UNDEAD;
        }
        return super.getMobType();
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
        double dist = (0.4D);
        double newPosX = this.getX() + (dist * Math.sin(this.yBodyRot / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.cos(this.yBodyRot / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getPassengersRidingOffset() + passenger.getMyRidingOffset(), newPosZ);
        passenger.yRot = this.yRot;
    }
}
