package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCItems;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityKitty extends MoCEntityTameableAnimal {

    private int kittytimer;
    private int madtimer;
    private boolean foundTree;
    private final int treeCoord[] = {-1, -1, -1};
    private boolean isSwinging;
    private boolean onTree;
    private ItemEntity itemAttackTarget;
    
    private static final DataParameter<Boolean> SITTING = EntityDataManager.defineId(MoCEntityKitty.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HUNGRY = EntityDataManager.defineId(MoCEntityKitty.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EMO = EntityDataManager.defineId(MoCEntityKitty.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> KITTY_STATE = EntityDataManager.defineId(MoCEntityKitty.class, DataSerializers.INT);
    
    public MoCEntityKitty(EntityType<? extends MoCEntityKitty> type, World world) {
        super(type, world);
        setAdult(true);
        setEdad(40);
        setKittyState(1);
        this.kittytimer = 0;
        this.madtimer = this.random.nextInt(5);
        this.foundTree = false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.0D));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.random.nextInt(8) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("pussycata.png");
            case 2:
                return MoCreatures.getTexture("pussycatb.png");
            case 3:
                return MoCreatures.getTexture("pussycatc.png");
            case 4:
                return MoCreatures.getTexture("pussycatd.png");
            case 5:
                return MoCreatures.getTexture("pussycate.png");
            case 6:
                return MoCreatures.getTexture("pussycatf.png");
            case 7:
                return MoCreatures.getTexture("pussycatg.png");
            case 8:
                return MoCreatures.getTexture("pussycath.png");

            default:
                return MoCreatures.getTexture("pussycata.png");
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, Boolean.valueOf(false));
        this.entityData.define(HUNGRY, Boolean.valueOf(false));
        this.entityData.define(EMO, Boolean.valueOf(false));
        this.entityData.define(KITTY_STATE, Integer.valueOf(0));
    }

    public int getKittyState() {
        return ((Integer)this.entityData.get(KITTY_STATE)).intValue();
    }

    @Override
    public boolean getIsSitting() {
        return ((Boolean)this.entityData.get(SITTING)).booleanValue();
    }

    public boolean getIsHungry() {
        return ((Boolean)this.entityData.get(HUNGRY)).booleanValue();
    }

    public boolean getIsEmo() {
        return ((Boolean)this.entityData.get(EMO)).booleanValue();
    }

    public boolean getIsSwinging() {
        return this.isSwinging;
    }

    public boolean getOnTree() {
        return this.onTree;
    }

    public void setKittyState(int i) {
        this.entityData.set(KITTY_STATE, Integer.valueOf(i));
    }

    public void setSitting(boolean flag) {
        this.entityData.set(SITTING, Boolean.valueOf(flag));
    }

    public void setHungry(boolean flag) {
        this.entityData.set(HUNGRY, Boolean.valueOf(flag));
    }

    public void setIsEmo(boolean flag) {
        this.entityData.set(EMO, Boolean.valueOf(flag));
    }

    public void setOnTree(boolean var1) {
        this.onTree = var1;
    }

    public void setSwinging(boolean var1) {
        this.isSwinging = var1;
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if ((getKittyState() != 18) && (getKittyState() != 10)) {
            swingArm();
        }
        if (((getKittyState() == 13) && (entityIn instanceof PlayerEntity)) || ((getKittyState() == 8) && (entityIn instanceof ItemEntity))
                || ((getKittyState() == 18) && (entityIn instanceof MoCEntityKitty)) || (getKittyState() == 10)) {
            return false;
        }
        return super.doHurtTarget(entityIn);
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if (entity != this && entity instanceof LivingEntity) {
                LivingEntity entityliving = (LivingEntity) entity;
                if (getKittyState() == 10) {
                    List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(16D, 6D, 16D));
                    for (int j = 0; j < list.size(); j++) {
                        Entity entity1 = list.get(j);
                        if ((entity1 instanceof MoCEntityKitty) && (((MoCEntityKitty) entity1).getKittyState() == 21)) {
                            ((MoCEntityKitty) entity1).setTarget(entityliving);
                            return true;
                        }
                    }

                    return true;
                }
                if (entityliving instanceof PlayerEntity && super.shouldAttackPlayers()) {
                    if (getKittyState() < 2) {
                        setTarget(entityliving);
                        setKittyState(-1);
                    }
                    if ((getKittyState() == 19) || (getKittyState() == 20) || (getKittyState() == 21)) {
                        setTarget(entityliving);
                        setSitting(false);
                        return true;
                    }
                    if ((getKittyState() > 1) && (getKittyState() != 10) && (getKittyState() != 19) && (getKittyState() != 20)
                            && (getKittyState() != 21)) {
                        setKittyState(13);
                        setSitting(false);
                    }
                    return true;
                }
                setTarget(entityliving);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        if (MoCConfig.COMMON_CONFIG.GLOBAL.forceDespawns.get()) {
            return getKittyState() < 3;
        } else {
            return false;
        }
    }

    private void changeKittyState(int i) {
        setKittyState(i);
        setSitting(false);
        this.kittytimer = 0;
        setOnTree(false);
        this.foundTree = false;
        setTarget(null);
        this.itemAttackTarget = null;
    }

    public boolean climbingTree() {
        return (getKittyState() == 16) && onClimbable();
    }

    @Override
    protected Entity findPlayerToAttack() {
        if ((this.level.getDifficulty().getId() > 0) && (getKittyState() != 8) && (getKittyState() != 10) && (getKittyState() != 15)
                && (getKittyState() != 18) && (getKittyState() != 19) && !isMovementCeased() && getIsHungry()) {
            LivingEntity entityliving = getClosestTarget(this, 10D);
            return entityliving;
        } else {
            return null;
        }
    }

    //TODO
    //change this so MoCAnimal getBoogey is used instead to decrease duplication of code
    public LivingEntity getBoogey(double d, boolean flag) {
        LivingEntity entityliving = null;
        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(d, 4D, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if ((entity instanceof LivingEntity) && !(entity instanceof MoCEntityDeer) && !(entity instanceof MoCEntityHorse)
                    && ((entity.getBbWidth() >= 0.5D) || (entity.getBbHeight() >= 0.5D)) && (flag || !(entity instanceof PlayerEntity))) {
                entityliving = (LivingEntity) entity;
            }
        }

        return entityliving;
    }

    //TODO use MoCAnimal instead
    @Override
    public LivingEntity getClosestTarget(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!(entity1 instanceof LivingEntity) || (entity1 instanceof MoCEntityKitty) || (entity1 instanceof PlayerEntity)
                    || (entity1 instanceof MobEntity) || (entity1 instanceof MoCEntityKittyBed) || (entity1 instanceof MoCEntityLitterBox)
                    || ((entity1.getBbWidth() > 0.5D) && (entity1.getBbHeight() > 0.5D)) || ((entity instanceof IMoCEntity) && !MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.enableHunters.get())) {
                continue;
            }
            double d2 = entity1.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) entity1).canSee(entity)) {
                d1 = d2;
                entityliving = (LivingEntity) entity1;
            }
        }

        return entityliving;
    }

    public ResourceLocation getEmoteIcon() {
        switch (getKittyState()) {
            case -1:
                return MoCreatures.getTexture("emoticon2.png");

            case 3: // '\003'
                return MoCreatures.getTexture("emoticon3.png");

            case 4: // '\004'
                return MoCreatures.getTexture("emoticon4.png");

            case 5: // '\005'
                return MoCreatures.getTexture("emoticon5.png");

            case 7: // '\007'
                return MoCreatures.getTexture("emoticon7.png");

            case 8: // '\b'
                return MoCreatures.getTexture("emoticon8.png");

            case 9: // '\t'
                return MoCreatures.getTexture("emoticon9.png");

            case 10: // '\n'
                return MoCreatures.getTexture("emoticon10.png");

            case 11: // '\013'
                return MoCreatures.getTexture("emoticon11.png");

            case 12: // '\f'
                return MoCreatures.getTexture("emoticon12.png");

            case 13: // '\r'
                return MoCreatures.getTexture("emoticon13.png");

            case 16: // '\020'
                return MoCreatures.getTexture("emoticon16.png");

            case 17: // '\021'
                return MoCreatures.getTexture("emoticon17.png");

            case 18: // '\022'
                return MoCreatures.getTexture("emoticon9.png");

            case 19: // '\023'
                return MoCreatures.getTexture("emoticon19.png");

            case 20: // '\024'
                return MoCreatures.getTexture("emoticon19.png");

            case 21: // '\025'
                return MoCreatures.getTexture("emoticon10.png");

            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 6: // '\006'
            case 14: // '\016'
            case 15: // '\017'
            default:
                return MoCreatures.getTexture("emoticon1.png");
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        if (getKittyState() == 10) {
            return MoCSoundEvents.ENTITY_KITTY_DEATH_BABY;
        } else {
            return MoCSoundEvents.ENTITY_KITTY_DEATH;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (getKittyState() == 10) {
            return MoCSoundEvents.ENTITY_KITTY_HURT_BABY;
        } else {
            return MoCSoundEvents.ENTITY_KITTY_HURT;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (getKittyState() == 4) {
            if (this.getVehicle() != null) {
                MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) this.getVehicle();
                if ((entitykittybed != null) && !entitykittybed.getHasMilk()) {
                    return MoCSoundEvents.ENTITY_KITTY_DRINKING;
                }
                if ((entitykittybed != null) && !entitykittybed.getHasFood()) {
                    return MoCSoundEvents.ENTITY_KITTY_EATING;
                }
            }
            return null;
        }
        if (getKittyState() == 6) {
            return MoCSoundEvents.ENTITY_KITTY_LITTER;
        }
        if (getKittyState() == 3) {
            return MoCSoundEvents.ENTITY_KITTY_HUNGRY;
        }
        if (getKittyState() == 10) {
            return MoCSoundEvents.ENTITY_KITTY_AMBIENT_BABY;
        }
        if (getKittyState() == 13) {
            return MoCSoundEvents.ENTITY_KITTY_ANGRY;
        }
        if (getKittyState() == 17) {
            return MoCSoundEvents.ENTITY_KITTY_TRAPPED;
        }
        if ((getKittyState() == 18) || (getKittyState() == 12)) {
            return MoCSoundEvents.ENTITY_KITTY_PURR;
        } else {
            return MoCSoundEvents.ENTITY_KITTY_AMBIENT;
        }
    }

    public LivingEntity getKittyStuff(Entity entity, double d, boolean flag) {
        double d1 = -1D;
        Object obj = null;
        List<Entity> list = this.level.getEntities(entity, getBoundingBox().expandTowards(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (flag) {
                if (!(entity1 instanceof MoCEntityLitterBox)) {
                    continue;
                }
                MoCEntityLitterBox entitylitterbox = (MoCEntityLitterBox) entity1;
                if (entitylitterbox.getUsedLitter()) {
                    continue;
                }
                double d2 = entity1.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
                if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1.0D) || (d2 < d1)) && entitylitterbox.canSee(entity)) {
                    d1 = d2;
                    obj = entitylitterbox;
                }
                continue;
            }
            if (!(entity1 instanceof MoCEntityKittyBed)) {
                continue;
            }
            MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) entity1;
            double d3 = entity1.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
            if (((d < 0.0D) || (d3 < (d * d))) && ((d1 == -1.0D) || (d3 < d1)) && entitykittybed.canSee(entity)) {
                d1 = d3;
                obj = entitykittybed;
            }
        }

        return ((LivingEntity) (obj));
    }

    // Mojang changed offsets in 1.12 so this needs to be reviewed
    /*@Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof EntityPlayer && this.getRidingEntity() == MoCreatures.proxy.getPlayer() && this.world.isRemote) {
            if (getKittyState() == 10) {
                return (super.getYOffset() - 0.95F);
            }
            if (upsideDown()) {
                return (super.getYOffset() - 1.55F);
            }
            if (onMaBack()) {
                return (super.getYOffset() - 1.35F);
            }
        }

        if ((this.getRidingEntity() instanceof EntityPlayer) && this.world.isRemote) {
            if (getKittyState() == 10) {
                return (super.getYOffset() + 0.45F);
            }
            if (upsideDown()) {
                return (super.getYOffset() - 0.1F);
            }
            if (onMaBack()) {
                return (super.getYOffset() + 0.1F);
            }
        }

        return super.getYOffset();
    }*/

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if ((getKittyState() == 2) && !stack.isEmpty() && (stack.getItem() == MoCItems.MEDALLION)) {
            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }
            if (getIsTamed()) {
                stack.shrink(1);
                if (stack.isEmpty()) {
                    player.setHeldItem(hand, ItemStack.EMPTY);
                }
                changeKittyState(3);
                this.setHealth(getMaxHealth());
                return true;
            }
            return false;
        }
        if ((getKittyState() == 7) && !stack.isEmpty()    //TODO: Does this work? ItemTags.FISHES
                && ((stack.getItem() == Items.CAKE) || (stack.getItem().getTags().contains(ItemTags.FISHES)))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_KITTY_EATING);
            this.setHealth(getMaxHealth());
            changeKittyState(9);
            return true;
        }
        if ((getKittyState() == 11) && !stack.isEmpty() && (stack.getItem() == MoCItems.WOOLBALL)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            setKittyState(8);
            if (!this.world.isRemote) {
                ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY() + 1.0D, this.getPosZ(), new ItemStack(MoCItems.WOOLBALL, 1));
                entityitem.setPickupDelay(30);
                entityitem.setNoDespawn();
                this.world.addEntity(entityitem);
                entityitem.getMotion().add(((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.3F), (this.world.rand.nextFloat() * 0.05F), ((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.3F));
                this.itemAttackTarget = entityitem;
            }
            return true;
        }
        if ((getKittyState() == 13) && !stack.isEmpty() && ((stack.getItem().getTags().contains(ItemTags.FISHES)))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_KITTY_EATING);
            this.setHealth(getMaxHealth());
            changeKittyState(7);
            return true;
        }
        if (!stack.isEmpty() && (getKittyState() > 2) && ((stack.getItem() == MoCItems.MEDALLION) || (stack.getItem() == Items.BOOK))) {
            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            return true;
        }
        if (!stack.isEmpty() && (getKittyState() > 2) && pickable() && (stack.getItem() == Items.LEAD)) {
            if (this.startRiding(player)) {
                changeKittyState(14);
            }
            return true;
        }
        if (!stack.isEmpty() && (getKittyState() > 2) && whipeable() && (stack.getItem() == MoCItems.WHIP)) {
            setSitting(!getIsSitting());
            return true;
        }
        
        if (stack.isEmpty() && (getKittyState() > 2) && pickable()) {
            if (this.startRiding(player)) {
                changeKittyState(15);
            }
            return true;
        }
        if (stack.isEmpty() && (getKittyState() == 15)) {
            changeKittyState(7);
            return true;
        }
        if ((getKittyState() == 14) && this.getRidingEntity() != null) {
            changeKittyState(7);
            return true;
        }

        return super.processInteract(player, hand);
    }*/

    @Override
    public boolean isMovementCeased() {
        return getIsSitting() || (getKittyState() == 6) || ((getKittyState() == 16) && getOnTree()) || (getKittyState() == 12)
                || (getKittyState() == 17) || (getKittyState() == 14) || (getKittyState() == 20) || (getKittyState() == 23);
    }

    @Override
    public boolean onClimbable() {
        if (getKittyState() == 16) {
            return this.horizontalCollision && getOnTree();
        } else {
            return super.onClimbable();
        }
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide) {
            if (!getIsAdult() && (getKittyState() != 10)) {
                setKittyState(10);
            }
            if (getKittyState() != 12) {
                super.aiStep();
            }
            if (this.random.nextInt(200) == 0) {
                setIsEmo(!getIsEmo());
            }
            if (!getIsAdult() && (this.random.nextInt(200) == 0)) {
                setEdad(getEdad() + 1);
                if (getEdad() >= 100) {
                    setAdult(true);
                }
            }
            if (!getIsHungry() && !getIsSitting() && (this.random.nextInt(100) == 0)) {
                setHungry(true);
            }

            if (this.isPassenger()) MoCTools.dismountSneakingPlayer(this);
            
            label0: switch (getKittyState()) {
                case -1:
                    break;
                case 23: // '\027'
                    break;

                case 1: // '\001'
                    if (this.random.nextInt(10) == 0) {
                        LivingEntity entityliving = getBoogey(6D, true);
                        if (entityliving != null) {
                            MoCTools.runLikeHell(this, entityliving);
                        }
                        break;
                    }
                    if (!getIsHungry() || (this.random.nextInt(10) != 0)) {
                        break;
                    }
                    ItemEntity entityitem = getClosestItem(this, 10D, Items.COOKED_COD, Items.COOKED_COD);
                    if (entityitem == null) {
                        break;
                    }
                    float f = entityitem.distanceTo(this);
                    if (f > 2.0F) {
                        getMyOwnPath(entityitem, f);
                    }
                    if ((f < 2.0F) && (entityitem != null) && (this.deathTime == 0)) {
                        entityitem.remove();
                        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_KITTY_EATING);
                        setHungry(false);
                        setKittyState(2);
                    }
                    break;

                case 2: // '\002'
                    LivingEntity entityliving1 = getBoogey(6D, false);
                    if (entityliving1 != null) {
                        MoCTools.runLikeHell(this, entityliving1);
                    }
                    break;

                case 3: // '\003'
                    this.kittytimer++;
                    if (this.kittytimer > 500) {
                        if (this.random.nextInt(200) == 0) {
                            changeKittyState(13);
                            break;
                        }
                        if (this.random.nextInt(500) == 0) {
                            changeKittyState(7);
                            break;
                        }
                    }
                    if (this.random.nextInt(20) != 0) {
                        break;
                    }
                    MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) getKittyStuff(this, 18D, false);
                    if ((entitykittybed == null) || (entitykittybed.isVehicle())
                            || (!entitykittybed.getHasMilk() && !entitykittybed.getHasFood())) {
                        break;
                    }
                    float f5 = entitykittybed.distanceTo(this);
                    if (f5 > 2.0F) {
                        getMyOwnPath(entitykittybed, f5);
                    }
                    if (f5 < 2.0F) {
                        if (this.startRiding(entitykittybed)) {
                            changeKittyState(4);
                            setSitting(true);
                        }
                    }
                    break;

                case 4: // '\004'
                    if (this.getVehicle() != null) {
                        MoCEntityKittyBed entitykittybed1 = (MoCEntityKittyBed) this.getVehicle();
                        if ((entitykittybed1 != null) && !entitykittybed1.getHasMilk() && !entitykittybed1.getHasFood()) {
                            this.setHealth(getMaxHealth());
                            changeKittyState(5);
                        }
                    } else {
                        this.setHealth(getMaxHealth());
                        changeKittyState(5);
                    }
                    if (this.random.nextInt(2500) == 0) {
                        this.setHealth(getMaxHealth());
                        changeKittyState(7);
                    }
                    break;

                case 5: // '\005'
                    this.kittytimer++;
                    if ((this.kittytimer > 2000) && (this.random.nextInt(1000) == 0)) {
                        changeKittyState(13);
                        break;
                    }
                    if (this.random.nextInt(20) != 0) {
                        break;
                    }
                    MoCEntityLitterBox entitylitterbox = (MoCEntityLitterBox) getKittyStuff(this, 18D, true);
                    if ((entitylitterbox == null) || (entitylitterbox.isVehicle()) || entitylitterbox.getUsedLitter()) {
                        break;
                    }
                    float f6 = entitylitterbox.distanceTo(this);
                    if (f6 > 2.0F) {
                        getMyOwnPath(entitylitterbox, f6);
                    }
                    if (f6 < 2.0F) {
                        if (this.startRiding(entitylitterbox)) {
                            changeKittyState(6);
                        }
                    }
                    break;

                case 6: // '\006'
                    this.kittytimer++;
                    if (this.kittytimer <= 300) {
                        break;
                    }
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_KITTY_LITTER);
                    MoCEntityLitterBox entitylitterbox1 = (MoCEntityLitterBox) this.getVehicle();
                    if (entitylitterbox1 != null) {
                        entitylitterbox1.setUsedLitter(true);
                        entitylitterbox1.littertime = 0;
                    }
                    changeKittyState(7);
                    break;

                case 7: // '\007'
                    if (getIsSitting()) {
                        break;
                    }
                    if (this.random.nextInt(20) == 0) {
                        PlayerEntity entityplayer = this.level.getNearestPlayer(this, 12D);
                        if (entityplayer != null) {
                            ItemStack stack = entityplayer.inventory.getSelected();
                            if (!stack.isEmpty() && (stack.getItem() == MoCItems.WOOLBALL)) {
                                changeKittyState(11);
                                break;
                            }
                        }
                    }
                    if (this.wasTouchingWater && (this.random.nextInt(500) == 0)) {
                        changeKittyState(13);
                        break;
                    }
                    if ((this.random.nextInt(500) == 0) && !this.level.isDay()) {
                        changeKittyState(12);
                        break;
                    }
                    if (this.random.nextInt(2000) == 0) {
                        changeKittyState(3);
                        break;
                    }
                    if (this.random.nextInt(4000) == 0) {
                        changeKittyState(16);
                    }
                    break;

                case 8: // '\b'
                    if (this.wasTouchingWater && this.random.nextInt(200) == 0) {
                        changeKittyState(13);
                        break;
                    }

                    if (this.itemAttackTarget != null && this.itemAttackTarget instanceof ItemEntity) {
                        if (getTarget() != null) {
                            float f1 = distanceTo(getTarget());
                            if (f1 < 1.5F) {
                                swingArm();
                                if (this.random.nextInt(10) == 0) {
                                    //float force = 0.3F;
                                    //if (type == 10) force = 0.2F;
                                    MoCTools.bigsmack(this, this.itemAttackTarget, 0.3F);
                                    //kittySmack(this, entityLivingToAttack);
                                }
                            }
                        }
                    }
                    if (getTarget() == null || this.random.nextInt(1000) == 0) {
                        changeKittyState(7);
                    }
                    break;

                case 9: // looking for mate
                    this.kittytimer++;
                    if (this.random.nextInt(50) == 0) {
                        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(16D, 6D, 16D));
                        int j = 0;
                        do {
                            if (j >= list.size()) {
                                break;
                            }
                            Entity entity = list.get(j);
                            if (entity instanceof MoCEntityKitty && ((MoCEntityKitty) entity).getKittyState() == 9) {
                                changeKittyState(18);
                                setTarget((LivingEntity) entity);
                                ((MoCEntityKitty) entity).changeKittyState(18);
                                ((MoCEntityKitty) entity).setTarget(this);
                                break;
                            }
                            j++;
                        } while (true);
                    }
                    if (this.kittytimer > 2000) {
                        changeKittyState(7);
                    }
                    break;

                case 10: // '\n'
                    if (getIsAdult()) {
                        changeKittyState(7);
                        break;
                    }
                    if (this.random.nextInt(50) == 0) {
                        List<Entity> list1 = this.level.getEntities(this, getBoundingBox().expandTowards(16D, 6D, 16D));
                        for (int k = 0; k < list1.size(); k++) {
                            Entity entity1 = list1.get(k);
                            if (!(entity1 instanceof MoCEntityKitty) || (((MoCEntityKitty) entity1).getKittyState() != 21)) {
                                continue;
                            }
                            float f9 = distanceTo(entity1);
                            if (f9 > 12F) {
                                setTarget((LivingEntity) entity1);
                            }
                        }

                    }
                    if ((this.itemAttackTarget == null || getTarget() == null) && (this.random.nextInt(100) == 0)) {
                        int i = this.random.nextInt(10);
                        if (i < 7) {
                            this.itemAttackTarget = getClosestItem(this, 10D, null, null);
                        } else {
                            this.setTarget(this.level.getNearestPlayer(this, 18D));
                        }
                    }
                    if ((this.getTarget() != null || this.itemAttackTarget != null) && (this.random.nextInt(400) == 0)) {
                        setTarget(null);
                        this.itemAttackTarget = null;
                    }
                    if ((this.itemAttackTarget != null) && (this.itemAttackTarget instanceof ItemEntity)) {
                        float f2 = distanceTo(this.itemAttackTarget);
                        if (f2 < 1.5F) {
                            swingArm();
                            if (this.random.nextInt(10) == 0) {
                                MoCTools.bigsmack(this, this.itemAttackTarget, 0.2F);
                                //kittySmack(this, entityLivingToAttack);
                            }
                        }
                    }
                    if (getTarget() != null && (getTarget() instanceof MoCEntityKitty) && (this.random.nextInt(20) == 0)) {
                        float f3 = distanceTo(getTarget());
                        if (f3 < 2.0F) {
                            swingArm();
                            this.getNavigation().stop();
                        }
                    }
                    if ((getTarget() == null) || !(getTarget() instanceof PlayerEntity)) {
                        break;
                    }
                    float f4 = distanceTo(getTarget());
                    if ((f4 < 2.0F) && (this.random.nextInt(20) == 0)) {
                        swingArm();
                    }
                    break;

                case 11: // '\013'
                    PlayerEntity entityplayer1 = this.level.getNearestPlayer(this, 18D);
                    if ((entityplayer1 == null) || (this.random.nextInt(10) != 0)) {
                        break;
                    }
                    ItemStack itemstack1 = entityplayer1.inventory.getSelected();
                    if ((itemstack1 == null) || ((itemstack1 != null) && (itemstack1.getItem() != MoCItems.WOOLBALL))) {
                        changeKittyState(7);
                        break;
                    }
                    float f8 = entityplayer1.distanceTo(this);
                    if (f8 > 5F) {
                        getPathOrWalkableBlock(entityplayer1, f8);
                    }
                    break;

                case 12: // '\f'
                    this.kittytimer++;
                    if (this.level.isDay() || ((this.kittytimer > 500) && (this.random.nextInt(500) == 0))) {
                        changeKittyState(7);
                        break;
                    }
                    setSitting(true);
                    if ((this.random.nextInt(80) == 0) || !this.onGround) {
                        super.aiStep();
                    }
                    break;

                case 13: // '\r'
                    setHungry(false);
                    setTarget(this.level.getNearestPlayer(this, 18D));
                    if (getTarget() != null) {
                        float f7 = distanceTo(getTarget());
                        if (f7 < 1.5F) {
                            swingArm();
                            if (this.random.nextInt(20) == 0) {
                                this.madtimer--;
                                getTarget().hurt(DamageSource.mobAttack(this), 1);
                                if (this.madtimer < 1) {
                                    changeKittyState(7);
                                    this.madtimer = this.random.nextInt(5);
                                }
                            }
                        }
                        if (this.random.nextInt(500) == 0) {
                            changeKittyState(7);
                        }
                    } else {
                        changeKittyState(7);
                    }
                    break;

                case 14: // held by rope
                    if (this.onGround) {
                        changeKittyState(13);
                        break;
                    }
                    if (this.random.nextInt(50) == 0) {
                        swingArm();
                    }
                    if (this.getVehicle() == null) {
                        break;
                    }
                    this.yRot = this.getVehicle().yRot + 90F;
                    PlayerEntity entityplayer2 = (PlayerEntity) this.getVehicle();
                    if (entityplayer2 == null) {
                        changeKittyState(13);
                        break;
                    }
                    ItemStack itemstack2 = entityplayer2.inventory.getSelected();
                    if (itemstack2 == null || ((itemstack2 != null) && (itemstack2.getItem() != Items.LEAD))) {
                        changeKittyState(13);
                    }
                    break;

                case 15: // '\017'
                    if (this.onGround) {
                        changeKittyState(7);
                    }
                    if (this.getVehicle() != null) {
                        this.yRot = this.getVehicle().yRot + 90F;
                    }
                    break;

                case 16: // '\020'
                    this.kittytimer++;
                    if ((this.kittytimer > 500) && !getOnTree()) {
                        changeKittyState(7);
                    }
                    if (!getOnTree()) {
                        if (!this.foundTree && (this.random.nextInt(50) == 0)) {
                            int ai[] = MoCTools.ReturnNearestMaterialCoord(this, Material.WOOD, Double.valueOf(18D), 4D);
                            if (ai[0] != -1) {
                                int i1 = 0;
                                do {
                                    if (i1 >= 20) {
                                        break;
                                    }
                                    BlockState blockstate = this.level.getBlockState(new BlockPos(ai[0], ai[1] + i1, ai[2]));
                                    if ((blockstate.getMaterial() == Material.LEAVES)) {
                                        this.foundTree = true;
                                        this.treeCoord[0] = ai[0];
                                        this.treeCoord[1] = ai[1];
                                        this.treeCoord[2] = ai[2];
                                        break;
                                    }
                                    i1++;
                                } while (true);
                            }
                        }
                        if (!this.foundTree || (this.random.nextInt(10) != 0)) {
                            break;
                        }
                        Path pathentity = this.navigation.createPath(this.treeCoord[0], this.treeCoord[1], this.treeCoord[2], 0);

                        if (pathentity != null) {
                            this.navigation.moveTo(pathentity, 24F);
                        }
                        Double double1 = Double.valueOf(distanceToSqr(this.treeCoord[0], this.treeCoord[1], this.treeCoord[2]));
                        if (double1.doubleValue() < 7D) {
                            setOnTree(true);
                        }
                        break;
                    }
                    if (!getOnTree()) {
                        break;
                    }
                    int l = this.treeCoord[0];
                    int j1 = this.treeCoord[1];
                    int l1 = this.treeCoord[2];
                    faceItem(l, j1, l1, 30F);
                    if ((j1 - MathHelper.floor(this.getY())) > 2) {
                        this.getDeltaMovement().add(0, 0.029999999999999999D, 0);
                    }

                    if (this.getX() < l) {
                        this.getDeltaMovement().add(0.01D, 0, 0);
                    } else {
                        this.getDeltaMovement().subtract(0.01D, 0, 0);
                    }
                    if (this.getZ() < l1) {
                        this.getDeltaMovement().add(0, 0, 0.01D);
                    } else {
                        this.getDeltaMovement().subtract(0, 0, 0.01D);
                    }
                    if (this.onGround || !this.horizontalCollision || !this.verticalCollision) {
                        break;
                    }
                    int i4 = 0;
                    do {
                        if (i4 >= 30) {
                            break label0;
                        }
                        BlockPos pos = new BlockPos(this.treeCoord[0], this.treeCoord[1] + i4, this.treeCoord[2]);
                        Block block = this.level.getBlockState(pos).getBlock();
                        if (block == Blocks.AIR) {
                            moveTo(this.treeCoord[0], this.treeCoord[1] + i4, this.treeCoord[2], this.yRot, this.xRot);
                            changeKittyState(17);
                            this.treeCoord[0] = -1;
                            this.treeCoord[1] = -1;
                            this.treeCoord[2] = -1;
                            break label0;
                        }
                        i4++;
                    } while (true);

                case 17: // '\021'
                    PlayerEntity entityplayer3 = this.level.getNearestPlayer(this, 2D);
                    if (entityplayer3 != null) {
                        changeKittyState(7);
                    }
                    break;

                case 18: // '\022'
                    if ((getTarget() == null) || !(getTarget() instanceof MoCEntityKitty)) {
                        changeKittyState(9);
                        break;
                    }
                    MoCEntityKitty entitykitty = (MoCEntityKitty) getTarget();
                    if ((entitykitty != null) && (entitykitty.getKittyState() == 18)) {
                        if (this.random.nextInt(50) == 0) {
                            swingArm();
                        }
                        float f10 = distanceTo(entitykitty);
                        if (f10 < 5F) {
                            this.kittytimer++;
                        }
                        if ((this.kittytimer > 500) && (this.random.nextInt(50) == 0)) {
                            ((MoCEntityKitty) getTarget()).changeKittyState(7);
                            changeKittyState(19);
                        }
                    } else {
                        changeKittyState(9);
                    }
                    break;

                case 19: // '\023'
                    if (this.random.nextInt(20) != 0) {
                        break;
                    }
                    MoCEntityKittyBed entitykittybed2 = (MoCEntityKittyBed) getKittyStuff(this, 18D, false);
                    if ((entitykittybed2 == null) || (entitykittybed2.isVehicle())) {
                        break;
                    }
                    float f11 = entitykittybed2.distanceTo(this);
                    if (f11 > 2.0F) {
                        getMyOwnPath(entitykittybed2, f11);
                    }
                    if (f11 < 2.0F) {
                        if (this.startRiding(entitykittybed2)) {
                            changeKittyState(20);
                        }
                    }
                    break;

                case 20: // '\024'
                    if (this.getVehicle() == null) {
                        changeKittyState(19);
                        break;
                    }
                    this.yRot = 180F;
                    this.kittytimer++;
                    if (this.kittytimer <= 1000) {
                        break;
                    }
                    int i2 = this.random.nextInt(3) + 1;
                    for (int l2 = 0; l2 < i2; l2++) {
                        MoCEntityKitty entitykitty1 = new MoCEntityKitty(MoCEntities.KITTY, this.level);
                        int babytype = this.getSubType();
                        if (this.random.nextInt(2) == 0) {
                            babytype = (this.random.nextInt(8) + 1);
                        }
                        entitykitty1.setType(babytype);
                        entitykitty1.setPos(this.getX(), this.getY(), this.getZ());
                        this.level.addFreshEntity(entitykitty1);
                        MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
                        entitykitty1.setAdult(false);
                        entitykitty1.changeKittyState(10);
                        // attackEntityFrom(DamageSource.generic, 1); blood - workaround to fix
                        // infinite births
                    }

                    changeKittyState(21);
                    break;

                case 21: // '\025'
                    this.kittytimer++;
                    if (this.kittytimer > 2000) {
                        List<Entity> list2 = this.level.getEntities(this, getBoundingBox().expandTowards(24D, 8D, 24D));
                        int i3 = 0;
                        for (int l3 = 0; l3 < list2.size(); l3++) {
                            Entity entity2 = list2.get(l3);
                            if ((entity2 instanceof MoCEntityKitty) && (((MoCEntityKitty) entity2).getKittyState() == 10)) {
                                i3++;
                            }
                        }

                        if (i3 < 1) {
                            changeKittyState(7);
                            break;
                        }
                        this.kittytimer = 1000;
                    }
                    if ((getTarget() != null) && (getTarget() instanceof PlayerEntity) && (this.random.nextInt(300) == 0)) {
                        setTarget(null);
                    }
                    break;

                case 0:
                    changeKittyState(1);
                    break;
                // case 22: // '\026'
                default:
                    changeKittyState(7);
                    break;
            }
        } else {
            super.aiStep();
        }
    }

    public boolean onMaBack() {
        return getKittyState() == 15;
    }

    @Override
    public void tick() {
        super.tick();
        if (getIsSwinging()) {
            this.attackAnim += 0.2F;
            if (this.attackAnim > 2.0F) {
                setSwinging(false);
                this.attackAnim = 0.0F;
            }
        }
    }

    private boolean pickable() {
        return (getKittyState() != 13) && (getKittyState() != 14) && (getKittyState() != 15) && (getKittyState() != 19) && (getKittyState() != 20)
                && (getKittyState() != 21);
    }

    @Override
    public boolean renderName() {
        return (getKittyState() != 14) && (getKittyState() != 15) && (getKittyState() > 1) && super.renderName();
    }

    @Override
    public void remove() {
        if (!this.level.isClientSide && (getKittyState() > 2) && (getHealth() > 0)) {
            return;
        } else {
            super.remove();
            return;
        }
    }

    public void swingArm() {
        //to synchronize, uses the packet handler to invoke the same method in the clients
        if (!this.level.isClientSide) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
//                    new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }

        if (!getIsSwinging()) {
            setSwinging(true);
            this.attackAnim = 0.0F;
        }
    }

    @Override
    public void performAnimation(int i) {
        swingArm();
    }

    public boolean upsideDown() {
        return getKittyState() == 14;
    }

    public boolean whipeable() {
        return getKittyState() != 13;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        setSitting(nbttagcompound.getBoolean("Sitting"));
        setKittyState(nbttagcompound.getInt("KittyState"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putBoolean("Sitting", getIsSitting());
        nbttagcompound.putInt("KittyState", getKittyState());
    }

    /*@Override
    public boolean updateMount() {
        return true;
    }*/

    /*@Override
    public boolean forceUpdates() {
        return true;
    }*/

    //drops medallion on death
    @Override
    public void die(DamageSource damagesource) {
        if (!this.level.isClientSide) {
            if (getIsTamed()) {
                MoCTools.dropCustomItem(this, this.level, new ItemStack(MoCItems.MEDALLION, 1));
            }
        }
        super.die(damagesource);
    }

    @Override
    public boolean swimmerEntity() {
        return true;
    }

    @Override
    public int nameYOffset() {
        if (this.getIsSitting())
            return -30;
        return -40;
    }
}
