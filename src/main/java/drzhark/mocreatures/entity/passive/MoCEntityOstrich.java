package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.FollowAdultGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCSoundEvents;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityOstrich extends MoCEntityTameableAnimal {

    private int eggCounter;
    private int hidingCounter;
    public int mouthCounter;
    public int wingCounter;
    public int sprintCounter;
    public int jumpCounter;
    public int transformCounter;
    public int transformType;
    public boolean canLayEggs;

    public MoCAnimalChest localchest;
    public ItemStack localstack;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.defineId(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EGG_WATCH = EntityDataManager.defineId(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.defineId(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_HIDING = EntityDataManager.defineId(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> HELMET_TYPE = EntityDataManager.defineId(MoCEntityOstrich.class, DataSerializers.INT);
    private static final DataParameter<Integer> FLAG_COLOR = EntityDataManager.defineId(MoCEntityOstrich.class, DataSerializers.INT);
    

    public MoCEntityOstrich(EntityType<? extends MoCEntityOstrich> type, World world) {
        super(type, world);
        setEdad(35);
        this.eggCounter = this.random.nextInt(1000) + 1000;
        this.maxUpStep = 1.0F;
        this.canLayEggs = false;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(4, new FollowAdultGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new MoCAlternateWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EGG_WATCH, Boolean.FALSE);
        this.entityData.define(CHESTED, Boolean.FALSE);
        this.entityData.define(RIDEABLE, Boolean.FALSE);
        this.entityData.define(IS_HIDING, Boolean.FALSE);
        this.entityData.define(HELMET_TYPE, 0);
        this.entityData.define(FLAG_COLOR, 0);
    }

    @Override
    public boolean getIsRideable() {
        return this.entityData.get(RIDEABLE);
    }

    @Override
    public void setRideable(boolean flag) {
        this.entityData.set(RIDEABLE, flag);
    }

    public boolean getEggWatching() {
        return this.entityData.get(EGG_WATCH);
    }

    public void setEggWatching(boolean flag) {
        this.entityData.set(EGG_WATCH, flag);
    }

    public boolean getHiding() {
        return this.entityData.get(IS_HIDING);
    }

    public void setHiding(boolean flag) {
        this.entityData.set(IS_HIDING, flag);
    }

    public int getHelmet() {
        return this.entityData.get(HELMET_TYPE);
    }

    public void setHelmet(int i) {
        this.entityData.set(HELMET_TYPE, i);
    }

    public int getFlagColor() {
        return this.entityData.get(FLAG_COLOR);
    }

    public void setFlagColor(int i) {
        this.entityData.set(FLAG_COLOR, i);
    }

    public boolean getIsChested() {
        return this.entityData.get(CHESTED);
    }

    public void setIsChested(boolean flag) {
        this.entityData.set(CHESTED, flag);
    }

    @Override
    public boolean isMovementCeased() {
        return (getHiding() || this.isVehicle());
    }

    @Override
    public boolean isNotScared() {
        return (getSubType() == 2 && getTarget() != null) || (getSubType() > 2);
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        //dmg reduction
        if (getIsTamed() && getHelmet() != 0) {
            int j = 0;
            switch (getHelmet()) {
                case 1:
                    j = 1;
                    break;
                case 5:
                case 6:
                case 2:
                    j = 2;
                    break;
                case 7:
                case 3:
                    j = 3;
                    break;
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                    j = 4;
                    break;
            }
            i -= j;
            if (i <= 0) {
                i = 1;
            }
        }

        if (super.hurt(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if (!(entity instanceof LivingEntity) || ((this.isVehicle()) && (entity == this.getVehicle()))
                    || (entity instanceof PlayerEntity && getIsTamed())) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers()) && getSubType() > 2) {
                setTarget((LivingEntity) entity);
                flapWings();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        dropMyStuff();
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if (entityIn instanceof PlayerEntity && !shouldAttackPlayers()) {
            return false;
        }
        openMouth();
        flapWings();
        return super.doHurtTarget(entityIn);
    }

    public float calculateMaxHealth() {
        switch (getSubType()) {
            case 1:
                return 10;
            case 2:
                return 20;
            case 3:
                return 25;
            case 4:
                return 25;
            case 5:
                return 35;
            case 6:
                return 35;
            default:
                return 20;
        }
    }

    @Override
    public boolean isPickable() {
        return !this.isVehicle();
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            /**
             * 1 = chick /2 = female /3 = male /4 = albino male /5 = nether ostrich /6 = wyvern
             */
            int j = this.random.nextInt(100);
            if (j <= (20)) {
                setType(1);
            } else if (j <= (65)) {
                setType(2);
            } else if (j <= (95)) {
                setType(3);
            } else {
                setType(4);
            }
        }
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
    }

    @Override
    public ResourceLocation getTexture() {
        if (this.transformCounter != 0 && this.transformType > 4) {
            String newText = "ostricha.png";
            if (this.transformType == 5) {
                newText = "ostriche.png";
            }
            if (this.transformType == 6) {
                newText = "ostrichf.png";
            }
            if (this.transformType == 7) {
                newText = "ostrichg.png";
            }
            if (this.transformType == 8) {
                newText = "ostrichh.png";
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

        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("ostrichc.png"); //chick
            case 2:
                return MoCreatures.getTexture("ostrichb.png"); //female
            case 3:
                return MoCreatures.getTexture("ostricha.png"); //male
            case 4:
                return MoCreatures.getTexture("ostrichd.png"); //albino
            case 5:
                return MoCreatures.getTexture("ostriche.png"); //nether
            case 6:
                return MoCreatures.getTexture("ostrichf.png"); //black wyvern
            case 7:
                return MoCreatures.getTexture("ostrichg.png"); //undead
            case 8:
                return MoCreatures.getTexture("ostrichh.png"); //unicorned
            default:
                return MoCreatures.getTexture("ostricha.png");
        }
    }

    @Override
    public double getCustomSpeed() {
        double OstrichSpeed = 0.8D;
        if (getSubType() == 1) {
            OstrichSpeed = 0.8;
        } else if (getSubType() == 2) {
            OstrichSpeed = 0.8D;
        } else if (getSubType() == 3) {
            OstrichSpeed = 1.1D;
        } else if (getSubType() == 4) {
            OstrichSpeed = 1.3D;
        } else if (getSubType() == 5) {
            OstrichSpeed = 1.4D;
//            this.isImmuneToFire = true; TODO: Fire immunity
        }
        if (this.sprintCounter > 0 && this.sprintCounter < 200) {
            OstrichSpeed *= 1.5D;
        }
        if (this.sprintCounter > 200) {
            OstrichSpeed *= 0.5D;
        }
        return OstrichSpeed;
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (getHiding()) {
            this.yBodyRotO = this.yBodyRot = this.yRot = this.yRotO;
        }

        if (this.mouthCounter > 0 && ++this.mouthCounter > 20) {
            this.mouthCounter = 0;
        }

        if (this.wingCounter > 0 && ++this.wingCounter > 80) {
            this.wingCounter = 0;
        }

        if (this.jumpCounter > 0 && ++this.jumpCounter > 8) {
            this.jumpCounter = 0;
        }

        if (this.sprintCounter > 0 && ++this.sprintCounter > 300) {
            this.sprintCounter = 0;
        }

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_TRANSFORM);
            }

            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                if (this.transformType != 0) {
                    dropArmor();
                    setType(this.transformType);
                    selectType();
                }
            }
        }
    }

    public void transform(int tType) {
        if (!this.level.isClientSide) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), tType),
//                    new TargetPoint(this.world.dimension.getType(), this.posX, this.posY, this.posZ, 64));
        }
        this.transformType = tType;
        if (!this.isVehicle() && this.transformType != 0) {
            dropArmor();
            this.transformCounter = 1;
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType >= 5 && animationType < 9) //transform 5 - 8
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }

    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (getIsTamed() && !this.level.isClientSide && (this.random.nextInt(300) == 0) && (getHealth() <= getMaxHealth()) && (this.deathTime == 0)) {
            this.setHealth(getHealth() + 1);
        }

        if (!this.level.isClientSide) {
            //ostrich buckle!
            if (getSubType() == 8 && (this.sprintCounter > 0 && this.sprintCounter < 150) && (this.isVehicle()) && random.nextInt(15) == 0) {
                MoCTools.buckleMobs(this, 2D, this.level);
            }
            // TODO
            //shy ostriches will run and hide
            /*if (!isNotScared() && fleeingTick > 0 && fleeingTick < 2) {
                fleeingTick = 0;
                setHiding(true);
                this.getNavigator().clearPath();
            }*/

            if (getHiding()) {
                //wild shy ostriches will hide their heads only for a short term
                //tamed ostriches will keep their heads hidden until the whip is used again
                if (++this.hidingCounter > 500 && !getIsTamed()) {
                    setHiding(false);
                    this.hidingCounter = 0;
                }

            }

            if (getSubType() == 1 && (this.random.nextInt(200) == 0)) {
                //when is chick and becomes adult, change over to different type
                setEdad(getEdad() + 1);
                if (getEdad() >= 100) {
                    setAdult(true);
                    setType(0);
                    selectType();
                }
            }

            //egg laying
            if (this.canLayEggs && (getSubType() == 2) && !getEggWatching() && --this.eggCounter <= 0 && this.random.nextInt(5) == 0)// &&
            {
                PlayerEntity entityplayer1 = this.level.getNearestPlayer(this, 12D);
                if (entityplayer1 != null) {
                    double distP = MoCTools.getSqDistanceTo(entityplayer1, this.getX(), this.getY(), this.getZ());
                    if (distP < 10D) {
                        int OstrichEggType = 30;
                        MoCEntityOstrich maleOstrich = getClosestMaleOstrich(this, 8D);
                        if (maleOstrich != null && this.random.nextInt(100) < MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.ostrichEggDropChance.get()) {
                            MoCEntityEgg entityegg = new MoCEntityEgg(MoCEntities.EGG, this.level, OstrichEggType);
                            entityegg.setPos(this.getX(), this.getY(), this.getZ());
                            this.level.addFreshEntity(entityegg);

                            if (!this.getIsTamed()) {
                                setEggWatching(true);
                                if (maleOstrich != null) {
                                    maleOstrich.setEggWatching(true);
                                }
                                openMouth();
                            }

                            //TODO change sound
                            MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
                            //finds a male and makes it eggWatch as well
                            //MoCEntityOstrich entityOstrich = (MoCEntityOstrich) getClosestSpecificEntity(this, MoCEntityOstrich.class, 12D);
                            this.eggCounter = this.random.nextInt(2000) + 2000;
                            this.canLayEggs = false;
                        }
                    }
                }
            }

            //egg protection
            if (getEggWatching()) {
                //look for and protect eggs and move close
                MoCEntityEgg myEgg = (MoCEntityEgg) getBoogey(8D);
                if ((myEgg != null) && (MoCTools.getSqDistanceTo(myEgg, this.getX(), this.getY(), this.getZ()) > 4D)) {
                    Path pathentity = this.navigation.createPath(new BlockPos(myEgg.position()), 0);
                    this.navigation.moveTo(pathentity, 16F);
                }
                if (myEgg == null) //didn't find egg
                {
                    setEggWatching(false);

                    PlayerEntity eggStealer = this.level.getNearestPlayer(this, 10D);
                    if (eggStealer != null) {
                        this.level.getDifficulty();
                        if (!getIsTamed() && this.level.getDifficulty() != Difficulty.PEACEFUL) {
                            setTarget(eggStealer);
                            flapWings();
                        }
                    }
                }
            }
        }
    }

    protected MoCEntityOstrich getClosestMaleOstrich(Entity entity, double d) {
        double d1 = -1D;
        MoCEntityOstrich entityliving = null;
        List<Entity> list = this.level.getEntities(entity, entity.getBoundingBox().expandTowards(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!(entity1 instanceof MoCEntityOstrich) || ((entity1 instanceof MoCEntityOstrich) && ((MoCEntityOstrich) entity1).getSubType() < 3)) {
                continue;
            }

            double d2 = entity1.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityliving = (MoCEntityOstrich) entity1;
            }
        }

        return entityliving;
    }

    @Override
    public boolean entitiesToInclude(Entity entity) {
        return ((entity instanceof MoCEntityEgg) && (((MoCEntityEgg) entity).eggType == 30)

        );
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (getIsTamed() && (getSubType() > 1) && !stack.isEmpty() && !getIsRideable()
                && (stack.getItem() == MoCItems.HORSESADDLE || stack.getItem() == Items.SADDLE)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            setRideable(true);
            return true;
        }

        if (!getIsTamed() && !stack.isEmpty() && getSubType() == 2 && stack.getItem() == Items.MELON_SEEDS) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            openMouth();
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            this.canLayEggs = true;
            return true;
        }

        //makes the ostrich stay by hiding their heads
        if (!stack.isEmpty() && (stack.getItem() == MoCItems.WHIP) && getIsTamed() && (!this.isBeingRidden())) {
            setHiding(!getHiding());
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getSubType() > 1 && stack.getItem() == MoCItems.ESSENCEDARKNESS) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getSubType() == 6) {
                this.setHealth(getMaxHealth());
            } else {
                transform(6);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getSubType() > 1 && stack.getItem() == MoCItems.ESSENCEUNDEAD) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getSubType() == 7) {
                this.setHealth(getMaxHealth());
            } else {
                transform(7);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getSubType() > 1 && stack.getItem() == MoCItems.ESSENCELIGHT) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getSubType() == 8) {
                this.setHealth(getMaxHealth());
            } else {
                transform(8);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getSubType() > 1 && stack.getItem() == MoCItems.ESSENCEFIRE) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getSubType() == 5) {
                this.setHealth(getMaxHealth());
            } else {
                transform(5);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }
        if (getIsTamed() && getIsChested() && (getSubType() > 1) && !stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(Blocks.WHITE_WOOL)) {
            int colorInt = (stack.getDamage());
            if (colorInt == 0) {
                colorInt = 16;
            }
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            dropFlag();
            setFlagColor((byte) colorInt);
            return true;
        }

        if (!stack.isEmpty() && (getSubType() > 1) && getIsTamed() && !getIsChested() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            //entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setIsChested(true);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            return true;
        }

        *//*if (player.isSneaking() && getIsChested()) {
            // if first time opening a chest, we must initialize it
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("OstrichChest", 9);
            }
            if (!this.world.isRemote) {
                player.displayGUIChest(this.localchest);
            }
            return true;
        }*//*

        if (getIsTamed() && (getSubType() > 1) && !stack.isEmpty()) {

            Item item = stack.getItem();
            if (item instanceof ArmorItem && ((ArmorItem) item).getEquipmentSlot() == EquipmentSlotType.HEAD) {
                final ArmorItem itemArmor = (ArmorItem) stack.getItem();
                byte helmetType = 0;
                if (stack.getItem() == Items.LEATHER_HELMET) {
                    helmetType = 1;
                } else if (stack.getItem() == Items.IRON_HELMET) {
                    helmetType = 2;
                } else if (stack.getItem() == Items.GOLDEN_HELMET) {
                    helmetType = 3;
                } else if (stack.getItem() == Items.DIAMOND_HELMET) {
                    helmetType = 4;
                } else if (stack.getItem() == MoCItems.HELMETHIDE) {
                    helmetType = 5;
                } else if (stack.getItem() == MoCItems.HELMETFUR) {
                    helmetType = 6;
                } else if (stack.getItem() == MoCItems.HELMETCROC) {
                    helmetType = 7;
                } else if (stack.getItem() == MoCItems.SCORPHELMETDIRT) {
                    helmetType = 9;
                } else if (stack.getItem() == MoCItems.SCORPHELMETFROST) {
                    helmetType = 10;
                } else if (stack.getItem() == MoCItems.SCORPHELMETCAVE) {
                    helmetType = 11;
                } else if (stack.getItem() == MoCItems.SCORPHELMETNETHER) {
                    helmetType = 12;
                }

                if (helmetType != 0) {
                    player.setHeldItem(hand, ItemStack.EMPTY);
                    dropArmor();
                    this.setItemStackToSlot(itemArmor.getEquipmentSlot(), stack);
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
                    setHelmet(helmetType);
                    return true;
                }
            }
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setHiding(false);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    /**
     * Drops a block of the color of the flag if carrying one
     */
    private void dropFlag() {
        if (!this.level.isClientSide && getFlagColor() != 0) {
            int color = getFlagColor();
            if (color == 16) {
                color = 0;
            }
            ItemEntity entityitem = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Blocks.WHITE_WOOL, 1));
            entityitem.setPickUpDelay(10);
            this.level.addFreshEntity(entityitem);
            setFlagColor((byte) 0);
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    private void flapWings() {
        this.wingCounter = 1;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        return MoCSoundEvents.ENTITY_OSTRICH_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        if (getSubType() == 1) {
            return MoCSoundEvents.ENTITY_OSTRICH_AMBIENT_BABY;
        }

        return MoCSoundEvents.ENTITY_OSTRICH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_OSTRICH_DEATH;
    }

//    @Override
//    protected Item getDropItem() {
//        boolean flag = (this.rand.nextInt(100) < MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.rareItemDropChance.get());
//        if (flag && (this.getSubType() == 8)) // unicorn
//        {
//            return MoCItems.unicornhorn;
//        }
//        if (this.getSubType() == 5 && flag) {
//            return MoCItems.heartfire;
//        }
//        if (this.getSubType() == 6 && flag) // bat horse
//        {
//            return MoCItems.heartdarkness;
//        }
//        if (this.getSubType() == 7) {
//            if (flag) {
//                return MoCItems.heartundead;
//            }
//            return Items.ROTTEN_FLESH;
//        }
//        return MoCItems.ostrichraw;
//    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setEggWatching(nbttagcompound.getBoolean("EggWatch"));
        setHiding(nbttagcompound.getBoolean("Hiding"));
        setHelmet(nbttagcompound.getInt("Helmet"));
        setFlagColor(nbttagcompound.getInt("FlagColor"));
        setIsChested(nbttagcompound.getBoolean("Bagged"));
        /*if (getIsChested()) {
            ListNBT nbttaglist = nbttagcompound.getList("Items", 10);
            this.localchest = new MoCAnimalChest("OstrichChest", 18);
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
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
        nbttagcompound.putBoolean("EggWatch", getEggWatching());
        nbttagcompound.putBoolean("Hiding", getHiding());
        nbttagcompound.putInt("Helmet", getHelmet());
        nbttagcompound.putInt("FlagColor", getFlagColor());
        nbttagcompound.putBoolean("Bagged", getIsChested());

        /*if (getIsChested() && this.localchest != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
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
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason reason) {
        //spawns in deserts and plains
        return getCanSpawnHereCreature() && getCanSpawnHereLiving();
    }

    @Override
    public int nameYOffset() {
        if (getSubType() > 1) {
            return -105;
        } else {
            return (-5 - getEdad());
        }
    }

    /*@Override
    public boolean updateMount() {
        return getIsTamed();
    }*/

    /* @Override
     public boolean forceUpdates() {
         return getIsTamed();
     }*/

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return MoCTools.isItemEdible(par1ItemStack.getItem());
    }

    @Override
    public void dropMyStuff() {
        if (!this.level.isClientSide) {
            dropArmor();
            MoCTools.dropSaddle(this, this.level);

            if (getIsChested()) {
//                MoCTools.dropInventory(this, this.localchest);
                MoCTools.dropCustomItem(this, this.level, new ItemStack(Blocks.CHEST, 1));
                setIsChested(false);
            }
        }

    }

    /**
     * Drops the helmet
     */
    @Override
    public void dropArmor() {
        if (!this.level.isClientSide) {
            final ItemStack itemStack = this.getItemBySlot(EquipmentSlotType.HEAD);
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof ArmorItem) {
                final ItemEntity entityitem = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemStack.copy());
                entityitem.setPickUpDelay(10);
                this.level.addFreshEntity(entityitem);
                }
            setHelmet((byte) 0);
        }
    }

    @Override
    public boolean isFlyer() {
        return this.isVehicle() && (getSubType() == 5 || getSubType() == 6);
    }

    @Override
    public boolean causeFallDamage(float f, float f1) {
        if (isFlyer()) {
            return false;
        }
        return super.causeFallDamage(f, f1);
    }

    @Override
    protected double myFallSpeed() {
        return 0.89D;
    }

    @Override
    protected double flyerThrust() {
        return 0.8D;
    }

    @Override
    protected float flyerFriction() {
        return 0.96F;
    }

    @Override
    protected boolean selfPropelledFlyer() {
        return getSubType() == 6;
    }

    @Override
    public void makeEntityJump() {
        if (this.jumpCounter > 5) {
            this.jumpCounter = 1;
        }
        if (this.jumpCounter == 0) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_WINGFLAP);
            this.jumpPending = true;
            this.jumpCounter = 1;
        }

    }

    @Override
    public CreatureAttribute getMobType() {
        if (getSubType() == 7) {
            return CreatureAttribute.UNDEAD;
        }
        return super.getMobType();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    //TODO 
    //improve fall flapping wing animation
    //IMPROVE DIVE CODE
    //ATTACK!
    //EGG LYING

    @Override
    public int getMaxEdad() {
        return 20;
    }
}
