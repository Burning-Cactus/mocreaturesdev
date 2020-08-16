package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.registry.MoCItems;
import drzhark.mocreatures.registry.MoCSoundEvents;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MoCEntityFox extends MoCEntityTameableAnimal {

    public MoCEntityFox(EntityType<? extends MoCEntityFox> type, World world) {
        super(type, world);
        setEdad(this.rand.nextInt(15) + 50);
        if (this.rand.nextInt(3) == 0) {
            setAdult(false);

        } else {
            setAdult(true);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.0D));
        this.goalSelector.addGoal(3, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.goalSelector.addGoal(3, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new EntityAIHunt(this, AnimalEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .func_233815_a_(Attributes.MAX_HEALTH, 15.0D)
                .func_233815_a_(Attributes.ATTACK_DAMAGE, 1.0D)
                .func_233815_a_(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getSubType() == 0) {
            setType(1);
        }
    }

    @Override
    public ResourceLocation getTexture() {

        if (!getIsAdult()) {
            if (getSubType() == 2) {
                return MoCreatures.getTexture("foxsnow.png");
            }
            return MoCreatures.getTexture("foxcub.png");
        }
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("fox.png");
            case 2:
                return MoCreatures.getTexture("foxsnow.png");

            default:
                return MoCreatures.getTexture("fox.png");
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if ((entity != this && this.isNotScared() && entity instanceof LivingEntity) && super.shouldAttackPlayers()) {
                setAttackTarget((LivingEntity) entity);
                setRevengeTarget((LivingEntity) entity);
                return true;
            }

        }
        return false;
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && ((stack.getItem() == MoCItems.TURKEY_RAW))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }
            this.setHealth(getMaxHealth());

            if (!this.world.isRemote && !getIsAdult() && (getEdad() < 100)) {
                setEdad(getEdad() + 1);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    @Override
    public boolean isNotScared() {
        return getIsAdult();
    }

    /*@Override
    public boolean checkSpawningBiome() {
        BlockPos pos =
                new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY),
                        MathHelper.floor(this.getPosZ()));
        Biome currentbiome = MoCTools.Biomekind(this.world, pos);
        try {
            if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
                setType(2);
            }
        } catch (Exception e) {
        }
        return true;
    }*/

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FOX_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_FOX_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Override
    protected float getSoundVolume() {
        return 0.3F;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == MoCItems.RAT_RAW;
    }

    @Override
    public int nameYOffset() {
        return -50;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityFox) && entity.getHeight() <= 0.7D && entity.getWidth() <= 0.7D;
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public float getSizeFactor() {
        if (getIsAdult()) {
            return 0.9F;
        }
        return 0.9F * getEdad() * 0.01F;
    }
}
