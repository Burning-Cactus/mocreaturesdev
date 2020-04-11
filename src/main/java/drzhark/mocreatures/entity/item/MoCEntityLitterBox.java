package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityLitterBox extends LivingEntity {

    public int littertime;
    private static final DataParameter<Boolean> PICKED_UP = EntityDataManager.<Boolean>createKey(MoCEntityLitterBox.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> USED_LITTER = EntityDataManager.<Boolean>createKey(MoCEntityLitterBox.class, DataSerializers.BOOLEAN);

    public MoCEntityLitterBox(EntityType<? extends MoCEntityLitterBox> type, World world) {
        super(type, world);
    }

    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("litterbox.png");
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D); // setMaxHealth
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(PICKED_UP, Boolean.valueOf(false));
        this.dataManager.register(USED_LITTER, Boolean.valueOf(false));
    }

    public boolean getPickedUp() {
        return ((Boolean)this.dataManager.get(PICKED_UP)).booleanValue();
    }

    public boolean getUsedLitter() {
        return ((Boolean)this.dataManager.get(USED_LITTER)).booleanValue();
    }

    public void setPickedUp(boolean flag) {
        this.dataManager.set(PICKED_UP, Boolean.valueOf(flag));
    }

    public void setUsedLitter(boolean flag) {
        this.dataManager.set(USED_LITTER, Boolean.valueOf(flag));
    }

    public boolean attackEntityFrom(Entity entity, int i) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public boolean canBePushed() {
        return this.isAlive();
    }

    @Override
    public HandSide getPrimaryHand() {
        return null;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof PlayerEntity)
        {
            return ((PlayerEntity) this.getRidingEntity()).isCrouching() ? 0.25 : 0.5F;
        }
        return super.getYOffset();

    }

    @Override
    public void handleStatusUpdate(byte byte0) {
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return null;
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        return null;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && ((stack.getItem() == Items.STONE_PICKAXE) || (stack.getItem() == Items.WOODEN_PICKAXE)
                        || (stack.getItem() == Items.IRON_PICKAXE) || (stack.getItem() == Items.GOLDEN_PICKAXE) || (stack.getItem() == Items.DIAMOND_PICKAXE))) {
            player.inventory.addItemStackToInventory(new ItemStack(MoCItems.LITTERBOX));
            this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            setDead();
            return true;
        }

        if (!stack.isEmpty() && (stack.getItem() == Item.getItemFromBlock(Blocks.SAND))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            setUsedLitter(false);
            this.littertime = 0;
            return true;
        }
        
        if (this.getRidingEntity() == null) {
            if (this.startRiding(player)) {
                setPickedUp(true);
                this.rotationYaw = player.rotationYaw;
            }

            return true;
        }

        return true;
    }

    @Override
    public void move(MoverType type, double d, double d1, double d2) {
        if ((this.getRidingEntity() != null) || !this.onGround || !MoCreatures.proxy.staticLitter) {
            if (!this.world.isRemote) {
                super.move(type, d, d1, d2);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround) {
            setPickedUp(false);
        }
        if (getUsedLitter() && !this.world.isRemote) {
            this.littertime++;
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(12D, 4D, 12D));
            for (int i = 0; i < list.size(); i++) {
                Entity entity = list.get(i);
                if (!(entity instanceof MobEntity)) {
                    continue;
                }
                MobEntity entitymob = (MobEntity) entity;
                entitymob.setAttackTarget(this);
                if (entitymob instanceof CreeperEntity) {
                    ((CreeperEntity) entitymob).setCreeperState(-1);
                }
                if (entitymob instanceof MoCEntityOgre) {
                    ((MoCEntityOgre) entitymob).smashCounter = 0;
                }
            }

        }
        if (this.littertime > 5000 && !this.world.isRemote) {
            setUsedLitter(false);
            this.littertime = 0;
        }
        
        if (this.isPassenger()) MoCTools.dismountSneakingPlayer(this);
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.putBoolean("UsedLitter", getUsedLitter());
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        setUsedLitter(nbttagcompound.getBoolean("UsedLitter"));
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        return false;
    }
}
