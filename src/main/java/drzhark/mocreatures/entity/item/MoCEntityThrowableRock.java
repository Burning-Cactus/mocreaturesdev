package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.registry.MoCEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityThrowableRock extends Entity {

    public int rockTimer;
    public int acceleration = 100;
    private double oPosX;
    private double oPosY;
    private double oPosZ;
    private static final DataParameter<Integer> ROCK_STATE = EntityDataManager.defineId(MoCEntityThrowableRock.class, DataSerializers.INT);
    private static final DataParameter<Integer> MASTERS_ID = EntityDataManager.defineId(MoCEntityThrowableRock.class, DataSerializers.INT);
    private static final DataParameter<Integer> BEHAVIOUR_TYPE = EntityDataManager.defineId(MoCEntityThrowableRock.class, DataSerializers.INT);

    public MoCEntityThrowableRock(EntityType<? extends MoCEntityThrowableRock> type, World worldIn) {
        super(type, worldIn);
        this.blocksBuilding = true;
        //this.yOffset = this.height / 2.0F;
    }

    public MoCEntityThrowableRock(World par1World, Entity entitythrower, double x, double y, double z)//, int behavior)//, int bMetadata)
    {
        this(MoCEntities.TROCK, par1World);
        this.setPos(x, y, z);
        this.rockTimer = 250;
        this.xo = this.oPosX = x;
        this.yo = this.oPosY = y;
        this.zo = this.oPosZ = z;
        this.setMasterID(entitythrower.getId());
    }

    public void setState(BlockState state) {
        this.entityData.set(ROCK_STATE, (Block.getId(state) & 65535));
    }

    public BlockState getState() {
        return Block.stateById(this.entityData.get(ROCK_STATE) & 65535);
    }

    public void setMasterID(int i) {
        this.entityData.set(MASTERS_ID, i);
    }

    public int getMasterID() {
        return this.entityData.get(MASTERS_ID);
    }

    public void setBehavior(int i) {
        this.entityData.set(BEHAVIOUR_TYPE, i);
    }

    public int getBehavior() {
        return this.entityData.get(BEHAVIOUR_TYPE);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(BEHAVIOUR_TYPE, 0);
        this.entityData.define(ROCK_STATE, 0);
        this.entityData.define(MASTERS_ID, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        BlockState iblockstate = this.getState();
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.putInt("Behavior", getBehavior());
        nbttagcompound.putInt("MasterID", getMasterID());
        nbttagcompound.putShort("BlockID", (short) Block.getId(iblockstate));
//        nbttagcompound.putShort("BlockMetadata", (short) iblockstate.getBlock().getMetaFromState(iblockstate));
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return null;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        setBehavior(nbttagcompound.getInt("Behavior"));
        setMasterID(nbttagcompound.getInt("MasterID"));
        BlockState iblockstate;
        iblockstate = Block.stateById(nbttagcompound.getShort("BlockID"));
        this.setState(iblockstate);
    }

    @Override
    public boolean isPickable() {
        return this.isAlive();
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        Entity master = getMaster();
        if (this.rockTimer-- <= -50 && getBehavior() == 0) {
            transformToItem();
        }

        /*if (getBehavior() == 0) {
            System.out.println("Zero Rock, Server? =" + !this.world.isRemote + " age " + rockTimer + " at " + this);
        }*/
        //held Trocks don't need to adjust its position
        if (getBehavior() == 1) {
            return;
        }

        //rock damage code (for all rock behaviors)
        if (!this.onGround) {
            List<Entity> list =
                    this.level.getEntities(this,
                            this.getBoundingBox().contract(this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z).expandTowards(1.0D, 1.0D, 1.0D));

            for (int i = 0; i < list.size(); i++) {
                Entity entity1 = (Entity) list.get(i);
                if (master != null && entity1.getId() == master.getId()) {
                    continue;
                }
                if (entity1 instanceof MoCEntityGolem) {
                    continue;
                }
                if (entity1 != null && !(entity1 instanceof LivingEntity)) {
                    continue;
                }

                if (master != null) {
                    entity1.hurt(DamageSource.mobAttack((LivingEntity) master), 4);
                } else {
                    entity1.hurt(DamageSource.GENERIC, 4);
                }
            }
        }

        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();

        if (getBehavior() == 2) {
            if (master == null) {
                setBehavior(0);
                this.rockTimer = -50;
                return;
            }

            //moves towards the master entity the bigger the number, the slower
            --this.acceleration;
            if (this.acceleration < 10) {
                this.acceleration = 10;
            }

            float tX = (float) this.getX() - (float) master.getX();
            float tZ = (float) this.getZ() - (float) master.getZ();
            float distXZToMaster = tX * tX + tZ * tZ;

            if (distXZToMaster < 1.5F && master instanceof MoCEntityGolem) {
                ((MoCEntityGolem) master).receiveRock(this.getState());
                this.setBehavior(0);
                this.remove();
            }

            double summonedSpeed = this.acceleration;//20D;
            this.setDeltaMovement(((master.getX() - this.getX()) / summonedSpeed), ((master.getY() - this.getY()) / 20D + 0.15D), ((master.getZ() - this.getZ()) / summonedSpeed));
            if (!this.level.isClientSide) {
                this.move(MoverType.SELF, this.getDeltaMovement());
            }
            return;
        }

        if (getBehavior() == 4)// imploding / exploding rock
        {
            if (master == null) {
                if (!this.level.isClientSide) {
                    setBehavior(5);
                }
                return;
            }

            //moves towards the master entity the bigger the number, the slower
            this.acceleration = 10;

            float tX = (float) this.getX() - (float) master.getX();
            float tZ = (float) this.getZ() - (float) master.getZ();
            float distXZToMaster = tX * tX + tZ * tZ;

            double summonedSpeed = this.acceleration;//20D;
            this.setDeltaMovement(((master.getX() - this.getX()) / summonedSpeed), (master.getY() - this.getY()) / 20D + 0.15D, (master.getZ() - this.getZ()) / summonedSpeed);

            if (distXZToMaster < 2.5F && master instanceof MoCEntityGolem) {
                this.setDeltaMovement(Vector3d.ZERO);
            }

            if (!this.level.isClientSide) {
                this.move(MoverType.SELF, this.getDeltaMovement());
            }

            return;
        }

        if (getBehavior() == 5)// exploding rock
        {
            this.acceleration = 5;
            double summonedSpeed = this.acceleration;//20D;
            this.setDeltaMovement(((this.oPosX - this.getX()) / summonedSpeed), ((this.oPosY - this.getY()) / 20D + 0.15D), ((this.oPosZ - this.getZ()) / summonedSpeed));
            if (!this.level.isClientSide) {
                this.move(MoverType.SELF, this.getDeltaMovement());
            }
            setBehavior(0);
            return;
        }

        this.getDeltaMovement().subtract(0, 0.04D, 0);
        if (!this.level.isClientSide) {
            this.move(MoverType.SELF, this.getDeltaMovement());
        }
        this.getDeltaMovement().scale(0.98D);

        if (this.onGround) {
            this.getDeltaMovement().multiply(0.699D, -0.5D, 0.699D);
        }

    }

    private void transformToItem() {
        if (!this.level.isClientSide && MoCTools.mobGriefing(this.level) && MoCConfig.COMMON_CONFIG.GENERAL.monsterSettings.golemDestroyBlocks.get()) // don't drop rocks if mobgriefing is set to false, prevents duping
        {
            ItemEntity entityitem =
                    new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(this.getState().getBlock(), 1));
            entityitem.setPickUpDelay(10);
//            entityitem.setAgeToCreativeDespawnTime();
            this.level.addFreshEntity(entityitem);
        }
        this.remove();
    }

    public Block getMyBlock() {
        if (this.getState() != null) {
            return this.getState().getBlock();
        }
        return Blocks.STONE;
    }

    private Entity getMaster() {
        List<Entity> entityList = this.level.getLoadedEntitiesOfClass(this.getClass(),
                new AxisAlignedBB(this.getX(), this.getY(), this.getZ(),
                        this.getX() + 16.0D, this.getY() + 16.0D, this.getZ() + 16.0D));
        for (Entity ent : entityList) {
            if (ent.getId() == getMasterID()) {
                return ent;
            }
        }

        return null;
    }
}
