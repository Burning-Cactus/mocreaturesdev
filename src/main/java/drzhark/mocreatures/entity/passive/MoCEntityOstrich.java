package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.*;
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
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.<Boolean>createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EGG_WATCH = EntityDataManager.<Boolean>createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.<Boolean>createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_HIDING = EntityDataManager.<Boolean>createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> HELMET_TYPE = EntityDataManager.<Integer>createKey(MoCEntityOstrich.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FLAG_COLOR = EntityDataManager.<Integer>createKey(MoCEntityOstrich.class, DataSerializers.VARINT);
    

    public MoCEntityOstrich(EntityType<? extends MoCEntityOstrich> type, World world) {
        super(type, world);
        setEdad(35);
        this.eggCounter = this.rand.nextInt(1000) + 1000;
        this.stepHeight = 1.0F;
        this.canLayEggs = false;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(EGG_WATCH, Boolean.valueOf(false));
        this.dataManager.register(CHESTED, Boolean.valueOf(false));
        this.dataManager.register(RIDEABLE, Boolean.valueOf(false));
        this.dataManager.register(IS_HIDING, Boolean.valueOf(false));
        this.dataManager.register(HELMET_TYPE, Integer.valueOf(0));
        this.dataManager.register(FLAG_COLOR, Integer.valueOf(0));
    }

    @Override
    public boolean getIsRideable() {
        return ((Boolean)this.dataManager.get(RIDEABLE)).booleanValue();
    }

    @Override
    public void setRideable(boolean flag) {
        this.dataManager.set(RIDEABLE, Boolean.valueOf(flag));
    }

    public boolean getEggWatching() {
        return ((Boolean)this.dataManager.get(EGG_WATCH)).booleanValue();
    }

    public void setEggWatching(boolean flag) {
        this.dataManager.set(EGG_WATCH, Boolean.valueOf(flag));
    }

    public boolean getHiding() {
        return ((Boolean)this.dataManager.get(IS_HIDING)).booleanValue();
    }

    public void setHiding(boolean flag) {
        this.dataManager.set(IS_HIDING, Boolean.valueOf(flag));
    }

    public int getHelmet() {
        return ((Integer)this.dataManager.get(HELMET_TYPE)).intValue();
    }

    public void setHelmet(int i) {
        this.dataManager.set(HELMET_TYPE, Integer.valueOf(i));
    }

    public int getFlagColor() {
        return ((Integer)this.dataManager.get(FLAG_COLOR)).intValue();
    }

    public void setFlagColor(int i) {
        this.dataManager.set(FLAG_COLOR, Integer.valueOf(i));
    }

    public boolean getIsChested() {
        return ((Boolean)this.dataManager.get(CHESTED)).booleanValue();
    }

    public void setIsChested(boolean flag) {
        this.dataManager.set(CHESTED, Boolean.valueOf(flag));
    }

    @Override
    public boolean isMovementCeased() {
        return (getHiding() || this.isBeingRidden());
    }

    @Override
    public boolean isNotScared() {
        return (getSubType() == 2 && getAttackTarget() != null) || (getSubType() > 2);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
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

        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();

            if (!(entity instanceof LivingEntity) || ((this.isBeingRidden()) && (entity == this.getRidingEntity()))
                    || (entity instanceof PlayerEntity && getIsTamed())) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers()) && getSubType() > 2) {
                setAttackTarget((LivingEntity) entity);
                flapWings();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        dropMyStuff();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn instanceof PlayerEntity && !shouldAttackPlayers()) {
            return false;
        }
        openMouth();
        flapWings();
        return super.attackEntityAsMob(entityIn);
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
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            /**
             * 1 = chick /2 = female /3 = male /4 = albino male /5 = nether ostrich /6 = wyvern
             */
            int j = this.rand.nextInt(100);
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
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
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
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
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
        if (!this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), tType),
