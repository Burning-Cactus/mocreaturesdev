package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class MoCEntityEnt extends MoCEntityAnimal {

    public MoCEntityEnt(EntityType<? extends MoCEntityEnt> type, World world) {
        super(type, world);
        this.stepHeight = 2F;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityAnimal.registerAttributes()
                .func_233815_a_(Attributes.MAX_HEALTH, 40.0D)
                .func_233815_a_(Attributes.ATTACK_DAMAGE, 3.0D)
                .func_233815_a_(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.rand.nextInt(2) + 1);
        }
    }

    @Override
    public boolean renderName() {
        return false;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("ent_oak.png");
            case 2:
                return MoCreatures.getTexture("ent_birch.png");
            default:
                return MoCreatures.getTexture("ent_oak.png");
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (damagesource.getTrueSource() != null && damagesource.getTrueSource() instanceof PlayerEntity) {
            PlayerEntity ep = (PlayerEntity) damagesource.getTrueSource();
            ItemStack currentItem = ep.inventory.getCurrentItem();
            if (currentItem != null) {
                Item itemheld = currentItem.getItem();
                if (itemheld != null && itemheld instanceof AxeItem) {
                    this.world.getDifficulty();
                    if (super.shouldAttackPlayers()) {
                        setAttackTarget(ep);

                    }
                    return super.attackEntityFrom(damagesource, i);
                }
            }
        }
        if (damagesource.isFireDamage()) {
            return super.attackEntityFrom(damagesource, i);
        }
        return false;
    }

    /*@Override
    protected void dropFewItems(boolean flag, int x) {
        int i = this.rand.nextInt(3);
        int qty = this.rand.nextInt(12) + 4;
        int typ = 0;
        if (getSubType() == 2) {
            typ = 2;
        }
        if (i == 0) {
            entityDropItem(new ItemStack(Blocks.LOG, qty, typ), 0.0F);
            return;
        }
        if (i == 1) {
            entityDropItem(new ItemStack(Items.STICK, qty, 0), 0.0F);
            return;

        }
        entityDropItem(new ItemStack(Blocks.SAPLING, qty, typ), 0.0F);
    }*/

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_ENT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_ENT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_ENT_AMBIENT;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {

            if (this.getAttackTarget() == null && this.rand.nextInt(500) == 0) {
                plantOnFertileGround();
            }

            if (this.rand.nextInt(100) == 0) {
                atractCritter();
            }
        }
    }

    /**
     * Makes small creatures follow the Ent
     */
    private void atractCritter() {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(8D, 3D, 8D));
        int n = this.rand.nextInt(3) + 1;
        int j = 0;
        for (int k = 0; k < list.size(); k++) {
            Entity entity = list.get(k);
            if (entity instanceof AnimalEntity && entity.getWidth() < 0.6F && entity.getHeight() < 0.6F) {
                AnimalEntity entityanimal = (AnimalEntity) entity;
                if (entityanimal.getAttackTarget() == null && !MoCTools.isTamed(entityanimal)) {
                    Path pathentity = entityanimal.getNavigator().getPathToEntity(this, 1);
                    entityanimal.setAttackTarget(this);
                    entityanimal.getNavigator().setPath(pathentity, 1D);
                    j++;
                    if (j > n) {
                        return;
                    }
                }

            }
        }
    }

    private boolean plantOnFertileGround() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY()), MathHelper.floor(this.getPosZ()));
        Block blockUnderFeet = this.world.getBlockState(pos.down()).getBlock();
        Block blockOnFeet = this.world.getBlockState(pos).getBlock();

        if (blockUnderFeet == Blocks.DIRT) {
            Block block = Blocks.GRASS;
            BlockEvent.BreakEvent event = null;
            if (!this.world.isRemote) {
                event =
                        new BlockEvent.BreakEvent(this.world, pos, block.getDefaultState(), FakePlayerFactory.get((ServerWorld) this.world,
                                MoCreatures.MOCFAKEPLAYER));
            }
            if (event != null && !event.isCanceled()) {
                this.world.setBlockState(pos.down(), block.getDefaultState(), 3);
                return true;
            }
            return false;
        }

        if (blockUnderFeet == Blocks.GRASS && blockOnFeet == Blocks.AIR) {
            BlockState iblockstate = getBlockStateToBePlanted();
            int plantChance = 3;
            if (iblockstate.getBlock() == Blocks.OAK_SAPLING) {
                plantChance = 10;
            }
            //boolean cantPlant = false;
            // check perms first
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos pos1 = new BlockPos(MathHelper.floor(this.getPosX() + x), MathHelper.floor(this.getPosY()), MathHelper.floor(this.getPosZ() + z));
                    //BlockEvent.BreakEvent event = null;
                    //if (!this.world.isRemote) {
                    //    event =
                    //            new BlockEvent.BreakEvent(this.world, pos1, iblockstate, FakePlayerFactory.get((WorldServer) this.world,
                    //                    MoCreatures.MOCFAKEPLAYER));
                    //}
                    //cantPlant = (event != null && event.isCanceled());
                    Block blockToPlant = this.world.getBlockState(pos1).getBlock();
                    //if (!cantPlant && this.rand.nextInt(plantChance) == 0 && blockToPlant == Blocks.AIR) {
                    if (this.rand.nextInt(plantChance) == 0 && blockToPlant == Blocks.AIR) {
                        this.world.setBlockState(pos1, iblockstate, 3);
                    }
                }
            }
            return true;
        }

        return false;
    }

    /**
     * Returns a random blockState
     *
     * @return Any of the flowers, mushrooms, grass and saplings
     */
    private BlockState getBlockStateToBePlanted() {
        int blockID = 0;
        int metaData = 0;
        switch (this.rand.nextInt(20)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                blockID = 31;
                metaData = rand.nextInt(2) + 1;
                break;
            case 8:
            case 9:
            case 10:
                blockID = 175; //other flowers
                metaData = rand.nextInt(6);
                break;
            case 11:
            case 12:
            case 13:
                blockID = 37; //dandelion
                break;
            case 14:
            case 15:
            case 16:
                blockID = 38; //flowers
                metaData = rand.nextInt(9);
                break;
            case 17:
                blockID = 39; //brown mushroom
                break;
            case 18:
                blockID = 40; //red mushroom
                break;
            case 19:
                blockID = 6; //sapling
                if (getSubType() == 2) {
                    metaData = 2; //to place the right sapling
                }
                break;

            default:
                blockID = 31;
        }
        BlockState iblockstate;
        iblockstate = Block.getStateById(blockID);
        return iblockstate;

    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (this.attackTime <= 0 && (f < 2.5D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)) {
            attackTime = 200;
            this.world.playSoundAtEntity(this, "mocreatures:goatsmack", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
            MoCTools.bigsmack(this, entity, 2F);
        }
    }*/

    @Override
    public void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_SMACK);
        MoCTools.bigsmack(this, entityIn, 1F);
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
}
