package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.registry.MoCItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityLitterBox extends LivingEntity {

    public int littertime;
    private static final DataParameter<Boolean> PICKED_UP = EntityDataManager.<Boolean>defineId(MoCEntityLitterBox.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> USED_LITTER = EntityDataManager.<Boolean>defineId(MoCEntityLitterBox.class, DataSerializers.BOOLEAN);

    public MoCEntityLitterBox(EntityType<? extends MoCEntityLitterBox> type, World world) {
        super(type, world);
    }

    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("litterbox.png");
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 20.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PICKED_UP, Boolean.FALSE);
        this.entityData.define(USED_LITTER, Boolean.FALSE);
    }

    public boolean getPickedUp() {
        return this.entityData.get(PICKED_UP);
    }

    public boolean getUsedLitter() {
        return this.entityData.get(USED_LITTER);
    }

    public void setPickedUp(boolean flag) {
        this.entityData.set(PICKED_UP, flag);
    }

    public void setUsedLitter(boolean flag) {
        this.entityData.set(USED_LITTER, flag);
    }

    public boolean attackEntityFrom(Entity entity, int i) {
        return false;
    }

    @Override
    public boolean isPickable() {
        return this.isAlive();
    }

    @Override
    public boolean isPushable() {
        return this.isAlive();
    }

    @Override
    public HandSide getMainArm() {
        return null;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

//    @Override
//    protected boolean canDespawn() {
//        return false;
//    }

//    @Override
//    public void fall(float f, float f1) {
//    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    public double getMyRidingOffset() {
        if (this.getVehicle() instanceof PlayerEntity)
        {
            return ((PlayerEntity) this.getVehicle()).isCrouching() ? 0.25 : 0.5F;
        }
        return super.getMyRidingOffset();

    }

    @Override
    public void handleEntityEvent(byte byte0) {
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlotType slotIn) {
        return null;
    }

    @Override
    public void setItemSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && ((stack.getItem() == Items.STONE_PICKAXE) || (stack.getItem() == Items.WOODEN_PICKAXE)
                        || (stack.getItem() == Items.IRON_PICKAXE) || (stack.getItem() == Items.GOLDEN_PICKAXE) || (stack.getItem() == Items.DIAMOND_PICKAXE))) {
            player.inventory.add(new ItemStack(MoCItems.LITTERBOX));
            this.playSound(SoundEvents.ITEM_PICKUP, 0.2F, (((this.random.nextFloat() - this.random.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            remove();
            return ActionResultType.SUCCESS;
        }

        if (!stack.isEmpty() && (stack.getItem() == Item.byBlock(Blocks.SAND))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(hand, ItemStack.EMPTY);
            }
            setUsedLitter(false);
            this.littertime = 0;
            return ActionResultType.SUCCESS;
        }
        
        if (this.getVehicle() == null) {
            if (this.startRiding(player)) {
                setPickedUp(true);
                this.yRot = player.yRot;
            }

            return ActionResultType.SUCCESS;
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public void move(MoverType type, Vector3d motion) {
        if ((this.getVehicle() != null) || !this.onGround || !MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.staticLitter.get()) {
            if (!this.level.isClientSide) {
                super.move(type, motion);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround) {
            setPickedUp(false);
        }
        if (getUsedLitter() && !this.level.isClientSide) {
            this.littertime++;
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(12D, 4D, 12D));
            for (int i = 0; i < list.size(); i++) {
                Entity entity = list.get(i);
                if (!(entity instanceof MobEntity)) {
                    continue;
                }
                MobEntity entitymob = (MobEntity) entity;
                entitymob.setTarget(this);
                if (entitymob instanceof CreeperEntity) {
                    ((CreeperEntity) entitymob).setSwellDir(-1);
                }
                if (entitymob instanceof MoCEntityOgre) {
                    ((MoCEntityOgre) entitymob).smashCounter = 0;
                }
            }

        }
        if (this.littertime > 5000 && !this.level.isClientSide) {
            setUsedLitter(false);
            this.littertime = 0;
        }
        
        if (this.isPassenger()) MoCTools.dismountSneakingPlayer(this);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.putBoolean("UsedLitter", getUsedLitter());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        setUsedLitter(nbttagcompound.getBoolean("UsedLitter"));
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        return false;
    }
}
