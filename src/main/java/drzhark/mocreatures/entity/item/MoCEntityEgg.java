package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityPiranha;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import drzhark.mocreatures.entity.aquatic.MoCEntitySmallFish;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import drzhark.mocreatures.entity.passive.MoCEntityManticorePet;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class MoCEntityEgg extends LivingEntity {

    private int tCounter;
    private int lCounter;
    public int eggType;

    public MoCEntityEgg(EntityType<? extends MoCEntityEgg> type, World world) {
        super(type, world);
        this.tCounter = 0;
        this.lCounter = 0;
    }

    public MoCEntityEgg(EntityType<? extends MoCEntityEgg> type, World world, int eggType) {
        this(type, world);
        this.eggType = eggType;
    }

    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("egg.png");
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityEgg.registerAttributes().add(Attributes.MAX_HEALTH, 10.0D);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlotType slotIn) {
        return null;
    }

    @Override
    public void setItemSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public HandSide getMainArm() {
        return null;
    }

//    @Override
    public boolean handleWaterMovement() {
        return this.isInWater();
    }

    @Override
    public void playerTouch(PlayerEntity entityplayer) {
        int i = this.eggType;
        if (i == 30) {
            i = 31;
        }
        if ((this.lCounter > 10) && entityplayer.inventory.add(new ItemStack(MoCItems.MOCEGG, 1))) {
            this.playSound(SoundEvents.ITEM_PICKUP, 0.2F, (((this.random.nextFloat() - this.random.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            if (!this.level.isClientSide) {
                entityplayer.take(this, 1);

            }
            remove();
        }
    }

    @Override
    public void aiStep() {
        this.xxa = 0.0F;
        this.zza = 0.0F;
//        this.randomYawVelocity = 0.0F;
        travel(new Vector3d(this.xxa, this.yya, this.zza));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            if (this.random.nextInt(20) == 0) {
                this.lCounter++;
            }

            if (this.lCounter > 500) {
                PlayerEntity entityplayer1 = this.level.getNearestPlayer(this, 24D);
                if (entityplayer1 == null) {
                    this.remove();
                }
            }

            if (isInWater() && (getEggType() < 12 || getEggType() > 69) && (this.random.nextInt(20) == 0)) {
                this.tCounter++;
                if (this.tCounter % 5 == 0) {
                    this.getDeltaMovement().add(0, 0.2D, 0);
                }

                if (this.tCounter == 5) {
                    NotifyEggHatching();
                }

                if (this.tCounter >= 30) {
                    if (getEggType() <= 10) // fishy
                    {
                        MoCEntityFishy entityspawn = new MoCEntityFishy(MoCEntities.FISHY, this.level);
                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        entityspawn.setType(getEggType());
                        entityspawn.setEdad(30);
                        this.level.addFreshEntity(entityspawn);
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() == 11) // shark
                    {
                        MoCEntityShark entityspawn = new MoCEntityShark(MoCEntities.SHARK, this.level);
                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        entityspawn.setEdad(30);
                        this.level.addFreshEntity(entityspawn);
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() == 90) // piranha
                    {
                        MoCEntityPiranha entityspawn = new MoCEntityPiranha(MoCEntities.PIRANHA, this.level);
                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        this.level.addFreshEntity(entityspawn);
                        entityspawn.setEdad(30);
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() >= 80 && getEggType() < (80 + MoCEntitySmallFish.fishNames.length)) // smallfish
                    {
                        final int type = getEggType() - 79;
                        MoCEntitySmallFish entityspawn = MoCEntitySmallFish.createEntity(this.level, type);
                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        this.level.addFreshEntity(entityspawn);
                        entityspawn.setEdad(30);
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() >= 70 && getEggType() < (70 + MoCEntityMediumFish.fishNames.length)) // mediumfish
                    {
                        final int type = getEggType() - 69;
                        MoCEntityMediumFish entityspawn = MoCEntityMediumFish.createEntity(this.level, type);
                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        this.level.addFreshEntity(entityspawn);
                        entityspawn.setEdad(30);
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
                    remove();
                }
            }

            else if (!isInWater() && getEggType() > 20 && (this.random.nextInt(20) == 0)) // non aquatic creatures
            {
                this.tCounter++;
                //if (getEggType() == 30) tCounter = 0; //with this, wild ostriches won't spawn eggs.

                if (this.tCounter % 5 == 0) {
                    this.getDeltaMovement().add(0, 0.2D, 0);
                }

                if (this.tCounter == 5) {
                    NotifyEggHatching();
                }

                if (this.tCounter >= 30) {
                    if (getEggType() > 20 && getEggType() < 29) // snakes
                    {
                        MoCEntitySnake entityspawn = new MoCEntitySnake(MoCEntities.SNAKE, this.level);

                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        entityspawn.setType(getEggType() - 20);
                        entityspawn.setEdad(50);
                        this.level.addFreshEntity(entityspawn);
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() == 30 || getEggType() == 31 || getEggType() == 32) // Ostriches. 30 = wild egg, 31 = stolen egg
                    {
                        MoCEntityOstrich entityspawn = new MoCEntityOstrich(MoCEntities.OSTRICH, this.level);
                        int typeInt = 1;
                        /*if (this.world.dimension.doesWaterVaporize() || getEggType() == 32) {
                            typeInt = 5;
                        }*/
                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        entityspawn.setType(typeInt);
                        entityspawn.setEdad(35);
                        this.level.addFreshEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());

                        if (getEggType() == 31)//stolen egg that hatches a tamed ostrich
                        {
                            PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                            if (entityplayer != null) {
                                MoCTools.tameWithName(entityplayer, entityspawn);
                            }
                        }
                    }

                    if (getEggType() == 33) // Komodo
                    {
                        MoCEntityKomodo entityspawn = new MoCEntityKomodo(MoCEntities.KOMODO_DRAGON, this.level);

                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        entityspawn.setEdad(30);
                        this.level.addFreshEntity(entityspawn);
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() > 40 && getEggType() < 46) //scorpions for now it uses 41 - 45
                    {
                        MoCEntityPetScorpion entityspawn = new MoCEntityPetScorpion(MoCEntities.PET_SCORPION, this.level);
                        int typeInt = getEggType() - 40;
                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        this.level.addFreshEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() > 49 && getEggType() < 62) //wyverns for now it uses 50 - 61
                    {
                        MoCEntityWyvern entityspawn = new MoCEntityWyvern(MoCEntities.WYVERN, this.level);
                        int typeInt = getEggType() - 49;
                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        entityspawn.setEdad(30);
                        this.level.addFreshEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    if (getEggType() > 61 && getEggType() < 66) //manticorePets for now it uses 62 - 65
                    {
                        MoCEntityManticorePet entityspawn = new MoCEntityManticorePet(MoCEntities.MANTICORE_PET, this.level);
                        int typeInt = getEggType() - 61;
                        entityspawn.setPos(this.getX(), this.getY(), this.getZ());
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        entityspawn.setEdad(30);
                        this.level.addFreshEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
                    remove();
                }
            }
        }
    }

    private void NotifyEggHatching() {
        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
        if (entityplayer != null) {
            entityplayer.sendMessage(new TranslationTextComponent("Egg hatching soon! KEEP WATCH! The hatched creature located @ "
                    + (int) this.getX() + ", " + (int) this.getY() + ", " + (int) this.getZ() + " will be lost if you leave area"), Util.NIL_UUID);
        }
    }

    public int getSize() {
        if (getEggType() == 30 || getEggType() == 31) {
            return 170;
        }
        return 100;
    }

    public int getEggType() {
        return this.eggType;
    }

    public void setEggType(int eggType) {
        this.eggType = eggType;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        setEggType(nbttagcompound.getInt("EggType"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.putInt("EggType", getEggType());
    }

    @Override
    public boolean isInWall() {
        return false;
    }
}
