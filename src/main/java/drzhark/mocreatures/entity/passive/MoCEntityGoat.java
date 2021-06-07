package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.FollowAdultGoal;
import drzhark.mocreatures.entity.ai.MoCPanicGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MoCEntityGoat extends MoCEntityTameableAnimal {

    private boolean hungry;
    private boolean swingLeg;
    private boolean swingEar;
    private boolean swingTail;
    private boolean bleat;
    private boolean eating;
    private int bleatcount;
    private int attacking;
    public int movecount;
    private int chargecount;
    private int tailcount; // 90 to -45
    private int earcount; // 20 to 40 default = 30
    private int eatcount;
    private static final DataParameter<Boolean> IS_CHARGING = EntityDataManager.defineId(MoCEntityGoat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_UPSET = EntityDataManager.defineId(MoCEntityGoat.class, DataSerializers.BOOLEAN);
    
    public MoCEntityGoat(EntityType<? extends MoCEntityGoat> type, World world) {
        super(type, world);
        setEdad(70);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MoCPanicGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new FollowAdultGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new MoCAlternateWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_CHARGING, false);
        this.entityData.define(IS_UPSET, false);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public boolean getUpset() {
        return this.entityData.get(IS_UPSET);
    }

    public boolean getCharging() {
        return this.entityData.get(IS_CHARGING);
    }

    public void setUpset(boolean flag) {
        this.entityData.set(IS_UPSET, flag);
    }

    public void setCharging(boolean flag) {
        this.entityData.set(IS_CHARGING, flag);
    }

    @Override
    public void selectType() {
        /*
         * type 1 = baby type 2 = female type 3 = female 2 type 4 = female 3
         * type 5 = male 1 type 6 = male 2 type 7 = male 3
         */
        if (getSubType() == 0) {
            int i = this.random.nextInt(100);
            if (i <= 15) {
                setType(1);
                setEdad(50);
            } else if (i <= 30) {
                setType(2);
                setEdad(70);
            } else if (i <= 45) {
                setType(3);
                setEdad(70);
            } else if (i <= 60) {
                setType(4);
                setEdad(70);
            } else if (i <= 75) {
                setType(5);
                setEdad(90);
            } else if (i <= 90) {
                setType(6);
                setEdad(90);
            } else {
                setType(7);
                setEdad(90);
            }
        }

    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("goat1.png");
            case 2:
                return MoCreatures.getTexture("goat2.png");
            case 3:
                return MoCreatures.getTexture("goat3.png");
            case 4:
                return MoCreatures.getTexture("goat4.png");
            case 5:
                return MoCreatures.getTexture("goat5.png");
            case 6:
                return MoCreatures.getTexture("goat6.png");
            case 7:
                return MoCreatures.getTexture("goat1.png");

            default:
                return MoCreatures.getTexture("goat1.png");
        }
    }

    public void calm() {
        setTarget(null);
        setUpset(false);
        setCharging(false);
        this.attacking = 0;
        this.chargecount = 0;
    }

    @Override
    protected void jumpFromGround() {
        if (getSubType() == 1) {
            this.setDeltaMovement(this.getDeltaMovement().x, 0.41D, this.getDeltaMovement().z);
        } else if (getSubType() < 5) {
            this.setDeltaMovement(this.getDeltaMovement().x, 0.45D, this.getDeltaMovement().z);
        } else {
            this.setDeltaMovement(this.getDeltaMovement().x, 0.5D, this.getDeltaMovement().z);
        }

        if (hasEffect(Effects.JUMP)) {
            this.getDeltaMovement().add(0, (getEffect(Effects.JUMP).getAmplifier() + 1) * 0.1F, 0);
        }
        if (isSprinting()) {
            float f = this.yRot * 0.01745329F;
            this.getDeltaMovement().add(-(MathHelper.sin(f) * 0.2F), 0, (MathHelper.cos(f) * 0.2F));
        }
        this.hasImpulse = true;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level.isClientSide) {
            if (this.random.nextInt(100) == 0) {
                setSwingEar(true);
            }

            if (this.random.nextInt(80) == 0) {
                setSwingTail(true);
            }

            if (this.random.nextInt(50) == 0) {
                setEating(true);
            }
        }
        if (getBleating()) {
            this.bleatcount++;
            if (this.bleatcount > 15) {
                this.bleatcount = 0;
                setBleating(false);
            }

        }

        if ((this.hungry) && (this.random.nextInt(20) == 0)) {
            this.hungry = false;
        }

        if (!this.level.isClientSide && (getEdad() < 90 || getSubType() > 4 && getEdad() < 100) && this.random.nextInt(500) == 0) {
            setEdad(getEdad() + 1);
            if (getSubType() == 1 && getEdad() > 70) {
                int i = this.random.nextInt(6) + 2;
                setType(i);

            }
        }

        if (getUpset()) {
            this.attacking += (this.random.nextInt(4)) + 2;
            if (this.attacking > 75) {
                this.attacking = 75;
            }

            if (this.random.nextInt(200) == 0 || getTarget() == null) {
                calm();
            }

            if (!getCharging() && this.random.nextInt(35) == 0) {
                swingLeg();
            }

            if (!getCharging()) {
                this.getNavigation().stop();
            }

            if (getTarget() != null)// && rand.nextInt(100)==0)
            {
                lookAt(getTarget(), 10F, 10F);
                if (this.random.nextInt(80) == 0) {
                    setCharging(true);
                }
            }
        }

        if (getCharging()) {
            this.chargecount++;
            if (this.chargecount > 120) {
                this.chargecount = 0;
            }
            if (getTarget() == null) {
                calm();
            }
        }

        if (!getUpset() && !getCharging()) {
            PlayerEntity entityplayer1 = this.level.getNearestPlayer(this, 24D);
            if (entityplayer1 != null) {// Behaviour that happens only close to player :)

                // is there food around? only check with player near
                ItemEntity entityitem = getClosestEntityItem(this, 10D);
                if (entityitem != null) {
                    float f = entityitem.distanceTo(this);
                    if (f > 2.0F) {
                        int i = MathHelper.floor(entityitem.getX());
                        int j = MathHelper.floor(entityitem.getY());
                        int k = MathHelper.floor(entityitem.getZ());
                        faceLocation(i, j, k, 30F);

                        getMyOwnPath(entityitem, f);
                        return;
                    }
                    if ((f < 2.0F) && (entityitem != null) && (this.deathTime == 0) && this.random.nextInt(50) == 0) {
                        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_EATING);
                        setEating(true);
                        entityitem.remove();
                        return;
                    }
                }

                // find other goat to play!
                if (getSubType() > 4 && this.random.nextInt(200) == 0) {
                    MoCEntityGoat entitytarget = (MoCEntityGoat) getClosestEntityLiving(this, 14D);
                    if (entitytarget != null) {
                        setUpset(true);
                        setTarget(entitytarget);
                        entitytarget.setUpset(true);
                        entitytarget.setTarget(this);
                    }
                }

            }// end of close to player behavior
        }// end of !upset !charging
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && MoCTools.isItemEdible(stack.getItem());
    }

    @Override
    public int getAmbientSoundInterval() {
        if (this.hungry) {
            return 80;
        }

        return 200;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof MoCEntityGoat)) || ((((MoCEntityGoat) entity).getSubType() < 5)));
    }

    @Override
    public boolean isMovementCeased() {
        return getUpset() && !getCharging();
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        this.attacking = 30;
        if (entityIn instanceof MoCEntityGoat) {
            MoCTools.bigsmack(this, entityIn, 0.4F);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_SMACK);
            if (this.random.nextInt(3) == 0) {
                calm();
                ((MoCEntityGoat) entityIn).calm();
            }
            return false;
        }
        MoCTools.bigsmack(this, entityIn, 0.8F);
        if (this.random.nextInt(3) == 0) {
            calm();
        }
        return super.doHurtTarget(entityIn);
    }

    @Override
    public boolean isNotScared() {
        return getSubType() > 4;
    }

    private void swingLeg() {
        if (!getSwingLeg()) {
            setSwingLeg(true);
            this.movecount = 0;
        }
    }

    public boolean getSwingLeg() {
        return this.swingLeg;
    }

    public void setSwingLeg(boolean flag) {
        this.swingLeg = flag;
    }

    public boolean getSwingEar() {
        return this.swingEar;
    }

    public void setSwingEar(boolean flag) {
        this.swingEar = flag;
    }

    public boolean getSwingTail() {
        return this.swingTail;
    }

    public void setSwingTail(boolean flag) {
        this.swingTail = flag;
    }

    public boolean getEating() {
        return this.eating;
    }

    public void setEating(boolean flag) {
        this.eating = flag;
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if ((entity != this && entity instanceof LivingEntity) && super.shouldAttackPlayers() && getSubType() > 4) {
                setTarget((LivingEntity) entity);
                setUpset(true);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void tick() {

        if (getSwingLeg()) {
            this.movecount += 5;
            if (this.movecount == 30) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_DIGG);
            }

            if (this.movecount > 100) {
                setSwingLeg(false);
                this.movecount = 0;
            }
        }

        if (getSwingEar()) {
            this.earcount += 5;
            if (this.earcount > 40) {
                setSwingEar(false);
                this.earcount = 0;
            }
        }

        if (getSwingTail()) {
            this.tailcount += 15;
            if (this.tailcount > 135) {
                setSwingTail(false);
                this.tailcount = 0;
            }
        }

        if (getEating()) {
            this.eatcount += 1;
            if (this.eatcount == 2) {
                PlayerEntity entityplayer1 = this.level.getNearestPlayer(this, 3D);
                if (entityplayer1 != null) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_EATING);
                }
            }
            if (this.eatcount > 25) {
                setEating(false);
                this.eatcount = 0;
            }
        }

        super.tick();
    }

    public int legMovement() {
        if (!getSwingLeg()) {
            return 0;
        }

        if (this.movecount < 21) {
            return this.movecount * -1;
        }
        if (this.movecount < 70) {
            return this.movecount - 40;
        }
        return -this.movecount + 100;
    }

    public int earMovement() {
        // 20 to 40 default = 30
        if (!getSwingEar()) {
            return 0;
        }
        if (this.earcount < 11) {
            return this.earcount + 30;
        }
        if (this.earcount < 31) {
            return -this.earcount + 50;
        }
        return this.earcount - 10;
    }

    public int tailMovement() {
        // 90 to -45
        if (!getSwingTail()) {
            return 90;
        }

        return this.tailcount - 45;
    }

    public int mouthMovement() {
        if (!getEating()) {
            return 0;
        }
        if (this.eatcount < 6) {
            return this.eatcount;
        }
        if (this.eatcount < 16) {
            return -this.eatcount + 10;
        }
        return this.eatcount - 20;
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && stack.getItem() == Items.BUCKET) {
            if (getSubType() > 4) {
                setUpset(true);
                setAttackTarget(player);
                return false;
            }
            if (getSubType() == 1) {
                return false;
            }

            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            player.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET));
            return true;
        }

        if (getIsTamed() && !stack.isEmpty() && (MoCTools.isItemEdible(stack.getItem()))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            this.setHealth(getMaxHealth());
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_EATING);
            return true;
        }

        if (!getIsTamed() && !stack.isEmpty() && MoCTools.isItemEdible(stack.getItem())) {
            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            return true;
        }

        return super.processInteract(player, hand);

    }*/

    public boolean getBleating() {
        return this.bleat && (getAttacking() == 0);
    }

    public void setBleating(boolean flag) {
        this.bleat = flag;
    }

    public int getAttacking() {
        return this.attacking;
    }

    public void setAttacking(int flag) {
        this.attacking = flag;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_GOAT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        setBleating(true);
        if (getSubType() == 1) {
            return MoCSoundEvents.ENTITY_GOAT_AMBIENT_BABY;
        }
        if (getSubType() > 2 && getSubType() < 5) {
            return MoCSoundEvents.ENTITY_GOAT_AMBIENT_FEMALE;
        }

        return MoCSoundEvents.ENTITY_GOAT_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_GOAT_DEATH;
    }

    @Override
    public int getMaxEdad() {
        return 50; //so the update is not handled on MoCEntityAnimal
    }

    @Override
    public float getSpeed() {
        return 0.15F;
    }
}