//                    new TargetPoint(this.world.dimension.getType(), this.posX, this.posY, this.posZ, 64));
        }
        this.transformType = tType;
        if (!this.isBeingRidden() && this.transformType != 0) {
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
    public void livingTick() {
        super.livingTick();

        if (getIsTamed() && !this.world.isRemote && (this.rand.nextInt(300) == 0) && (getHealth() <= getMaxHealth()) && (this.deathTime == 0)) {
            this.setHealth(getHealth() + 1);
        }

        if (!this.world.isRemote) {
            //ostrich buckle!
            if (getSubType() == 8 && (this.sprintCounter > 0 && this.sprintCounter < 150) && (this.isBeingRidden()) && rand.nextInt(15) == 0) {
                MoCTools.buckleMobs(this, 2D, this.world);
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

            if (getSubType() == 1 && (this.rand.nextInt(200) == 0)) {
                //when is chick and becomes adult, change over to different type
                setEdad(getEdad() + 1);
                if (getEdad() >= 100) {
                    setAdult(true);
                    setType(0);
                    selectType();
                }
            }

            //egg laying
            if (this.canLayEggs && (getSubType() == 2) && !getEggWatching() && --this.eggCounter <= 0 && this.rand.nextInt(5) == 0)// &&
            {
                PlayerEntity entityplayer1 = this.world.getClosestPlayer(this, 12D);
                if (entityplayer1 != null) {
                    double distP = MoCTools.getSqDistanceTo(entityplayer1, this.getPosX(), this.getPosY(), this.getPosZ());
                    if (distP < 10D) {
                        int OstrichEggType = 30;
                        MoCEntityOstrich maleOstrich = getClosestMaleOstrich(this, 8D);
                        if (maleOstrich != null && this.rand.nextInt(100) < MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.ostrichEggDropChance.get()) {
                            MoCEntityEgg entityegg = new MoCEntityEgg(MoCEntities.EGG, this.world, OstrichEggType);
                            entityegg.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                            this.world.addEntity(entityegg);

                            if (!this.getIsTamed()) {
                                setEggWatching(true);
                                if (maleOstrich != null) {
                                    maleOstrich.setEggWatching(true);
                                }
                                openMouth();
                            }

                            //TODO change sound
                            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                            //finds a male and makes it eggWatch as well
                            //MoCEntityOstrich entityOstrich = (MoCEntityOstrich) getClosestSpecificEntity(this, MoCEntityOstrich.class, 12D);
                            this.eggCounter = this.rand.nextInt(2000) + 2000;
                            this.canLayEggs = false;
                        }
                    }
                }
            }

            //egg protection
            if (getEggWatching()) {
                //look for and protect eggs and move close
                MoCEntityEgg myEgg = (MoCEntityEgg) getBoogey(8D);
                if ((myEgg != null) && (MoCTools.getSqDistanceTo(myEgg, this.getPosX(), this.getPosY(), this.getPosZ()) > 4D)) {
                    Path pathentity = this.navigator.getPathToPos(myEgg.getPosition(), 0);
                    this.navigator.setPath(pathentity, 16F);
                }
                if (myEgg == null) //didn't find egg
                {
                    setEggWatching(false);

                    PlayerEntity eggStealer = this.world.getClosestPlayer(this, 10D);
                    if (eggStealer != null) {
                        this.world.getDifficulty();
                        if (!getIsTamed() && this.world.getDifficulty() != Difficulty.PEACEFUL) {
                            setAttackTarget(eggStealer);
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
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(entity, entity.getBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!(entity1 instanceof MoCEntityOstrich) || ((entity1 instanceof MoCEntityOstrich) && ((MoCEntityOstrich) entity1).getSubType() < 3)) {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
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

    @Override
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

        /*if (player.isSneaking() && getIsChested()) {
            // if first time opening a chest, we must initialize it
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("OstrichChest", 9);
            }
            if (!this.world.isRemote) {
                player.displayGUIChest(this.localchest);
            }
            return true;
        }*/

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
    }

    /**
     * Drops a block of the color of the flag if carrying one
     */
    private void dropFlag() {
        if (!this.world.isRemote && getFlagColor() != 0) {
            int color = getFlagColor();
            if (color == 16) {
                color = 0;
            }
            ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(Blocks.WHITE_WOOL, 1));
            entityitem.setPickupDelay(10);
            this.world.addEntity(entityitem);
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
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
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
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
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
    public boolean canSpawn(IWorld worldIn, SpawnReason reason) {
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
        if (!this.world.isRemote) {
            dropArmor();
            MoCTools.dropSaddle(this, this.world);

            if (getIsChested()) {
//                MoCTools.dropInventory(this, this.localchest);
                MoCTools.dropCustomItem(this, this.world, new ItemStack(Blocks.CHEST, 1));
                setIsChested(false);
            }
        }

    }

    /**
     * Drops the helmet
     */
    @Override
    public void dropArmor() {
        if (!this.world.isRemote) {
            final ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.HEAD);
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof ArmorItem) {
                final ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), itemStack.copy());
                entityitem.setPickupDelay(10);
                this.world.addEntity(entityitem);
                }
            setHelmet((byte) 0);
        }
    }

    @Override
    public boolean isFlyer() {
        return this.isBeingRidden() && (getSubType() == 5 || getSubType() == 6);
    }

    @Override
    public boolean onLivingFall(float f, float f1) {
        if (isFlyer()) {
            return false;
        }
        return super.onLivingFall(f, f1);
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
    public CreatureAttribute getCreatureAttribute() {
        if (getSubType() == 7) {
            return CreatureAttribute.UNDEAD;
        }
        return super.getCreatureAttribute();
    }

    @Override
    public int getMaxSpawnedInChunk() {
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
