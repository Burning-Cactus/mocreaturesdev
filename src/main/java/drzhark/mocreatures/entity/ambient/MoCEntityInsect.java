package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityInsect extends MoCEntityAmbient {

    private int climbCounter;
    protected MoCAlternateWanderGoal wander;

    private static final DataParameter<Boolean> IS_FLYING = EntityDataManager.defineId(MoCEntityInsect.class, DataSerializers.BOOLEAN);
    
    public MoCEntityInsect(EntityType<? extends MoCEntityInsect> type, World world) {
        super(type, world);
        this.goalSelector.addGoal(2, this.wander = new MoCAlternateWanderGoal(this, 1.0D, 80));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityAmbient.registerAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_FLYING, false);
    }

    @Override
    public boolean isFlyer() {
        return false;
    }

    @Override
    public boolean isFlyingAlone() {
        return getIsFlying();
    }

    public boolean getIsFlying() {
        return ((Boolean)this.entityData.get(IS_FLYING)).booleanValue();
    }

    public void setIsFlying(boolean flag) {
        this.entityData.set(IS_FLYING, Boolean.valueOf(flag));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
//            if (!getIsFlying() && isOnLadder() && !this.onGround) {
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
//                        new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//            }

            if (isFlyer() && !getIsFlying() && this.random.nextInt(getFlyingFreq()) == 0) {
                List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(4D, 4D, 4D));
                for (int i = 0; i < list.size(); i++) {
                    Entity entity1 = list.get(i);
                    if (!(entity1 instanceof LivingEntity)) {
                        continue;
                    }
                    if (((LivingEntity) entity1).getBbWidth() >= 0.4F && ((LivingEntity) entity1).getBbHeight() >= 0.4F && canSee(entity1)) {
                        setIsFlying(true);
                        this.wander.makeUpdate();
                    }
                }
            }

            if (isFlyer() && !getIsFlying() && this.random.nextInt(200) == 0) {
                setIsFlying(true);
                this.wander.makeUpdate();
            }

            if (isAttractedToLight() && this.random.nextInt(50) == 0) {
                int ai[] = MoCTools.ReturnNearestBlockCoord(this, Blocks.TORCH, 8D);
                if (ai[0] > -1000) {
                    this.getNavigation().moveTo(ai[0], ai[1], ai[2], 1.0D);//
                }
            }

            //this makes the flying insect move all the time in the air
            if (getIsFlying() && this.getNavigation().isDone() && !isMovementCeased() && this.getTarget() == null) {
                this.wander.makeUpdate();
            }

        } else // client stuff
        {
            if (this.climbCounter > 0 && ++this.climbCounter > 8) {
                this.climbCounter = 0;
            }
        }
    }

    /**
     * Is this insect attracted to light?
     *
     * @return
     */
    public boolean isAttractedToLight() {
        return false;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //climbing animation
        {
            this.climbCounter = 1;
        }

    }

    @Override
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason reason) {
        return super.getCanSpawnHereAnimal() && super.getCanSpawnHereCreature();
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4;
    }

    @Override
    public boolean onClimbable() {
        return this.horizontalCollision;
    }

    public boolean climbing() {
        return (this.climbCounter != 0);
    }

    @Override
    protected void jumpFromGround() {
    }

    @Override
    protected boolean isMovementNoisy() {
        return false;
    }

    protected int getFlyingFreq() {
        return 20;
    }

    @Override
    public float rollRotationOffset() {
        return 0F;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    public PathNavigator getNavigation() {
        /*if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        */if (this.getIsFlying()) {
            return this.navigatorFlyer;
        }
        return this.navigation;
    }

    @Override
    public Entity getEntity() {
        return this;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
