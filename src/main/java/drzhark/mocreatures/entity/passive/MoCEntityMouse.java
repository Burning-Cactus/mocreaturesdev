package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.FleeFromPlayerGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class MoCEntityMouse extends MoCEntityAnimal {

    public MoCEntityMouse(EntityType<? extends MoCEntityMouse> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new FleeFromPlayerGoal(this, 1.2D, 4D));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(5, new MoCAlternateWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getSubType() == 0) {
            setType(this.random.nextInt(3) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 2:
                return MoCreatures.getTexture("miceb.png");
            case 3:
                return MoCreatures.getTexture("micew.png");

            default:
                return MoCreatures.getTexture("miceg.png");
        }
    }

    /*@Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());
        Biome currentbiome = MoCTools.Biomekind(this.world, pos);

        try {
            if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
                setType(3); //white mice!
            }
        } catch (Exception e) {
        }
        return true;
    }*/

    public boolean getIsPicked() {
        return this.getVehicle() != null;
    }

    public boolean climbing() {
        return !this.onGround && onClimbable();
    }

    /*@Override
    public boolean canSpawn(IWorld worldIn, SpawnReason reason) {
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getPosZ());
        BlockPos pos = new BlockPos(i, j, k);
        Block block = this.world.getBlockState(pos.down()).getBlock();
        return ((MoCreatures.entityMap.get(this.getType()).getFrequency() > 0) && this.world.checkNoEntityCollision(this)
                && (this.world.getCollisionShapes(this, this.getBoundingBox()).count() == 0)
                && !this.world.containsAnyLiquid(this.getBoundingBox()) && ((block == Blocks.COBBLESTONE) || (block.getTags().contains(BlockTags.PLANKS.getName()))
                || (block == Blocks.DIRT) || (block == Blocks.STONE) || (block == Blocks.GRASS)));
    }*/

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_MOUSE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_MOUSE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_MOUSE_AMBIENT;
    }

    @Override
    public double getMyRidingOffset() {
        if (this.getVehicle() instanceof PlayerEntity /*&& this.getRidingEntity() == MoCreatures.proxy.getPlayer()*/ && this.level.isClientSide) {
            return (super.getMyRidingOffset() - 0.7F);
        }

        if ((this.getVehicle() instanceof PlayerEntity) && this.level.isClientSide) {
            return (super.getMyRidingOffset() - 0.1F);
        } else {
            return super.getMyRidingOffset();
        }
    }

    /*@Override TODO Apparently this method in MobEntity is final?!?!?
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        if (this.getRidingEntity() == null) {
            if (this.startRiding(player)) {
                this.rotationYaw = player.rotationYaw;
            }

            return ActionResultType.SUCCESS;
        }

        return super.processInitialInteract(player, hand);
    }*/

    @Override
    public boolean onClimbable() {
        return this.horizontalCollision;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.onGround && (this.getVehicle() != null)) {
            this.yRot = this.getVehicle().yRot;
        }
        
    }

    public boolean upsideDown() {
        return getIsPicked();
    }
    
    @Override
    public boolean canRidePlayer()
    {
        return true;
    }
}
