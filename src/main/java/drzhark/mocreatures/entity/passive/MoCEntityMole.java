package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityMole extends MoCEntityTameableAnimal {

    private static final DataParameter<Integer> MOLE_STATE = EntityDataManager.<Integer>createKey(MoCEntityMole.class, DataSerializers.VARINT);

    public MoCEntityMole(EntityType<? extends MoCEntityMole> type, World world) {
        super(type, world);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("mole.png");
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(MOLE_STATE, Integer.valueOf(0)); // state - 0 outside / 1 digging / 2 underground / 3 pick-a-boo

    }

    public boolean isOnDirt() {
        BlockState block =
                this.world.getBlockState(
                        new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getBoundingBox().minY - 0.5D), MathHelper
                                .floor(this.getPosZ())));
        return isDiggableBlock(Block.getStateId(block));//(j == 2 | j == 3 | j == 12);
    }

    private boolean isDiggableBlock(int i) {
        return i == 2 | i == 3 | i == 12;
    }

    /**
     * Moves entity forward underground
     */
    @SuppressWarnings("unused")
    private void digForward() {
        double coordY = this.getPosY();
        double coordZ = this.getPosZ();
        double coordX = this.getPosX();
        int x = 1;
        double newPosY = coordY - Math.cos((this.rotationPitch - 90F) / 57.29578F) * x;
        double newPosX =
                coordX + Math.cos((MoCTools.realAngle(this.rotationYaw - 90F) / 57.29578F)) * (Math.sin((this.rotationPitch - 90F) / 57.29578F) * x);
        double newPosZ =
                coordZ + Math.sin((MoCTools.realAngle(this.rotationYaw - 90F) / 57.29578F)) * (Math.sin((this.rotationPitch - 90F) / 57.29578F) * x);
        BlockState block =
                this.world.getBlockState(
                        new BlockPos(MathHelper.floor(newPosX), MathHelper.floor(newPosY), MathHelper.floor(newPosZ)));
        if (isDiggableBlock(Block.getStateId(block))) {
            this.setPosition(newPosX, newPosY, newPosZ);
        }
    }

    /**
     * obtains State
     *
     * @return 0 outside / 1 digging / 2 underground / 3 pick-a-boo
     */
    public int getState() {
        return ((Integer)this.dataManager.get(MOLE_STATE)).intValue();
    }

    /**
     * Changes the state
     *
     * @param i - 0 outside / 1 digging / 2 underground / 3 pick-a-boo
     */
    public void setState(int i) {
        this.dataManager.set(MOLE_STATE, Integer.valueOf(i));
    }

    @Override
    public float pitchRotationOffset() {

        int i = getState();
        switch (i) {
            case 0:
                return 0F;
            case 1:
                return -45F;
            case 2:
                return 0F;
            case 3:
                return 60F;
            default:
                return 0F;
        }
    }

    @Override
    public float getAdjustedYOffset() {
        int i = getState();
        switch (i) {
            case 0:
                return 0F;
            case 1:
                return 0.3F;
            case 2:
                return 1F;
            case 3:
                return 0.1F;
            default:
                return 0F;
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            if (this.rand.nextInt(10) == 0 && getState() == 1) {
                setState(2);
            }

            if (getState() != 2 && getState() != 1 && isOnDirt()) {
                LivingEntity entityliving = getBoogey(4D);
                if ((entityliving != null) && canEntityBeSeen(entityliving)) {
                    setState(1);
                    this.getNavigator().clearPath();
                }
            }

            //if underground and no enemies: pick a boo
            if (this.rand.nextInt(20) == 0 && getState() == 2 && (getBoogey(4D) == null)) {
                setState(3);
                this.getNavigator().clearPath();
            }

            //if not on dirt, get out!
            if (getState() != 0 && !isOnDirt()) {
                setState(0);
            }

            if (this.rand.nextInt(30) == 0 && getState() == 3) {
                setState(2);
            }

            /*
             * if (getState() == 2) { if (rand.nextInt(50) == 0) digForward(); }
             */

            //digging fx
            if ((getState() == 1 || getState() == 2)) {
                setSprinting(true);
            } else {
                setSprinting(false);
            }
        }
    }

    @Override
    public boolean isMovementCeased() {
        return getState() == 1 || getState() == 3;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (getState() != 2) {
            return super.attackEntityFrom(damagesource, i);
        }
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return (getState() != 2);
    }

    @Override
    public boolean canBePushed() {
        return (getState() != 2);
    }

    @Override
    protected void collideWithEntity(Entity par1Entity) {
        if (getState() != 2) {
            super.collideWithEntity(par1Entity);
            //            par1Entity.applyEntityCollision(this);
        }
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (getState() == 2) {
            return false;
        }
        return super.isEntityInsideOpaqueBlock();
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (getState() == 2) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

//    @Override
//    protected Item getDropItem() {
//        return MoCItems.FUR;
//    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_RABBIT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_RABBIT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }
}
