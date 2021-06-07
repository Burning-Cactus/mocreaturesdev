package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.FollowAdultGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCItems;
import drzhark.mocreatures.registry.MoCParticleTypes;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.JukeboxTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class MoCEntityHorse extends MoCEntityTameableAnimal {

    private int gestationtime;
    private int countEating;
    private int textCounter;
    private int fCounter;
    public int shuffleCounter;
    public int wingFlapCounter;
    private float transFloat = 0.2F;

//    public MoCAnimalChest localchest;
    public boolean eatenpumpkin;
    private boolean hasReproduced;
    private int nightmareInt;
    public ItemStack localstack;
    public int mouthCounter;
    public int standCounter;
    public int tailCounter;
    public int vanishCounter;
    public int sprintCounter;
    public int transformType;
    public int transformCounter;
    protected MoCAlternateWanderGoal wander;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.defineId(MoCEntityHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.defineId(MoCEntityHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.defineId(MoCEntityHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BRED = EntityDataManager.defineId(MoCEntityHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ARMOR_TYPE = EntityDataManager.defineId(MoCEntityHorse.class, DataSerializers.INT);

    public MoCEntityHorse(EntityType<? extends MoCEntityHorse> type, World world) { //TODO: Set fire immunity for Nightmare in the damage event
        super(type, world);
        this.gestationtime = 0;
        this.eatenpumpkin = false;
        this.nightmareInt = 0;
        setEdad(50);
        setIsChested(false);
        this.maxUpStep = 1.0F;

        if (!this.level.isClientSide) {
            if (this.random.nextInt(5) == 0) {
                setAdult(false);
            } else {
                setAdult(true);
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new FollowAdultGoal(this, 1.0D));
        this.goalSelector.addGoal(4, this.wander = new MoCAlternateWanderGoal(this, 1.0D, 80));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RIDEABLE, false); // rideable: 0 nothing, 1 saddle
        this.entityData.define(SITTING, false); // rideable: 0 nothing, 1 saddle
        this.entityData.define(CHESTED, false);
        this.entityData.define(BRED, false);
        this.entityData.define(ARMOR_TYPE, 0);
    }

    @Override
    public int getArmorType() {
        return this.entityData.get(ARMOR_TYPE);
    }

    public boolean getIsChested() {
        return this.entityData.get(CHESTED);
    }
    
    @Override
    public boolean getIsSitting() {
        return this.entityData.get(SITTING);
    }

    public boolean getHasBred() {
        return this.entityData.get(BRED);
    }

    public void setBred(boolean flag) {
        this.entityData.set(BRED, flag);
    }

    @Override
    public boolean getIsRideable() {
        return this.entityData.get(RIDEABLE);
    }
    @Override
    public void setRideable(boolean flag) {
        this.entityData.set(RIDEABLE, flag);
    }
    
    @Override
    public void setArmorType(int i) {
        this.entityData.set(ARMOR_TYPE, i);
    }

    public void setIsChested(boolean flag) {
        this.entityData.set(CHESTED, flag);
    }

    public void setSitting(boolean flag) {
        this.entityData.set(SITTING, flag);
    }
    
    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if ((this.isVehicle()) && (entity == this.getVehicle())) {
            return false;
        }
        if (entity instanceof WolfEntity) {
            CreatureEntity entitycreature = (CreatureEntity) entity;
            entitycreature.setTarget(null);
            return false;
        } else {
            i = i - (getArmorType() + 2);
            if (i < 0F) {
                i = 0F;
            }
            return super.hurt(damagesource, i);
        }
    }

    @Override
    public boolean isPickable() {
        return !this.isVehicle();
    }

    /*@Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());

        Biome currentbiome = MoCTools.Biomekind(this.world, pos);
        String s = MoCTools.biomeName(this.world, pos);
        try {
            if (BiomeDictionary.hasType(currentbiome, Type.PLAINS)) {
                if (this.rand.nextInt(3) == 0) {
                    setType(60);// zebra
                }
            }
            if (BiomeDictionary.hasType(currentbiome, Type.SANDY)) {
                setType(60);// zebra
            }

            if (s.toLowerCase().contains("prairie"))//prairies spawn only regular horses, no zebras there
            {
                setType(this.rand.nextInt(5) + 1);
            }
        } catch (Exception e) {
        }
        return true;
    }*/

    /**
     * returns one of the RGB color codes
     *
     * @param sColor : 1 will return the Red component, 2 will return the Green
     *        and 3 the blue
     * @param typeInt : which set of colors to inquiry about, corresponds with
     *        the horse types.
     * @return
     */
    public float colorFX(int sColor, int typeInt) {
        if (typeInt == 48) // yellow
        {
            if (sColor == 1) {
                return (float) 179 / 256;
            }
            if (sColor == 2) {
                return (float) 160 / 256;
            }
            return (float) 22 / 256;
        }

        if (typeInt == 49) // purple
        {
            if (sColor == 1) {
                return (float) 147 / 256;
            }
            if (sColor == 2) {
                return (float) 90 / 256;
            }
            return (float) 195 / 256;
        }

        if (typeInt == 51) // blue
        {
            if (sColor == 1) {
                return (float) 30 / 256;
            }
            if (sColor == 2) {
                return (float) 144 / 256;
            }
            return (float) 255 / 256;
        }
        if (typeInt == 52) // pink
        {
            if (sColor == 1) {
                return (float) 255 / 256;
            }
            if (sColor == 2) {
                return (float) 105 / 256;
            }
            return (float) 180 / 256;
        }

        if (typeInt == 53) // lightgreen
        {
            if (sColor == 1) {
                return (float) 188 / 256;
            }
            if (sColor == 2) {
                return (float) 238 / 256;
            }
            return (float) 104 / 256;
        }

        if (typeInt == 54) // black fairy
        {
            if (sColor == 1) {
                return (float) 110 / 256;
            }
            if (sColor == 2) {
                return (float) 123 / 256;
            }
            return (float) 139 / 256;
        }

        if (typeInt == 55) // red fairy
        {
            if (sColor == 1) {
                return (float) 194 / 256;
            }
            if (sColor == 2) {
                return (float) 29 / 256;
            }
            return (float) 34 / 256;
        }

        if (typeInt == 56) // dark blue fairy
        {
            if (sColor == 1) {
                return (float) 63 / 256;
            }
            if (sColor == 2) {
                return (float) 45 / 256;
            }
            return (float) 255 / 256;
        }

        if (typeInt == 57) // cyan
        {
            if (sColor == 1) {
                return (float) 69 / 256;
            }
            if (sColor == 2) {
                return (float) 146 / 256;
            }
            return (float) 145 / 256;
        }

        if (typeInt == 58) // green
        {
            if (sColor == 1) {
                return (float) 90 / 256;
            }
            if (sColor == 2) {
                return (float) 136 / 256;
            }
            return (float) 43 / 256;
        }

        if (typeInt == 59) // orange
        {
            if (sColor == 1) {
                return (float) 218 / 256;
            }
            if (sColor == 2) {
                return (float) 40 / 256;
            }
            return (float) 0 / 256;
        }

        if (typeInt > 22 && typeInt < 26) // green for undeads
        {
            if (sColor == 1) {
                return (float) 60 / 256;
            }
            if (sColor == 2) {
                return (float) 179 / 256;
            }
            return (float) 112 / 256;

        }
        if (typeInt == 40) // dark red for black pegasus
        {
            if (sColor == 1) {
                return (float) 139 / 256;
            }
            if (sColor == 2) {
                return 0F;
            }
            return 0F;

        }

        // by default will return clear gold
        if (sColor == 1) {
            return (float) 255 / 256;
        }
        if (sColor == 2) {
            return (float) 236 / 256;
        }
        return (float) 139 / 256;
    }

    /**
     * Called to vanish a Horse without FX
     */
    public void dissapearHorse() {
        this.dead = true;
    }

    private void drinkingHorse() {
        openMouth();
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
    }

    /**
     * Drops the current armor if the horse has one
     */
    @Override
    public void dropArmor() {
        if (!this.level.isClientSide) {
            int i = getArmorType();
            if (i != 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            }

            if (i == 1) {
                ItemEntity entityitem = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.IRON_HORSE_ARMOR, 1));
                entityitem.setPickUpDelay(10);
                this.level.addFreshEntity(entityitem);
            }
            if (i == 2) {
                ItemEntity entityitem = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.GOLDEN_HORSE_ARMOR, 1));
                entityitem.setPickUpDelay(10);
                this.level.addFreshEntity(entityitem);
            }
            if (i == 3) {
                ItemEntity entityitem = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.DIAMOND_HORSE_ARMOR, 1));
                entityitem.setPickUpDelay(10);
                this.level.addFreshEntity(entityitem);
            }
            if (i == 4) {
                ItemEntity entityitem =
                        new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(MoCItems.HORSEARMORCRYSTAL, 1));
                entityitem.setPickUpDelay(10);
                this.level.addFreshEntity(entityitem);
            }
            setArmorType((byte) 0);
        }
    }

    /**
     * Drops a chest block if the horse is bagged
     */
    public void dropBags() {
        if (!isBagger() || !getIsChested() || this.level.isClientSide) {
            return;
        }

        ItemEntity entityitem = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Blocks.CHEST, 1));
        float f3 = 0.05F;
        entityitem.setDeltaMovement((float) this.level.random.nextGaussian() * f3, ((float) this.level.random.nextGaussian() * f3) + 0.2F, (float) this.level.random.nextGaussian() * f3);
        this.level.addFreshEntity(entityitem);
        setIsChested(false);
    }

    private void eatingHorse() {
        openMouth();
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
    }

    @Override
    public boolean causeFallDamage(float f, float f1) {
        if (isFlyer() || isFloater()) {
            return false;
        }

        float i = (float) (Math.ceil(f - 3F) / 2F);
        if (!this.level.isClientSide && (i > 0)) {
            if (getSubType() >= 10) {
                i /= 2;
            }
            if (i > 1F) {
                hurt(DamageSource.FALL, i);
            }
            if ((this.isVehicle()) && (i > 1F)) {
                for (Entity entity : this.getIndirectPassengers())
                {
                    entity.hurt(DamageSource.FALL, (float)i);
                }
            }
            BlockState iblockstate = this.level.getBlockState(new BlockPos(this.getX(), this.getY() - 0.2D - (double)this.yRotO, this.getZ()));
            Block block = iblockstate.getBlock();

            if (iblockstate.getMaterial() != Material.AIR && !this.isSilent())
            {
                SoundType soundtype = block.getSoundType(iblockstate, level, new BlockPos(this.getX(), this.getY() - 0.2D - (double)this.yRotO, this.getZ()), this);
                this.level.playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), soundtype.getStepSound(), this.getSoundSource(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
            }
        }
        return false;
    }

    public int getInventorySize() {
        if (getSubType() == 40) {
            return 18;
        } else if (getSubType() > 64) {
            return 27;
        }
        return 9;
    }

    @Override
    public double getCustomJump() {
        double HorseJump = 0.35D;
        if (getSubType() < 6) // tier 1
        {
            HorseJump = 0.35;
        } else if (getSubType() > 5 && getSubType() < 11) // tier 2
        {
            HorseJump = 0.40D;
        } else if (getSubType() > 10 && getSubType() < 16) // tier 3
        {
            HorseJump = 0.45D;
        } else if (getSubType() > 15 && getSubType() < 21) // tier 4
        {
            HorseJump = 0.50D;
        }

        else if (getSubType() > 20 && getSubType() < 26) // ghost and undead
        {
            HorseJump = 0.45D;
        } else if (getSubType() > 25 && getSubType() < 30) // skely
        {
            HorseJump = 0.5D;
        } else if (getSubType() >= 30 && getSubType() < 40) // magics
        {
            HorseJump = 0.55D;
        } else if (getSubType() >= 40 && getSubType() < 60) // black pegasus and fairies
        {
            HorseJump = 0.6D;
        } else if (getSubType() >= 60) // donkeys - zebras and the like
        {
            HorseJump = 0.4D;
        }
        return HorseJump;
    }

    @Override
    public double getCustomSpeed() {
        double HorseSpeed = 0.8D;
        if (getSubType() < 6) // tier 1
        {
            HorseSpeed = 0.9;
        } else if (getSubType() > 5 && getSubType() < 11) // tier 2
        {
            HorseSpeed = 1.0D;
        } else if (getSubType() > 10 && getSubType() < 16) // tier 3
        {
            HorseSpeed = 1.1D;
        } else if (getSubType() > 15 && getSubType() < 21) // tier 4
        {
            HorseSpeed = 1.2D;
        }

        else if (getSubType() > 20 && getSubType() < 26) // ghost and undead
        {
            HorseSpeed = 0.8D;
        } else if (getSubType() > 25 && getSubType() < 30) // skely
        {
            HorseSpeed = 1.0D;
        } else if (getSubType() > 30 && getSubType() < 40) // magics
        {
            HorseSpeed = 1.2D;
        } else if (getSubType() >= 40 && getSubType() < 60) // black pegasus and
                                                      // fairies
        {
            HorseSpeed = 1.3D;
        } else if (getSubType() == 60 || getSubType() == 61) // zebras and zorse
        {
            HorseSpeed = 1.1D;
        } else if (getSubType() == 65) // donkeys
        {
            HorseSpeed = 0.7D;
        } else if (getSubType() > 65) // mule and zorky
        {
            HorseSpeed = 0.9D;
        }
        if (this.sprintCounter > 0 && this.sprintCounter < 150) {
            HorseSpeed *= 1.5D;
        }
        if (this.sprintCounter > 150) {
            HorseSpeed *= 0.5D;
        }
        return HorseSpeed;
    }

    @Override
    protected SoundEvent getDeathSound() {
        openMouth();
        if (this.isUndead()) {
            return MoCSoundEvents.ENTITY_HORSE_DEATH_UNDEAD;
        }
        if (this.getIsGhost()) {
            return MoCSoundEvents.ENTITY_HORSE_DEATH_GHOST;
        }
        if (this.getSubType() == 60 || this.getSubType() == 61) {
            return MoCSoundEvents.ENTITY_HORSE_HURT_ZEBRA;
        }
        if (this.getSubType() >= 65 && this.getSubType() <= 67) {
            return MoCSoundEvents.ENTITY_HORSE_DEATH_DONKEY;
        }
        return MoCSoundEvents.ENTITY_HORSE_DEATH;
    }

    @Override
    public boolean renderName() {
        if (getIsGhost() && getEdad() < 10) {
            return false;
        }
//        return super.renderName();
        return false;
    }

//    @Override
//    protected Item getDropItem() {
//        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
//
//        if (flag && (this.getSubType() == 36 || (this.getSubType() >= 50 && this.getSubType() < 60))) // unicorn
//        {
//            return MoCItems.UNICORNHORN;
//        }
//        if (this.getSubType() == 39) // pegasus
//        {
//            return Items.FEATHER;
//        }
//        if (this.getSubType() == 40) // dark pegasus
//        {
//            return Items.FEATHER;
//        }
//        if (this.getSubType() == 38 && flag && this.world.dimension.doesWaterVaporize()) // nightmare
//        {
//            return MoCItems.HEARTFIRE;
//        }
//        if (this.getSubType() == 32 && flag) // bat horse
//        {
//            return MoCItems.HEARTDARKNESS;
//        }
//        if (this.getSubType() == 26)// skely
//        {
//            return Items.BONE;
//        }
//        if ((this.getSubType() == 23 || this.getSubType() == 24 || this.getSubType() == 25)) {
//            if (flag) {
//                return MoCItems.HEARTUNDEAD;
//            }
//            return Items.ROTTEN_FLESH;
//        }
//        if (this.getSubType() == 21 || this.getSubType() == 22) {
//            return Items.GHAST_TEAR;
//        }
//
//        return Items.LEATHER;
//    }

    public boolean getHasReproduced() {
        return this.hasReproduced;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        if (isFlyer() && !this.isVehicle()) {
            wingFlap();
        } else {
            if (this.random.nextInt(3) == 0) {
                stand();
            }
        }
        if (this.isUndead()) {
            return MoCSoundEvents.ENTITY_HORSE_HURT_UNDEAD;
        }
        if (this.getIsGhost()) {
            return MoCSoundEvents.ENTITY_HORSE_HURT_GHOST;
        }
        if (this.getSubType() == 60 || this.getSubType() == 61) {
            return MoCSoundEvents.ENTITY_HORSE_HURT_ZEBRA;
        }
        if (this.getSubType() >= 65 && this.getSubType() <= 67) {
            return MoCSoundEvents.ENTITY_HORSE_HURT_DONKEY;
        }
        return MoCSoundEvents.ENTITY_HORSE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        if (this.random.nextInt(10) == 0 && !isMovementCeased()) {
            stand();
        }
        if (this.isUndead()) {
            return MoCSoundEvents.ENTITY_HORSE_AMBIENT_UNDEAD;
        }
        if (this.getIsGhost()) {
            return MoCSoundEvents.ENTITY_HORSE_AMBIENT_GHOST;
        }
        if (this.getSubType() == 60 || this.getSubType() == 61) {
            return MoCSoundEvents.ENTITY_HORSE_AMBIENT_ZEBRA;
        }
        if (this.getSubType() >= 65 && this.getSubType() <= 67) {
            return MoCSoundEvents.ENTITY_HORSE_HURT_DONKEY;
        }
        return MoCSoundEvents.ENTITY_HORSE_AMBIENT;
    }

    /**
     * sound played when an untamed mount buckles rider
     */
    @Override
    protected SoundEvent getAngrySound() {
        openMouth();
        stand();
        if (this.isUndead()) {
            return MoCSoundEvents.ENTITY_HORSE_ANGRY_UNDEAD;
        }
        if (this.getIsGhost()) {
            return MoCSoundEvents.ENTITY_HORSE_ANGRY_GHOST;
        }
        if (this.getSubType() == 60 || this.getSubType() == 61) {
            return MoCSoundEvents.ENTITY_HORSE_HURT_ZEBRA;
        }
        if (this.getSubType() >= 65 && this.getSubType() <= 67) {
            return MoCSoundEvents.ENTITY_HORSE_HURT_DONKEY;
        }
        return MoCSoundEvents.ENTITY_HORSE_MAD;
    }

    public float calculateMaxHealth() {
        int maximumHealth = 30;
        if (getSubType() < 6) // tier 1
        {
            maximumHealth = 25;
        } else if (getSubType() > 5 && getSubType() < 11) // tier 2
        {
            maximumHealth = 30;
        } else if (getSubType() > 10 && getSubType() < 16) // tier 3
        {
            maximumHealth = 35;
        } else if (getSubType() > 15 && getSubType() < 21) // tier 4
        {
            maximumHealth = 40;
        }

        else if (getSubType() > 20 && getSubType() < 26) // ghost and undead
        {
            maximumHealth = 35;
        } else if (getSubType() > 25 && getSubType() < 30) // skely
        {
            maximumHealth = 35;
        } else if (getSubType() >= 30 && getSubType() < 40) // magics
        {
            maximumHealth = 50;
        } else if (getSubType() == 40) // black pegasus
        {
            maximumHealth = 50;
        } else if (getSubType() > 40 && getSubType() < 60) // fairies
        {
            maximumHealth = 40;
        } else if (getSubType() >= 60) // donkeys - zebras and the like
        {
            maximumHealth = 30;
        }

        return maximumHealth;
    }

    /**
     * How difficult is the creature to be tamed? the Higher the number, the
     * more difficult
     */
    @Override
    public int getMaxTemper() {

        if (getSubType() == 60) {
            return 200; // zebras are harder to tame
        }
        return 100;
    }

    public int getNightmareInt() {
        return this.nightmareInt;
    }

    @Override
    protected float getSoundVolume() {
        return 0.8F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    /**
     * Overridden for the dynamic nightmare texture.
     */
    @Override
    public ResourceLocation getTexture() {
        String tempTexture;

        switch (getSubType()) {
            case 1:
                tempTexture = "horsewhite.png";
                break;
            case 2:
                tempTexture = "horsecreamy.png";
                break;
            case 3:
                tempTexture = "horsebrown.png";
                break;
            case 4:
                tempTexture = "horsedarkbrown.png";
                break;
            case 5:
                tempTexture = "horseblack.png";
                break;
            case 6:
                tempTexture = "horsebrightcreamy.png";
                break;
            case 7:
                tempTexture = "horsespeckled.png";
                break;
            case 8:
                tempTexture = "horsepalebrown.png";
                break;
            case 9:
                tempTexture = "horsegrey.png";
                break;
            case 11:
                tempTexture = "horsepinto.png";
                break;
            case 12:
                tempTexture = "horsebrightpinto.png";
                break;
            case 13:
                tempTexture = "horsepalespeckles.png";
                break;
            case 16:
                tempTexture = "horsespotted.png";
                break;
            case 17:
                tempTexture = "horsecow.png";
                break;

            case 21:
                tempTexture = "horseghost.png";
                break;
            case 22:
                tempTexture = "horseghostb.png";
                break;
            case 23:
                tempTexture = "horseundead.png";
                break;
            case 24:
                tempTexture = "horseundeadunicorn.png";
                break;
            case 25:
                tempTexture = "horseundeadpegasus.png";
                break;
            case 26:
                tempTexture = "horseskeleton.png";
                break;
            case 27:
                tempTexture = "horseunicornskeleton.png";
                break;
            case 28:
                tempTexture = "horsepegasusskeleton.png";
                break;
            case 30:
                tempTexture = "horsebug.png";
                break;
            case 32:
                tempTexture = "horsebat.png";
                break;
            case 36:
                tempTexture = "horseunicorn.png";
                break;
            case 38:
                //this.isImmuneToFire = true;
                tempTexture = "horsenightmare.png";
                break;
            case 39:
                tempTexture = "horsepegasus.png";
                break;
            case 40:
                //this.isImmuneToFire = true;
                tempTexture = "horsedarkpegasus.png";
                break;
            /*
             * case 44: tempTexture = "horsefairydarkblue.png"; break; case 45:
             * tempTexture = "horsefairydarkblue.png"; break; case 46:
             * tempTexture = "horsefairydarkblue.png"; break; case 47:
             * tempTexture = "horsefairydarkblue.png"; break;
             */
            case 48:
                tempTexture = "horsefairyyellow.png";
                break;
            case 49:
                tempTexture = "horsefairypurple.png";
                break;
            case 50:
                tempTexture = "horsefairywhite.png";
                break;
            case 51:
                tempTexture = "horsefairyblue.png";
                break;
            case 52:
                tempTexture = "horsefairypink.png";
                break;
            case 53:
                tempTexture = "horsefairylightgreen.png";
                break;
            case 54:
                tempTexture = "horsefairyblack.png";
                break;
            case 55:
                tempTexture = "horsefairyred.png";
                break;
            case 56:
                tempTexture = "horsefairydarkblue.png";
                break;
            case 57:
                tempTexture = "horsefairycyan.png";
                break;
            case 58:
                tempTexture = "horsefairygreen.png";
                break;
            case 59:
                tempTexture = "horsefairyorange.png";
                break;

            case 60:
                tempTexture = "horsezebra.png";
                break;
            case 61:
                tempTexture = "horsezorse.png";
                break;
            case 65:
                tempTexture = "horsedonkey.png";
                break;
            case 66:
                tempTexture = "horsemule.png";
                break;
            case 67:
                tempTexture = "horsezonky.png";
                break;

            default:
                tempTexture = "horsebug.png";
        }

        if ((isArmored() || isMagicHorse()) && getArmorType() > 0) {
            String armorTex = "";
            if (getArmorType() == 1) {
                armorTex = "metal.png";
            }
            if (getArmorType() == 2) {
                armorTex = "gold.png";
            }
            if (getArmorType() == 3) {
                armorTex = "diamond.png";
            }
            if (getArmorType() == 4) {
                armorTex = "crystaline.png";
            }
            return MoCreatures.getTexture(tempTexture.replace(".png", armorTex));
        }

        if (this.isUndead() && this.getSubType() < 26) {
            String baseTex = "horseundead";
            int max = 79;
            if (this.getSubType() == 25) // undead pegasus
            {
                baseTex = "horseundeadpegasus";
                // max = 79; //undead pegasus have an extra animation

            }
            if (this.getSubType() == 24)// undead unicorn
            {
                baseTex = "horseundeadunicorn";
                max = 69; // undead unicorn have an animation less
            }

            String iteratorTex = "1";
            if (MoCConfig.CLIENT_CONFIG.animateTextures.get()) {
                if (this.random.nextInt(3) == 0) {
                    this.textCounter++;
                }
                if (this.textCounter < 10) {
                    this.textCounter = 10;
                }
                if (this.textCounter > max) {
                    this.textCounter = 10;
                }
                iteratorTex = "" + this.textCounter;
                iteratorTex = iteratorTex.substring(0, 1);
            }

            String decayTex = "" + (getEdad() / 100);
            decayTex = decayTex.substring(0, 1);
            return MoCreatures.getTexture(baseTex + decayTex + iteratorTex + ".png");
        }

        // if animate textures is off, return plain textures
        if (!MoCConfig.CLIENT_CONFIG.animateTextures.get()) {
            return MoCreatures.getTexture(tempTexture);
        }

        if (this.isNightmare()) {
            if (this.random.nextInt(1) == 0) {
                this.textCounter++;
            }
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
        }

        if (this.transformCounter != 0 && this.transformType != 0) {
            String newText = "horseundead.png";
            if (this.transformType == 23) {
                newText = "horseundead.png";
            }
            if (this.transformType == 24) {
                newText = "horseundeadunicorn.png";
            }
            if (this.transformType == 25) {
                newText = "horseundeadpegasus.png";
            }
            if (this.transformType == 36) {
                newText = "horseunicorn.png";
            }
            if (this.transformType == 39) {
                newText = "horsepegasus.png";
            }
            if (this.transformType == 40) {
                newText = "horseblackpegasus.png";
            }

            if (this.transformType == 48) {
                newText = "horsefairyyellow.png";
            }
            if (this.transformType == 49) {
                newText = "horsefairypurple.png";
            }
            if (this.transformType == 50) {
                newText = "horsefairywhite.png";
            }
            if (this.transformType == 51) {
                newText = "horsefairyblue.png";
            }
            if (this.transformType == 52) {
                newText = "horsefairypink.png";
            }
            if (this.transformType == 53) {
                newText = "horsefairylightgreen.png";
            }
            if (this.transformType == 54) {
                newText = "horsefairyblack.png";
            }
            if (this.transformType == 55) {
                newText = "horsefairyred.png";
            }
            if (this.transformType == 56) {
                newText = "horsefairydarkblue.png";
            }

            if (this.transformType == 57) {
                newText = "horsefairycyan.png";
            }

            if (this.transformType == 58) {
                newText = "horsefairygreen.png";
            }

            if (this.transformType == 59) {
                newText = "horsefairyorange.png";
            }

            if (this.transformType == 32) {
                newText = "horsebat.png";
            }
            if (this.transformType == 38) {
                newText = "horsenightmare1.png";
            }
            if ((this.transformCounter % 5) == 0) {
                return MoCreatures.getTexture(newText);
            }
            if (this.transformCounter > 50 && (this.transformCounter % 3) == 0) {
                return MoCreatures.getTexture(newText);
            }

            if (this.transformCounter > 75 && (this.transformCounter % 4) == 0) {
                return MoCreatures.getTexture(newText);
            }
        }

        return MoCreatures.getTexture(tempTexture);

    }

    /**
     * New networked to fix SMP issues
     *
     * @return
     */
    public byte getVanishC() {
        return (byte) this.vanishCounter;
    }

    /**
     * Breeding rules for the horses
     *
     * //@param entityhorse
     * //@param entityhorse1
     * @return
     */
    //private int HorseGenetics(MoCEntityHorse entityhorse, MoCEntityHorse entityhorse1)
    private int HorseGenetics(int typeA, int typeB) {
        boolean flag = MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.easyBreeding.get();
        //int typeA = entityhorse.getType();
        //int typeB = entityhorse1.getType();

        // identical horses have so spring
        if (typeA == typeB) {
            return typeA;
        }

        // zebras plus any horse
        if (typeA == 60 && typeB < 21 || typeB == 60 && typeA < 21) {
            return 61; // zorse
        }

        // dokey plus any horse
        if (typeA == 65 && typeB < 21 || typeB == 65 && typeA < 21) {
            return 66; // mule
        }

        // zebra plus donkey
        if (typeA == 60 && typeB == 65 || typeB == 60 && typeA == 65) {
            return 67; // zonky
        }

        if (typeA > 20 && typeB < 21 || typeB > 20 && typeA < 21) // rare horses plus  ordinary horse always returns ordinary horse
        {
            if (typeA < typeB) {
                return typeA;
            }
            return typeB;
        }

        // unicorn plus white pegasus (they will both vanish!)
        if (typeA == 36 && typeB == 39 || typeB == 36 && typeA == 39) {
            return 50; // white fairy
        }

        // unicorn plus black pegasus (they will both vanish!)
        if (typeA == 36 && typeB == 40 || typeB == 36 && typeA == 40) {
            return 54; // black fairy
        }

        // rare horse mixture: produces a regular horse 1-5
        if (typeA > 20 && typeB > 20 && (typeA != typeB)) {
            return (this.random.nextInt(5)) + 1;
        }

        // rest of cases will return either typeA, typeB or new mix
        int chanceInt = (this.random.nextInt(4)) + 1;
        if (!flag) {
            if (chanceInt == 1) // 25%
            {
                return typeA;
            } else if (chanceInt == 2) // 25%
            {
                return typeB;
            }
        }

        if ((typeA == 1 && typeB == 2) || (typeA == 2 && typeB == 1)) {
            return 6;
        }

        if ((typeA == 1 && typeB == 3) || (typeA == 3 && typeB == 1)) {
            return 2;
        }

        if ((typeA == 1 && typeB == 4) || (typeA == 4 && typeB == 1)) {
            return 7;
        }

        if ((typeA == 1 && typeB == 5) || (typeA == 5 && typeB == 1)) {
            return 9;
        }

        if ((typeA == 1 && typeB == 7) || (typeA == 7 && typeB == 1)) {
            return 12;
        }

        if ((typeA == 1 && typeB == 8) || (typeA == 8 && typeB == 1)) {
            return 7;
        }

        if ((typeA == 1 && typeB == 9) || (typeA == 9 && typeB == 1)) {
            return 13;
        }

        if ((typeA == 1 && typeB == 11) || (typeA == 11 && typeB == 1)) {
            return 12;
        }

        if ((typeA == 1 && typeB == 12) || (typeA == 12 && typeB == 1)) {
            return 13;
        }

        if ((typeA == 1 && typeB == 17) || (typeA == 17 && typeB == 1)) {
            return 16;
        }

        if ((typeA == 2 && typeB == 4) || (typeA == 4 && typeB == 2)) {
            return 3;
        }

        if ((typeA == 2 && typeB == 5) || (typeA == 5 && typeB == 2)) {
            return 4;
        }

        if ((typeA == 2 && typeB == 7) || (typeA == 7 && typeB == 2)) {
            return 8;
        }

        if ((typeA == 2 && typeB == 8) || (typeA == 8 && typeB == 2)) {
            return 3;
        }

        if ((typeA == 2 && typeB == 12) || (typeA == 12 && typeB == 2)) {
            return 6;
        }

        if ((typeA == 2 && typeB == 16) || (typeA == 16 && typeB == 2)) {
            return 13;
        }

        if ((typeA == 2 && typeB == 17) || (typeA == 17 && typeB == 2)) {
            return 12;
        }

        if ((typeA == 3 && typeB == 4) || (typeA == 4 && typeB == 3)) {
            return 8;
        }

        if ((typeA == 3 && typeB == 5) || (typeA == 5 && typeB == 3)) {
            return 8;
        }

        if ((typeA == 3 && typeB == 6) || (typeA == 6 && typeB == 3)) {
            return 2;
        }

        if ((typeA == 3 && typeB == 7) || (typeA == 7 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 3 && typeB == 9) || (typeA == 9 && typeB == 3)) {
            return 8;
        }

        if ((typeA == 3 && typeB == 12) || (typeA == 12 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 3 && typeB == 16) || (typeA == 16 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 3 && typeB == 17) || (typeA == 17 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 4 && typeB == 6) || (typeA == 6 && typeB == 4)) {
            return 3;
        }

        if ((typeA == 4 && typeB == 7) || (typeA == 7 && typeB == 4)) {
            return 8;
        }

        if ((typeA == 4 && typeB == 9) || (typeA == 9 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 11) || (typeA == 11 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 12) || (typeA == 12 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 13) || (typeA == 13 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 16) || (typeA == 16 && typeB == 4)) {
            return 13;
        }

        if ((typeA == 4 && typeB == 17) || (typeA == 17 && typeB == 4)) {
            return 5;
        }

        if ((typeA == 5 && typeB == 6) || (typeA == 6 && typeB == 5)) {
            return 4;
        }

        if ((typeA == 5 && typeB == 7) || (typeA == 7 && typeB == 5)) {
            return 4;
        }

        if ((typeA == 5 && typeB == 8) || (typeA == 8 && typeB == 5)) {
            return 4;
        }

        if ((typeA == 5 && typeB == 11) || (typeA == 11 && typeB == 5)) {
            return 17;
        }

        if ((typeA == 5 && typeB == 12) || (typeA == 12 && typeB == 5)) {
            return 13;
        }

        if ((typeA == 5 && typeB == 13) || (typeA == 13 && typeB == 5)) {
            return 16;
        }

        if ((typeA == 5 && typeB == 16) || (typeA == 16 && typeB == 5)) {
            return 17;
        }

        if ((typeA == 6 && typeB == 8) || (typeA == 8 && typeB == 6)) {
            return 2;
        }

        if ((typeA == 6 && typeB == 17) || (typeA == 17 && typeB == 6)) {
            return 7;
        }

        if ((typeA == 7 && typeB == 16) || (typeA == 16 && typeB == 7)) {
            return 13;
        }

        if ((typeA == 8 && typeB == 11) || (typeA == 11 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 12) || (typeA == 12 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 13) || (typeA == 13 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 16) || (typeA == 16 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 17) || (typeA == 17 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 9 && typeB == 16) || (typeA == 16 && typeB == 9)) {
            return 13;
        }

        if ((typeA == 11 && typeB == 16) || (typeA == 16 && typeB == 11)) {
            return 13;
        }

        if ((typeA == 11 && typeB == 17) || (typeA == 17 && typeB == 11)) {
            return 7;
        }

        if ((typeA == 12 && typeB == 16) || (typeA == 16 && typeB == 12)) {
            return 13;
        }

        if ((typeA == 13 && typeB == 17) || (typeA == 17 && typeB == 13)) {
            return 9;
        }

        return typeA; // breed is not in the table so it will return the first
                      // parent type
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }
        if (this.getSubType() == 60 && !getIsTamed() && isZebraRunning()) {
            return false;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && !getIsRideable() && stack.getItem() == Items.SADDLE) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            setRideable(true);
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && stack.getItem() == Items.IRON_HORSE_ARMOR && isArmored()) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON);
            }
            dropArmor();
            setArmorType((byte) 1);
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && stack.getItem() == Items.GOLDEN_HORSE_ARMOR && isArmored()) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON);
            }
            dropArmor();
            setArmorType((byte) 2);
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && stack.getItem() == Items.DIAMOND_HORSE_ARMOR && isArmored()) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON);
            }
            dropArmor();
            setArmorType((byte) 3);
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && stack.getItem() == MoCItems.HORSEARMORCRYSTAL && isMagicHorse()) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON);
            }
            dropArmor();
            setArmorType((byte) 4);
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            return true;
        }

        // transform to undead, or heal undead horse
        if (!stack.isEmpty() && this.getIsTamed() && stack.getItem() == MoCItems.ESSENCEUNDEAD) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (this.isUndead() || getIsGhost()) {
                this.setHealth(getMaxHealth());

            }

            // pegasus, dark pegasus, or bat horse
            if (this.getSubType() == 39 || this.getSubType() == 32 || this.getSubType() == 40) {

                // transformType = 25; //undead pegasus
                transform(25);

            } else if (this.getSubType() == 36 || (this.getSubType() > 47 && this.getSubType() < 60)) // unicorn or fairies
            {

                // transformType = 24; //undead unicorn
                transform(24);
            } else if (this.getSubType() < 21 || this.getSubType() == 60 || this.getSubType() == 61) // regular horses or zebras
            {

                // transformType = 23; //undead
                transform(23);
            }

            drinkingHorse();
            return true;
        }

        // to transform to nightmares: only pure breeds
        if (!stack.isEmpty() && this.getIsTamed() && stack.getItem() == MoCItems.ESSENCEFIRE) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (this.isNightmare()) {
                if (getIsAdult() && getHealth() == getMaxHealth()) {
                    this.eatenpumpkin = true;
                }
                this.setHealth(getMaxHealth());

            }
            if (this.getSubType() == 61) {
                //nightmare
                transform(38);
            }
            drinkingHorse();
            return true;
        }

        // transform to dark pegasus
        if (!stack.isEmpty() && this.getIsTamed() && stack.getItem() == MoCItems.ESSENCEDARKNESS) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (this.getSubType() == 32) {
                if (getIsAdult() && getHealth() == getMaxHealth()) {
                    this.eatenpumpkin = true;
                }
                this.setHealth(getMaxHealth());
            }

            if (this.getSubType() == 61) {
                transform(32); //horsezorse to bat horse
            }

            if (this.getSubType() == 39) // pegasus to darkpegasus
            {
                //darkpegasus
                transform(40);
            }
            drinkingHorse();
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && stack.getItem() == MoCItems.ESSENCELIGHT) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (this.isMagicHorse()) {
                if (getIsAdult() && getHealth() == getMaxHealth()) {
                    this.eatenpumpkin = true;
                }
                this.setHealth(getMaxHealth());
            }

            if (this.isNightmare()) {
                // unicorn
                transform(36);
            }
            if (this.getSubType() == 32 && this.getPosY() > 128D) // bathorse to pegasus
            {
                // pegasus
                transform(39);
            }
            // to return undead horses to pristine conditions
            if (this.isUndead() && this.getIsAdult() && !this.world.isRemote) {
                setEdad(10);
                if (this.getSubType() >= 26) {
                    setType(getSubType() - 3);
                }
            }
            drinkingHorse();
            return true;
        }

        if (!stack.isEmpty() && this.isAmuletHorse() && this.getIsTamed()) {
            if ((this.getSubType() == 26 || this.getSubType() == 27 || this.getSubType() == 28) && stack.getItem() == MoCItems.AMULETBONE) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                vanishHorse();
                return true;
            }

            if ((this.getSubType() > 47 && this.getSubType() < 60) && stack.getItem() == MoCItems.AMULETFAIRY) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                vanishHorse();
                return true;
            }

            if ((this.getSubType() == 39 || this.getSubType() == 40) && (stack.getItem() == MoCItems.AMULETPEGASUS)) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                vanishHorse();
                return true;
            }

            if ((this.getSubType() == 21 || this.getSubType() == 22) && (stack.getItem() == MoCItems.AMULETGHOST)) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                vanishHorse();
                return true;
            }

        }

        //TODO: Make it work with all dye
        if (!stack.isEmpty() && (stack.getItem() == Items.RED_DYE) && this.getSubType() == 50) {

            int colorInt = stack.getDamage();
            switch (colorInt) {
                case 14: //orange
                    transform(59);
                    break;
                case 13: //magenta TODO
                    //transform(46);
                    break;
                case 12: //light blue
                    transform(51);
                    break;
                case 11: //yellow
                    transform(48);
                    break;
                case 10: //light green
                    transform(53);
                    break;
                case 9: //pink
                    transform(52);
                    break;
                case 8: //gray TODO
                    //transform(50);
                    break;
                case 7: //light gray TODO
                    //transform(50);
                    break;
                case 6: //cyan
                    transform(57);
                    break;
                case 5: //purple
                    transform(49);
                    break;
                case 4: //dark blue
                    transform(56);
                    break;
                case 3: //brown TODO
                    //transform(50);
                    break;
                case 2: //green
                    transform(58);
                    break;
                case 1: //red
                    transform(55);
                    break;
                case 0: //black
                    transform(54);
                    break;

            }

            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            eatingHorse();
            return true;
        }

        // zebra easter egg
        if (!stack.isEmpty()
                && (this.getSubType() == 60)
                && ((stack.getItem() == Items.MUSIC_DISC_11) || (stack.getItem() == Items.MUSIC_DISC_13) || (stack.getItem() == Items.MUSIC_DISC_CAT)
                        || (stack.getItem() == Items.MUSIC_DISC_CHIRP) || (stack.getItem() == Items.MUSIC_DISC_FAR)
                        || (stack.getItem() == Items.MUSIC_DISC_MALL) || (stack.getItem() == Items.MUSIC_DISC_MELLOHI)
                        || (stack.getItem() == Items.MUSIC_DISC_STAL) || (stack.getItem() == Items.MUSIC_DISC_STRAD) || (stack.getItem() == Items.MUSIC_DISC_WARD))) {
            player.setHeldItem(hand, ItemStack.EMPTY);
            if (!this.world.isRemote) {
                ItemEntity entityitem1 = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(MoCItems.RECORDSHUFFLE, 1));
                entityitem1.setPickupDelay(20);
                this.world.addEntity(entityitem1);
            }
            eatingHorse();
            return true;
        }
        if (!stack.isEmpty() && (stack.getItem() == Items.WHEAT) && !isMagicHorse() && !isUndead()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            if (!this.world.isRemote) {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper()) {
                    setTemper(getMaxTemper() - 5);
                }
            }
            if ((getHealth() + 5) > getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
            eatingHorse();
            if (!getIsAdult() && (getEdad() < getMaxEdad())) {
                setEdad(getEdad() + 1);
            }
            return true;
        }

        if (!stack.isEmpty() && (stack.getItem() == MoCItems.SUGARLUMP) && !isMagicHorse() && !isUndead()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            if (!this.world.isRemote) {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper()) {
                    setTemper(getMaxTemper() - 5);
                }
            }
            if ((getHealth() + 10) > getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
            eatingHorse();
            if (!getIsAdult() && (getEdad() < getMaxEdad())) {
                setEdad(getEdad() + 2);
            }
            return true;
        }

        if (!stack.isEmpty() && (stack.getItem() == Items.BREAD) && !isMagicHorse() && !isUndead()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            if (!this.world.isRemote) {
                setTemper(getTemper() + 100);
                if (getTemper() > getMaxTemper()) {
                    setTemper(getMaxTemper() - 5);
                }
            }
            if ((getHealth() + 20) > getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
            eatingHorse();
            if (!getIsAdult() && (getEdad() < getMaxEdad())) {
                setEdad(getEdad() + 3);
            }
            return true;
        }

        if (!stack.isEmpty() && ((stack.getItem() == Items.APPLE) || (stack.getItem() == Items.GOLDEN_APPLE)) && !isMagicHorse()
                && !isUndead()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            this.setHealth(getMaxHealth());
            eatingHorse();
            if (!getIsAdult() && (getEdad() < getMaxEdad()) && !this.world.isRemote) {
                setEdad(getEdad() + 1);
            }

            return true;
        }

        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) && (isBagger())) {
            if (getIsChested()) {
                return false;
            }
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            //entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setIsChested(true);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            return true;
        }
        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == MoCItems.HAYSTACK)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            // eatinghaystack = true;
            setSitting(true);
            eatingHorse();
            if (!isMagicHorse() && !isUndead()) {
                this.setHealth(getMaxHealth());
            }

            return true;
        }
        *//*if (getIsChested() && player.isCrouching()) {
            // if first time opening horse chest, we must initialize it
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("HorseChest", getInventorySize());// , new
            }
            // only open this chest on server side
            if (!this.world.isRemote) {
                player.displayGUIChest(this.localchest);
            }
            return true;

        }*//*

        if (!stack.isEmpty()
                && ((stack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN)) || (stack.getItem() == Items.MUSHROOM_STEW)
                        || (stack.getItem() == Items.CAKE) || (stack.getItem() == Items.GOLDEN_CARROT))) {
            if (!getIsAdult() || isMagicHorse() || isUndead()) {
                return false;
            }
            stack.shrink(1);
            if (stack.getItem() == Items.MUSHROOM_STEW) {
                if (stack.isEmpty()) {
                    player.setHeldItem(hand, new ItemStack(Items.BOWL));
                } else {
                    player.inventory.addItemStackToInventory(new ItemStack(Items.BOWL));
                }
            } else if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            this.eatenpumpkin = true;
            this.setHealth(getMaxHealth());
            eatingHorse();
            return true;
        }

        if (!stack.isEmpty() && (stack.getItem() == MoCItems.WHIP) && getIsTamed() && (!this.isBeingRidden())) {
            setSitting(!getIsSitting());// eatinghaystack = !eatinghaystack;
            return true;
        }

        if (getIsRideable() && getIsAdult() && (!this.isBeingRidden())) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
                this.gestationtime = 0;
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    /**
     * Can this horse be trapped in a special amulet?
     */
    public boolean isAmuletHorse() {

        return (this.getSubType() >= 48 && this.getSubType() < 60) || this.getSubType() == 40 || this.getSubType() == 39 || this.getSubType() == 21
                || this.getSubType() == 22 || this.getSubType() == 26 || this.getSubType() == 27 || this.getSubType() == 28;
    }

    /**
     * Can wear regular armor
     */
    public boolean isArmored() {
        return (this.getSubType() < 21);
    }

    /**
     * able to carry bags
     *
     * @return
     */
    public boolean isBagger() {
        return (this.getSubType() == 66) // mule
                || (this.getSubType() == 65) // donkey
                || (this.getSubType() == 67) // zonkey
                || (this.getSubType() == 39) // pegasi
                || (this.getSubType() == 40) // black pegasi
                || (this.getSubType() == 25) // undead pegasi
                || (this.getSubType() == 28) // skely pegasi
                || (this.getSubType() >= 45 && this.getSubType() < 60) // fairy
        ;
    }

    /**
     * Falls slowly
     */
    public boolean isFloater() {
        return this.getSubType() == 36 // unicorn
                || this.getSubType() == 27 // skely unicorn
                || this.getSubType() == 24 // undead unicorn
                || this.getSubType() == 22; // not winged ghost

    }

    @Override
    public boolean isFlyer() {
        return this.getSubType() == 39 // pegasus
                || this.getSubType() == 40 // dark pegasus
                || (this.getSubType() >= 45 && this.getSubType() < 60) //fairy
                || this.getSubType() == 32 // bat horse
                || this.getSubType() == 21 // ghost winged
                || this.getSubType() == 25 // undead pegasus
                || this.getSubType() == 28;// skely pegasus
    }

    /**
     * Is this a ghost horse?
     *
     * @return
     */
    @Override
    public boolean getIsGhost() {

        return this.getSubType() == 21 || this.getSubType() == 22;
    }

    /**
     * Can wear magic armor
     */
    public boolean isMagicHorse() {
        return

        this.getSubType() == 39 || this.getSubType() == 36 || this.getSubType() == 32 || this.getSubType() == 40 || (this.getSubType() >= 45 && this.getSubType() < 60) //fairy
                || this.getSubType() == 21 || this.getSubType() == 22;
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting() || (this.isVehicle()) || this.standCounter != 0 || this.shuffleCounter != 0 || this.getVanishC() != 0;
    }

    /**
     * Is this a Nightmare horse?
     */
    public boolean isNightmare() {

        return this.getSubType() == 38;
    }

    /**
     * Rare horse that can be transformed into Nightmares or Bathorses or give
     * ghost horses on dead
     */
    public boolean isPureBreed() {

        return (this.getSubType() > 10 && this.getSubType() < 21);
    }

    /**
     * Mobs don't attack you if you're riding one of these they won't reproduce
     * either
     *
     * @return
     */
    public boolean isUndead() {
        return (this.getSubType() == 23) || (this.getSubType() == 24) || (this.getSubType() == 25) || (this.getSubType() == 26) // skely
                || (this.getSubType() == 27) // skely unicorn
                || (this.getSubType() == 28); // skely pegasus
    }

    /**
     * Has an unicorn? to render it and buckle entities!
     *
     * @return
     */
    public boolean isUnicorned() {

        return this.getSubType() == 36 || (this.getSubType() >= 45 && this.getSubType() < 60) || this.getSubType() == 27 || this.getSubType() == 24;
    }

    public boolean isZebraRunning() {
        boolean flag = false;
        PlayerEntity ep1 = this.level.getNearestPlayer(this, 8D);
        if (ep1 != null) {
            flag = true;
            if (ep1.getVehicle() != null && ep1.getVehicle() instanceof MoCEntityHorse) {
                MoCEntityHorse playerHorse = (MoCEntityHorse) ep1.getVehicle();
                if (playerHorse.getSubType() == 16 || playerHorse.getSubType() == 17 || playerHorse.getSubType() == 60 || playerHorse.getSubType() == 61) {
                    flag = false;
                }
            }

        }
        if (flag) {
            MoCTools.runLikeHell(this, ep1);
        }
        return flag;
    }

    public void LavaFX() {
        double var2 = level.random.nextGaussian() * 0.02D;
        double var4 = level.random.nextGaussian() * 0.02D;
        double var6 = level.random.nextGaussian() * 0.02D;
        level.addParticle(ParticleTypes.LAVA, getX() + level.random.nextFloat() * getBbWidth() - getBbWidth(), getY()
                + 0.5D + level.random.nextFloat() * getBbHeight(), getZ() + level.random.nextFloat() * getBbWidth()
                - getBbWidth(), var2, var4, var6);
    }

    public void MaterializeFX() {

    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    @Override
    public int nameYOffset() {
        if (this.getIsAdult()) {
            return -80;
        } else {
            return (-5 - getEdad());
        }
    }

    private boolean nearMusicBox() {
        // only works server side
        if (this.level.isClientSide) {
            return false;
        }

        boolean flag = false;
        JukeboxTileEntity jukebox = MoCTools.nearJukeBoxRecord(this, 6D);
        if (jukebox != null && jukebox.getRecord() != null) {
            Item record = jukebox.getRecord().getItem();
            Item shuffleRecord = MoCItems.RECORDSHUFFLE;
            if (record == shuffleRecord) {
                flag = true;
                if (this.shuffleCounter > 1000) {
                    this.shuffleCounter = 0;
//                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 102), new TargetPoint(
//                            this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
                    flag = false;
                }
            }
        }
        return flag;
    }

    // changed to public since we need to send this info to server
    public void NightmareEffect() {
        if (!MoCTools.mobGriefing(this.level)) {
            setNightmareInt(getNightmareInt() - 1);
            return;
        }
        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getZ());
        BlockPos pos = new BlockPos(i, j, k);
        BlockState blockstate = this.level.getBlockState(pos.offset(-1, 0, -1));

        BlockEvent.BreakEvent event = null;
        if (!this.level.isClientSide) {
            /*try {
                event =
                        new BlockEvent.BreakEvent(this.world, pos, blockstate, FakePlayerFactory.get(
                                DimensionManager.getWorld(this.world.dimension.getType()), MoCreatures.MOCFAKEPLAYER));
            } catch (Throwable t) {
            }

        }
        if (event != null && !event.isCanceled()) {
            this.world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 3);//MC1.5
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            if ((entityplayer != null) && (entityplayer.isBurning())) {
                entityplayer.extinguish();
            }*/
            setNightmareInt(getNightmareInt() - 1);
        }
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (!this.level.isClientSide) {
            if ((this.random.nextInt(10) == 0) && (this.getSubType() == 23) || (this.getSubType() == 24) || (this.getSubType() == 25)) {
                MoCTools.spawnMaggots(this.level, this);
            }

            if (getIsTamed() && (isMagicHorse() || isPureBreed()) && !getIsGhost() && this.random.nextInt(4) == 0) {
                MoCEntityHorse entityhorse1 = new MoCEntityHorse(MoCEntities.WILDHORSE, this.level);
                entityhorse1.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(entityhorse1);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR);

                entityhorse1.setOwnerId(this.getOwnerId());
                entityhorse1.setTamed(true);
                PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                if (entityplayer != null) {
                    MoCTools.tameWithName(entityplayer, entityhorse1);
                }

                entityhorse1.setAdult(false);
                entityhorse1.setEdad(1);
                int l = 22;
                if (this.isFlyer()) {
                    l = 21;
                }
                entityhorse1.setType(l);
            }

        }
    }

    @Override
    public void aiStep() {
        /**
         * slow falling
         */
        if (isFlyer() || isFloater()) {
            if (!this.onGround && (this.getDeltaMovement().y < 0.0D)) {
                this.getDeltaMovement().multiply(1, 0.6D, 1);
            }
        }

        if (this.random.nextInt(200) == 0) {
            moveTail();
        }

        if ((getSubType() == 38) && (this.random.nextInt(50) == 0) && this.level.isClientSide) {
            LavaFX();
        }

        if ((getSubType() == 36) && isOnAir() && this.level.isClientSide) {
            StarFX();
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
            if (this.wingFlapCounter != 0 && this.wingFlapCounter % 5 == 0 && this.level.isClientSide) {
                StarFX();
            }
            if (this.wingFlapCounter == 5 && !this.level.isClientSide) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_WINGFLAP);
            }
        }

        if (isUndead() && (this.getSubType() < 26) && getIsAdult() && (this.random.nextInt(20) == 0)) {
            if (!this.level.isClientSide) {
                if (this.random.nextInt(16) == 0) {
                    setEdad(getEdad() + 1);
                }
                if (getEdad() >= 399) {
                    setType(this.getSubType() + 3);
                }
            } else {
                UndeadFX();
            }

        }

        super.aiStep();

        if (!this.level.isClientSide) {
            /**
             * Shuffling LMFAO!
             */
            if (this.getSubType() == 60 && getIsTamed() && this.random.nextInt(50) == 0 && nearMusicBox() && shuffleCounter == 0) {
                shuffleCounter = 1;
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 101),
//                        new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            }

            if ((this.random.nextInt(300) == 0) && (this.deathTime == 0)) {
                this.setHealth(getHealth() + 1);
                if (getHealth() > getMaxHealth()) {
                    this.setHealth(getMaxHealth());
                }
            }

            if (!getIsSitting() && !getIsTamed() && this.random.nextInt(300) == 0) {
                setSitting(true);
            }

            if (getIsSitting() && ++this.countEating > 50 && !getIsTamed()) {
                this.countEating = 0;
                setSitting(false);
            }

            if ((getSubType() == 38) && (this.isVehicle()) && (getNightmareInt() > 0) && (this.random.nextInt(2) == 0)) {
                NightmareEffect();
            }

            /**
             * zebras on the run!
             */
            /*
            if (this.getType() == 60 && !getIsTamed()) {
                boolean flag = isZebraRunning();
            }*/

            /**
             * foal following mommy!
             */
            /*if (!getIsAdult() && (this.rand.nextInt(200) == 0)) {
                setEdad(getEdad() + 1);
                if (getEdad() >= 100) {
                    setAdult(true);
                    setBred(false);
                    MoCEntityHorse mommy = getClosestMommy(this, 16D);
                    if (mommy != null) {
                        mommy.setBred(false);
                    }
                }
            }*/

            /**
             * Buckling
             */
            if ((this.sprintCounter > 0 && this.sprintCounter < 150) && isUnicorned() && (this.isVehicle())) {
                MoCTools.buckleMobs(this, 2D, this.level);
            }

            if (isFlyer() && !getIsTamed() && this.random.nextInt(100) == 0 && !isMovementCeased() && !getIsSitting()) {
                wingFlap();
            }

            if (!ReadyforParenting(this)) {
                return;
            }

            int i = 0;

            List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(8D, 3D, 8D));
            for (int j = 0; j < list.size(); j++) {
                Entity entity = list.get(j);
                if (entity instanceof MoCEntityHorse || entity instanceof HorseEntity) {
                    i++;
                }
            }

            if (i > 1) {
                return;
            }
            List<Entity> list1 = this.level.getEntities(this, getBoundingBox().expandTowards(4D, 2D, 4D));
            for (int k = 0; k < list1.size(); k++) {
                Entity horsemate = list1.get(k);
                boolean flag = (horsemate instanceof HorseEntity);
                if (!(horsemate instanceof MoCEntityHorse || flag) || (horsemate == this)) {
                    continue;
                }

                if (!flag) {
                    if (!ReadyforParenting((MoCEntityHorse) horsemate)) {
                        return;
                    }
                }

                if (this.random.nextInt(100) == 0) {
                    this.gestationtime++;
                }

                if (this.gestationtime % 3 == 0) {
//                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHeart(this.getEntityId()),
//                            new TargetPoint(this.world.dimension.getType().getId(), this.posX, this.posY, this.posZ, 64));
                }

                if (this.gestationtime <= 50) {
                    continue;
                }
                MoCEntityHorse baby = new MoCEntityHorse(MoCEntities.WILDHORSE, this.level);
                baby.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(baby);
                MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
                this.eatenpumpkin = false;
                this.gestationtime = 0;
                //this.setBred(true);

                int horsemateType;// = 0;
                if (flag) {
                    horsemateType = TranslateVanillaHorseType((HorseEntity) horsemate);
                    if (horsemateType == -1) {
                        return;
                    }
                } else {
                    horsemateType = ((MoCEntityHorse) horsemate).getSubType();
                    ((MoCEntityHorse) horsemate).eatenpumpkin = false;
                    ((MoCEntityHorse) horsemate).gestationtime = 0;
                }
                int l = HorseGenetics(this.getSubType(), horsemateType);

                if (l == 50 || l == 54) // fairy horse!
                {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR);
                    if (!flag) {
                        ((MoCEntityHorse) horsemate).dissapearHorse();
                    }
                    this.dissapearHorse();
                }
                baby.setOwnerId(this.getOwnerId());
                baby.setTamed(true);
                //baby.setBred(true);
                baby.setAdult(false);
                PlayerEntity entityplayer = this.level.getPlayerByUUID(this.getOwnerId());
                if (entityplayer != null) {
                    MoCTools.tameWithName(entityplayer, baby);
                }
                baby.setType(l);
                break;
            }
        }

    }

    /**
     * Obtains the 'Type' of vanilla horse for inbreeding with MoC Horses
     *
     * @param horse
     * @return
     */
    private int TranslateVanillaHorseType(AbstractHorseEntity horse) {
        if (horse instanceof DonkeyEntity) {
            return 65; // donkey
        }
        /*if (horse instanceof HorseEntity) { TODO: get the value of the vanilla horse variant
            switch ((byte) ((HorseEntity) horse).getHorseVariant) {
                case 0: //white
                    return 1;
                case 1: //creamy
                    return 2;
                case 3: //brown
                    return 3;
                case 4: //black
                    return 5;
                case 5: //gray
                    return 9;
                case 6: //dark brown
                    return 4;
                default:
                    return 3;
            }

        }*/
        return -1;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.shuffleCounter > 0) {
            ++this.shuffleCounter;
            if (this.level.isClientSide && this.shuffleCounter % 20 == 0) {
                double var2 = this.random.nextGaussian() * 0.5D;
                double var4 = this.random.nextGaussian() * -0.1D;
                double var6 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.NOTE, this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(), this.getY()
                        + 0.5D + this.random.nextFloat() * this.getBbHeight(), this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(), var2, var4,
                        var6);
            }

            if (!this.level.isClientSide && !nearMusicBox()) {
                this.shuffleCounter = 0;
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 102),
//                        new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            }
        }

        if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
            this.mouthCounter = 0;
        }

        if (this.standCounter > 0 && ++this.standCounter > 20) {
            this.standCounter = 0;
        }

        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }

        if (getVanishC() > 0) {

            setVanishC((byte) (getVanishC() + 1));

            if (getVanishC() < 15 && this.level.isClientSide) {
                VanishFX();

            }

            if (getVanishC() > 100) {
                setVanishC((byte) 101);
                MoCTools.dropHorseAmulet(this);
                dissapearHorse();
            }

            if (getVanishC() == 1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_VANISH);
            }

            if (getVanishC() == 70) {
                stand();
            }
        }

        if (this.sprintCounter > 0) {
            ++this.sprintCounter;
            if (this.sprintCounter < 150 && this.sprintCounter % 2 == 0 && this.level.isClientSide) {
                StarFX();
            }

            if (this.sprintCounter > 300) {
                this.sprintCounter = 0;
            }
        }

        /*if (this.wingFlapCounter > 0) {
            ++this.wingFlapCounter;
            if (this.wingFlapCounter % 5 == 0 && this.world.isRemote) {
                StarFX();
            }
            if (this.wingFlapCounter > 20) {
                this.wingFlapCounter = 0;

            }
        }*/

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_TRANSFORM);
            }

            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                if (this.transformType != 0) {
                    dropArmor();
                    setType(this.transformType);
                }
            }
        }

        if (getIsGhost() && getEdad() < 10 && this.random.nextInt(7) == 0) {
            setEdad(getEdad() + 1);
        }

        if (getIsGhost() && getEdad() == 9) {
            setEdad(100);
            setAdult(true);
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    public boolean ReadyforParenting(MoCEntityHorse entityhorse) {
        int i = entityhorse.getSubType();
        return (!entityhorse.isVehicle()) && (entityhorse.getVehicle() == null) && entityhorse.getIsTamed() && entityhorse.eatenpumpkin
                && entityhorse.getIsAdult() && !entityhorse.isUndead() && !entityhorse.getIsGhost() && (i != 61) && (i < 66);
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    /**
     * Horse Types
     *
     * 1 White . 2 Creamy. 3 Brown. 4 Dark Brown. 5 Black.
     *
     * 6 Bright Creamy. 7 Speckled. 8 Pale Brown. 9 Grey. 10 11 Pinto . 12
     * Bright Pinto . 13 Pale Speckles.
     *
     * 16 Spotted 17 Cow.
     *
     *
     *
     *
     * 21 Ghost (winged) 22 Ghost B
     *
     * 23 Undead 24 Undead Unicorn 25 Undead Pegasus
     *
     * 26 skeleton 27 skeleton unicorn 28 skeleton pegasus
     *
     * 30 bug horse
     *
     * 32 Bat Horse
     *
     * 36 Unicorn
     *
     * 38 Nightmare? 39 White Pegasus 40 Black Pegasus
     *
     * 50 fairy white 51 fairy blue 52 fairy pink 53 fairy light green
     *
     * 60 Zebra 61 Zorse
     *
     * 65 Donkey 66 Mule 67 Zonky
     */

    @Override
    public void selectType() {
        checkSpawningBiome();
        if (getSubType() == 0) {
            if (this.random.nextInt(5) == 0) {
                setAdult(false);
            }
            int j = this.random.nextInt(100);
            int i = MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.zebraChance.get();
            if (j <= (33 - i)) {
                setType(6);
            } else if (j <= (66 - i)) {
                setType(7);
            } else if (j <= (99 - i)) {
                setType(8);
            } else {
                setType(60);// zebra
            }
        }
    }

    public void setNightmareInt(int i) {
        this.nightmareInt = i;
    }

    public void setReproduced(boolean var1) {
        this.hasReproduced = var1;
    }

    /**
     * New networked to fix SMP issues
     *
     * @return
     */
    public void setVanishC(byte i) {
        this.vanishCounter = i;
    }

    private void stand() {
        if (!this.isVehicle() && !this.isOnAir()) {
            this.standCounter = 1;
        }
    }

    public void StarFX() { //TODO: StarFX
//        MoCreatures.proxy.StarFX(this);
    }

    /**
     * Used to flicker ghosts
     *
     * @return
     */
    public float tFloat() {
        if (++this.fCounter > 60) {
            this.fCounter = 0;
            this.transFloat = (this.random.nextFloat() * (0.6F - 0.3F) + 0.3F);
        }

        if (getIsGhost() && getEdad() < 10) {
            this.transFloat = 0;
        }
        return this.transFloat;
    }

    public void transform(int tType) {
        if (!this.level.isClientSide) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), tType),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }

        this.transformType = tType;
        if (!this.isVehicle() && this.transformType != 0) {
            dropArmor();
            this.transformCounter = 1;
        }
    }

    public void UndeadFX() {
        ParticleType particle = MoCParticleTypes.UNDEAD_FX;
    }

    public void VanishFX() {
//        MoCreatures.proxy.VanishFX(this);
    }

    /**
     * Called to vanish Horse
     */

    public void vanishHorse() {
        this.getNavigation().stop();
        this.setDeltaMovement(0D, this.getDeltaMovement().y, 0D);

        if (this.isBagger()) {
//            MoCTools.dropInventory(this, this.localchest);
            dropBags();
        }
        if (!this.level.isClientSide) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageVanish(this.getEntityId()),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            setVanishC((byte) 1);
        }
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_VANISH);
    }

    @Override
    public void dropMyStuff() {
        dropArmor();
        MoCTools.dropSaddle(this, this.level);
        if (this.isBagger()) {
//            MoCTools.dropInventory(this, this.localchest);
            dropBags();
        }
    }

    public void wingFlap() {
        if (this.isFlyer() && this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
            if (!this.level.isClientSide) {
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
//                        new TargetPoint(this.world.dimension.getType().getId(), this.posX, this.posY, this.posZ, 64));
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
        nbttagcompound.putBoolean("EatingHaystack", getIsSitting());
        nbttagcompound.putBoolean("ChestedHorse", getIsChested());
        nbttagcompound.putBoolean("HasReproduced", getHasReproduced());
        nbttagcompound.putBoolean("Bred", getHasBred());
        nbttagcompound.putInt("ArmorType", getArmorType());

        /*if (getIsChested() && this.localchest != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                // grab the current item stack
                this.localstack = this.localchest.getStackInSlot(i);
                if (this.localstack != null && !this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items", nbttaglist);
        }*/
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setSitting(nbttagcompound.getBoolean("EatingHaystack"));
        setBred(nbttagcompound.getBoolean("Bred"));
        setIsChested(nbttagcompound.getBoolean("ChestedHorse"));
        setReproduced(nbttagcompound.getBoolean("HasReproduced"));
        setArmorType((byte) nbttagcompound.getInt("ArmorType"));
        /*if (getIsChested()) {
            ListNBT nbttaglist = nbttagcompound.getList("Items", 10);
            this.localchest = new MoCAnimalChest("HorseChest", getInventorySize());
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }*/
    }

    @Override
    public void performAnimation(int animationType) {
        //23,24,25,32,36,38,39,40,51,52,53
        if (animationType >= 23 && animationType < 60) //transform
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }
        if (animationType == 3) //wing flap 
        {
            this.wingFlapCounter = 1;
        }
        if (animationType == 101) //zebra Shuffle starts
        {
            this.shuffleCounter = 1;
        }
        if (animationType == 102) //zebra Shuffle ends
        {
            this.shuffleCounter = 0;
        }
    }

    @Override
    public CreatureAttribute getMobType() {
        if (isUndead()) {
            return CreatureAttribute.UNDEAD;
        }
        return super.getMobType();
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return getIsTamed() && !isAmuletHorse();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4;
    }

    @Override
    public void setType(int i) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        if (getSubType() == 38 || getSubType() == 40) {
            //TODO: this.isImmuneToFire = true;
        }
        super.setType(i);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData livingdata, CompoundNBT dataTag) {
        if (getSubType() == 38 || getSubType() == 40) {
            //TODO: this.isImmuneToFire = true;
        }
        return super.finalizeSpawn(worldIn, difficulty, reason, livingdata, dataTag);
    }

    @Override
    public void positionRider(Entity passenger) {
        double dist = getSizeFactor() * (0.25D);
        double newPosX = this.getX() + (dist * Math.sin(this.yBodyRot / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.cos(this.yBodyRot / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getPassengersRidingOffset() + passenger.getMyRidingOffset(), newPosZ);
    }

    @Override
    public void makeEntityJump() {
        wingFlap();
        super.makeEntityJump();
    }
}
