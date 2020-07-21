package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.List;

public class MoCEntityElephant extends MoCEntityTameableAnimal {
        //TODO: Literally fix this entire class, a lot of it is commented out for the sake of compiling the mod and testing it.
    public int sprintCounter;
    public int sitCounter;
    public MoCAnimalChest localelephantchest;
    public MoCAnimalChest localelephantchest2;
    public MoCAnimalChest localelephantchest3;
    public MoCAnimalChest localelephantchest4;
    public ItemStack localstack;
    boolean hasPlatform;
    public int tailCounter;
    public int trunkCounter;
    public int earCounter;
    private byte tuskUses;
    private byte temper;
    private static final DataParameter<Integer> TUSK_TYPE = EntityDataManager.<Integer>createKey(MoCEntityElephant.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> STORAGE_TYPE = EntityDataManager.<Integer>createKey(MoCEntityElephant.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HARNESS_TYPE = EntityDataManager.<Integer>createKey(MoCEntityElephant.class, DataSerializers.VARINT);


    public MoCEntityElephant(EntityType<? extends MoCEntityElephant> type, World world) {
        super(type, world);
        setAdult(true);
        setTamed(false);
        setEdad(50);
        this.stepHeight = 1.0F;

        if (!this.world.isRemote) {
            if (this.rand.nextInt(4) == 0) {
                setAdult(false);
            } else {
                setAdult(true);
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();
        if (getSubType() == 0) {
            setType(this.rand.nextInt(2) + 1);
        }
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TUSK_TYPE, Integer.valueOf(0));// tusks: 0 nothing, 1 wood, 2 iron, 3 diamond
        this.dataManager.register(STORAGE_TYPE, Integer.valueOf(0));// storage: 0 nothing, 1 chest, 2 chests....
        this.dataManager.register(HARNESS_TYPE, Integer.valueOf(0));// harness: 0 nothing, 1 harness, 2 cabin
    }

    public int getTusks() {
        return ((Integer)this.dataManager.get(TUSK_TYPE)).intValue();
    }

    public void setTusks(int i) {
        this.dataManager.set(TUSK_TYPE, Integer.valueOf(i));
    }

    @Override
    public int getArmorType() {
        return ((Integer)this.dataManager.get(HARNESS_TYPE)).intValue();
    }

    @Override
    public void setArmorType(int i) {
        this.dataManager.set(HARNESS_TYPE, Integer.valueOf(i));
    }

    public int getStorage() {
        return ((Integer)this.dataManager.get(STORAGE_TYPE)).intValue();
    }

    public void setStorage(int i) {
        this.dataManager.set(STORAGE_TYPE, Integer.valueOf(i));
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("elephantafrican.png");
            case 2:
                return MoCreatures.getTexture("elephantindian.png");
            case 3:
                return MoCreatures.getTexture("mammoth.png");
            case 4:
                return MoCreatures.getTexture("mammothsonghua.png");
            case 5:
                return MoCreatures.getTexture("elephantindianpretty.png");
            default:
                return MoCreatures.getTexture("elephantafrican.png");
        }
    }

    public float calculateMaxHealth() {
        switch (getSubType()) {
            case 1:
                return 40;
            case 2:
                return 30;
            case 3:
                return 50;
            case 4:
                return 60;
            case 5:
                return 40;

            default:
                return 30;
        }
    }

    @Override
    public double getCustomSpeed() {
        if (this.sitCounter != 0) {
            return 0D;
        }
        double tSpeed = 0.5D;
        if (getSubType() == 1) {
            tSpeed = 0.55D;
        } else if (getSubType() == 2) {
            tSpeed = 0.6D;
        } else if (getSubType() == 3) {
            tSpeed = 0.5D;
        } else if (getSubType() == 4) {
            tSpeed = 0.55D;
        } else if (getSubType() == 5) {
            tSpeed = 0.5D;
        }

        if (this.sprintCounter > 0 && this.sprintCounter < 150) {
            tSpeed *= 1.5D;
        }
        if (this.sprintCounter > 150) {
            tSpeed *= 0.5D;
        }

        return tSpeed;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            if ((this.sprintCounter > 0 && this.sprintCounter < 150) && (this.isBeingRidden()) && rand.nextInt(15) == 0) {
                MoCTools.buckleMobsNotPlayers(this, 3D, this.world);
            }

            if (this.sprintCounter > 0 && ++this.sprintCounter > 300) {
                this.sprintCounter = 0;
            }

            if (getIsTamed() && (!this.isBeingRidden()) && getArmorType() >= 1 && this.rand.nextInt(20) == 0) {
                PlayerEntity ep = this.world.getClosestPlayer(this, 3D);
                if (ep != null && (!MoCConfig.COMMON_CONFIG.OWNERSHIP.enableOwnership.get() || ep.getUniqueID().equals(this.getOwnerId())) && ep.isSneaking()) {
                    sit();
                }
            }

            if (MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.elephantBulldozer.get() && getIsTamed() && (this.isBeingRidden()) && (getTusks() > 0)) {
                int height = 2;
                if (getSubType() == 3) {
                    height = 3;
                }
                if (getSubType() == 4) {
                    height = 3;
                }
                int dmg = MoCTools.destroyBlocksInFront(this, 2D, this.getTusks(), height);
                checkTusks(dmg);

            }

        } else //client only animation counters
        {
            if (this.tailCounter > 0 && ++this.tailCounter > 8) {
                this.tailCounter = 0;
            }

            if (this.rand.nextInt(200) == 0) {
                this.tailCounter = 1;
            }

            if (this.trunkCounter > 0 && ++this.trunkCounter > 38) {
                this.trunkCounter = 0;
            }

            if (this.trunkCounter == 0 && this.rand.nextInt(200) == 0) {
                this.trunkCounter = rand.nextInt(10) + 1;
            }

            if (this.earCounter > 0 && ++this.earCounter > 30) {
                this.earCounter = 0;
            }

            if (this.rand.nextInt(200) == 0) {
                this.earCounter = rand.nextInt(20) + 1;
            }

        }

        if (this.sitCounter != 0) {
            if (++this.sitCounter > 100) {
                this.sitCounter = 0;
            }
        }
    }

    /**
     * Checks if the tusks sets need to break or not (wood = 59, stone = 131,
     * iron = 250, diamond = 1561, gold = 32)
     *
     * @param dmg
     */
    private void checkTusks(int dmg) {
        this.tuskUses += (byte) dmg;
        if ((this.getTusks() == 1 && this.tuskUses > 59) || (this.getTusks() == 2 && this.tuskUses > 250)
                || (this.getTusks() == 3 && this.tuskUses > 1000)) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_HURT);
            setTusks((byte) 0);
        }
    }

    private void sit() {
        this.sitCounter = 1;
//        if (!this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
//                    new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
//        }
        this.getNavigator().clearPath();
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //sitting animation
        {
            this.sitCounter = 1;
            this.getNavigator().clearPath();
        }
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && !getIsTamed() && !getIsAdult() && stack.getItem() == Items.CAKE) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            this.temper += 2;
            this.setHealth(getMaxHealth());
            if (!this.world.isRemote && !getIsAdult() && !getIsTamed() && this.temper >= 10) {
                MoCTools.tameWithName(player, this);
            }
            return true;
        }

        if (!stack.isEmpty() &&!getIsTamed() && !getIsAdult() && stack.getItem() == MoCItems.SUGARLUMP) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            this.temper += 1;
            this.setHealth(getMaxHealth());
            if (!this.world.isRemote && !getIsAdult() && !getIsTamed() && this.temper >= 10) {
                setTamed(true);
                MoCTools.tameWithName(player, this);
            }
            return true;
        }

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() == 0 && stack.getItem() == MoCItems.ELEPHANTHARNESS) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setArmorType((byte) 1);
            return true;
        }

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() >= 1 && getStorage() == 0
                && stack.getItem() == MoCItems.ELEPHANTCHEST) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            //entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setStorage((byte) 1);
            return true;
        }
        // second storage unit
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() >= 1 && getStorage() == 1
                && stack.getItem() == MoCItems.ELEPHANTCHEST) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setStorage((byte) 2);
            return true;
        }
        // third storage unit for small mammoths
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && (getSubType() == 3) && getArmorType() >= 1 && getStorage() == 2
                && stack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setStorage((byte) 3);
            return true;
        }
        // fourth storage unit for small mammoths
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && (getSubType() == 3) && getArmorType() >= 1 && getStorage() == 3
                && stack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setStorage((byte) 4);
            return true;
        }

        //giving a garment to an indian elephant with an harness will make it pretty
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() == 1 && getSubType() == 2
                && stack.getItem() == MoCItems.ELEPHANTGARMENT) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setArmorType((byte) 2);
            setType(5);
            return true;
        }

        //giving a howdah to a pretty indian elephant with a garment will attach the howdah
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() == 2 && getSubType() == 5
                && stack.getItem() == MoCItems.ELEPHANTHOWDAH) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setArmorType((byte) 3);
            return true;
        }

        //giving a platform to a ? mammoth with harness will attach the platform
        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && getArmorType() == 1 && getSubType() == 4
                && stack.getItem() == MoCItems.MAMMOTHPLATFORM) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            setArmorType((byte) 3);
            return true;
        }

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && stack.getItem() == MoCItems.TUSKSWOOD) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            dropTusks();
            this.tuskUses = (byte) stack.getDamage();
            setTusks((byte) 1);
            return true;
        }

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && stack.getItem() == MoCItems.TUSKSIRON) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            dropTusks();
            this.tuskUses = (byte) stack.getDamage();
            setTusks((byte) 2);
            return true;
        }

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && stack.getItem() == MoCItems.TUSKSDIAMOND) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            dropTusks();
            this.tuskUses = (byte) stack.getDamage();
            setTusks((byte) 3);
            return true;
        }

        if (player.isSneaking()) {
            initChest();
            if (getStorage() == 1) {
                if (!this.world.isRemote) {
                    player.displayGUIChest(this.localelephantchest);
                }
                return true;
            }
            if (getStorage() == 2) {
                InventoryLargeChest doubleChest = new InventoryLargeChest("ElephantChest", this.localelephantchest, this.localelephantchest2);
                if (!this.world.isRemote) {
                    player.displayGUIChest(doubleChest);
                }
                return true;
            }
            if (getStorage() == 3) {
                InventoryLargeChest doubleChest = new InventoryLargeChest("ElephantChest", this.localelephantchest, this.localelephantchest2);
                InventoryLargeChest tripleChest = new InventoryLargeChest("ElephantChest", doubleChest, this.localelephantchest3);
                if (!this.world.isRemote) {
                    player.displayGUIChest(tripleChest);
                }
                return true;
            }
            if (getStorage() == 4) {
                InventoryLargeChest doubleChest = new InventoryLargeChest("ElephantChest", this.localelephantchest, this.localelephantchest2);
                InventoryLargeChest doubleChestb = new InventoryLargeChest("ElephantChest", this.localelephantchest3, this.localelephantchest4);
                InventoryLargeChest fourChest = new InventoryLargeChest("ElephantChest", doubleChest, doubleChestb);
                if (!this.world.isRemote) {
                    player.displayGUIChest(fourChest);
                }
                return true;
            }

        }
        if (!stack.isEmpty()
                && getTusks() > 0 
                && ((stack.getItem() == Items.DIAMOND_PICKAXE) || (stack.getItem() == Items.WOODEN_PICKAXE)
                        || (stack.getItem() == Items.STONE_PICKAXE) || (stack.getItem() == Items.IRON_PICKAXE) || (stack.getItem() == Items.GOLDEN_PICKAXE))) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            dropTusks();
            return true;
        }

        if (getIsTamed() && getIsAdult() && getArmorType() >= 1 && this.sitCounter != 0) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                this.sitCounter = 0;
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    private void initChest() {
        if (getStorage() > 0 && this.localelephantchest == null) {
            this.localelephantchest = new MoCAnimalChest("ElephantChest", 18);
        }

        if (getStorage() > 1 && this.localelephantchest2 == null) {
            this.localelephantchest2 = new MoCAnimalChest("ElephantChest", 18);
        }

        if (getStorage() > 2 && this.localelephantchest3 == null) {
            this.localelephantchest3 = new MoCAnimalChest("ElephantChest", 9);
        }

        if (getStorage() > 3 && this.localelephantchest4 == null) {
            this.localelephantchest4 = new MoCAnimalChest("ElephantChest", 9);
        }
    }*/

    /**
     * Drops tusks, makes sound
     */
    private void dropTusks() {
        if (this.world.isRemote) {
            return;
        }
        int i = getTusks();

        if (i == 1) {
            ItemEntity entityitem =
                    new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(MoCItems.TUSKSWOOD, 1));
            entityitem.getItem().setDamage(this.tuskUses);
            entityitem.setPickupDelay(10);
            this.world.addEntity(entityitem);
        }
        if (i == 2) {
            ItemEntity entityitem =
                    new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(MoCItems.TUSKSIRON, 1));
            entityitem.getItem().setDamage(this.tuskUses);
            entityitem.setPickupDelay(10);
            this.world.addEntity(entityitem);
        }
        if (i == 3) {
            ItemEntity entityitem =
                    new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(MoCItems.TUSKSDIAMOND, 1));
            entityitem.getItem().setDamage(this.tuskUses);
            entityitem.setPickupDelay(10);
            this.world.addEntity(entityitem);
        }
        setTusks((byte) 0);
        this.tuskUses = 0;
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    /*@Override
    public boolean updateMount() {
        return getIsTamed();
    }
    */
    /*@Override
    public boolean forceUpdates() {
        return getIsTamed();
    }*/

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());
        Biome currentbiome = MoCTools.Biomekind(this.world, pos);

        if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
            setType(3 + this.rand.nextInt(2));
            return true;
        }
        if (BiomeDictionary.hasType(currentbiome, Type.SANDY)) {
            setType(1);
            return true;
        }

        if (BiomeDictionary.hasType(currentbiome, Type.JUNGLE)) {
            setType(2);
            return true;
        }

        if (BiomeDictionary.hasType(currentbiome, Type.PLAINS) || BiomeDictionary.hasType(currentbiome, Type.FOREST)) {
            setType(1 + this.rand.nextInt(2));
            return true;
        }

        return false;
    }

    @Override
    public float getSizeFactor() {
        float sizeF = 1.25F;

        switch (getSubType()) {
            case 4:
                sizeF *= 1.2F;
                break;
            case 2:
            case 5:
                sizeF *= 0.80F;
                break;
        }

        if (!getIsAdult()) {
            sizeF = sizeF * (getEdad() * 0.01F);
        }
        return sizeF;
    }

    /*@Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setTusks(nbttagcompound.getInt("Tusks"));
        setArmorType(nbttagcompound.getInt("Harness"));
        setStorage(nbttagcompound.getInt("Storage"));
        this.tuskUses = nbttagcompound.getByte("TuskUses");
        if (getStorage() > 0) {
            ListNBT nbttaglist = nbttagcompound.getList("Items", 10);
            this.localelephantchest = new MoCAnimalChest("ElephantChest", 18);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localelephantchest.getSizeInventory()) {
                    this.localelephantchest.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }
        if (getStorage() >= 2) {
            ListNBT nbttaglist = nbttagcompound.getList("Items2", 10);
            this.localelephantchest2 = new MoCAnimalChest("ElephantChest", 18);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localelephantchest2.getSizeInventory()) {
                    this.localelephantchest2.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }

        if (getStorage() >= 3) {
            ListNBT nbttaglist = nbttagcompound.getList("Items3", 10);
            this.localelephantchest3 = new MoCAnimalChest("ElephantChest", 9);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localelephantchest3.getSizeInventory()) {
                    this.localelephantchest3.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }
        if (getStorage() >= 4) {
            ListNBT nbttaglist = nbttagcompound.getList("Items4", 10);
            this.localelephantchest4 = new MoCAnimalChest("ElephantChest", 9);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localelephantchest4.getSizeInventory()) {
                    this.localelephantchest4.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }
    }*/

    /*@Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putInt("Tusks", getTusks());
        nbttagcompound.putInt("Harness", getArmorType());
        nbttagcompound.putInt("Storage", getStorage());
        nbttagcompound.putByte("TuskUses", this.tuskUses);

        if (getStorage() > 0 && this.localelephantchest != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localelephantchest.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest.getStackInSlot(i);
                if (this.localstack != null && !this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items", nbttaglist);
        }

        if (getStorage() >= 2 && this.localelephantchest2 != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localelephantchest2.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest2.getStackInSlot(i);
                if (this.localstack != null && !this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items2", nbttaglist);
        }

        if (getStorage() >= 3 && this.localelephantchest3 != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localelephantchest3.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest3.getStackInSlot(i);
                if (this.localstack != null && !this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items3", nbttaglist);
        }

        if (getStorage() >= 4 && this.localelephantchest4 != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localelephantchest4.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest4.getStackInSlot(i);
                if (this.localstack != null && !this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items4", nbttaglist);
        }
    }*/

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() == Items.BAKED_POTATO || stack.getItem() == Items.BREAD || stack.getItem() == MoCItems.HAYSTACK);
    }

    @Override
    public boolean isMovementCeased() {
        return (this.isBeingRidden()) || this.sitCounter != 0;
    }

    @Override
    public void Riding() {
        if ((this.isBeingRidden()) && (this.getRidingEntity() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(1.0D, 0.0D, 1.0D));
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = list.get(i);
                    if (!entity.isAlive()) {
                        continue;
                    }
                    entity.onCollideWithPlayer(entityplayer);
                }

            }
            if (entityplayer.isSneaking()) {
                if (!this.world.isRemote) {
                    if (this.sitCounter == 0) {
                        sit();
                    }
                    if (this.sitCounter >= 50) {
                        entityplayer.stopRiding();
                    }

                }
            }
        }
    }

    @Override
    public boolean canBePushed() {
        return !this.isBeingRidden();
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void updatePassenger(Entity passenger) {

        double dist = (1.0D);
        switch (getSubType()) {
            case 1:
            case 3:

                dist = 0.8D;
                break;
            case 2:
            case 5:

                dist = 0.1D;
                break;
            case 4:
                dist = 1.2D;

                break;
        }

        double newPosX = this.getPosX() - (dist * Math.cos((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.sin((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public double getMountedYOffset() {
        double yOff = 0F;
        boolean sit = (this.sitCounter != 0);

        switch (getSubType()) {
            case 1:
                yOff = 0.55D;
                if (sit) {
                    yOff = -0.05D;
                }
                break;
            case 3:
                yOff = 0.55D;
                if (sit) {
                    yOff = -0.05D;
                }
                break;
            case 2:
            case 5:
                yOff = -0.17D;
                if (sit) {
                    yOff = -0.5D;
                }
                break;
            case 4:
                yOff = 1.2D;
                if (sit) {
                    yOff = 0.45D;
                }
                break;
        }
        return yOff + (this.getHeight() * 0.75D);
    }

    /**
     * Had to set to false to avoid damage due to the collision boxes
     */
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_ELEPHANT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_ELEPHANT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!getIsAdult() && getEdad() < 80) {
            return MoCSoundEvents.ENTITY_ELEPHANT_AMBIENT_BABY;
        }
        return MoCSoundEvents.ENTITY_ELEPHANT_AMBIENT;
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason reason) {
        return (MoCreatures.entityMap.get(this.getType()).getFrequency() > 0) && getCanSpawnHereCreature() && getCanSpawnHereLiving();
    }

    /*@Override
    public void dropMyStuff() {
        if (!this.world.isRemote) {
            dropTusks();
            //dropSaddle(this, world);
            if (getStorage() > 0) {
                if (getStorage() > 0) {
                    MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.ELEPHANTCHEST, 1));
                    if (this.localelephantchest != null) {
                        MoCTools.dropInventory(this, this.localelephantchest);
                    }

                }
                if (getStorage() >= 2) {
                    if (this.localelephantchest2 != null) {
                        MoCTools.dropInventory(this, this.localelephantchest2);
                    }
                    MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.ELEPHANTCHEST, 1));
                }
                if (getStorage() >= 3) {
                    if (this.localelephantchest3 != null) {
                        MoCTools.dropInventory(this, this.localelephantchest3);
                    }
                    MoCTools.dropCustomItem(this, this.world, new ItemStack(Blocks.CHEST, 1));
                }
                if (getStorage() >= 4) {
                    if (this.localelephantchest4 != null) {
                        MoCTools.dropInventory(this, this.localelephantchest4);
                    }
                    MoCTools.dropCustomItem(this, this.world, new ItemStack(Blocks.CHEST, 1));
                }
                setStorage((byte) 0);
            }
            dropArmor();
        }
    }*/

    @Override
    public void dropArmor() {
        if (this.world.isRemote) {
            return;
        }
        if (getArmorType() >= 1) {
            MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.ELEPHANTHARNESS, 1));
        }
        if (getSubType() == 5 && getArmorType() >= 2) {

            MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.ELEPHANTGARMENT, 1));
            if (getArmorType() == 3) {
                MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.ELEPHANTHOWDAH, 1));
            }
            setType(2);
        }
        if (getSubType() == 4 && getArmorType() == 3) {
            MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.MAMMOTHPLATFORM, 1));
        }
        setArmorType((byte) 0);

    }

    @Override
    public int nameYOffset() {
        int yOff = (int) ((100 / getEdad()) * (getSizeFactor() * -110));
        if (getIsAdult()) {
            yOff = (int) (getSizeFactor() * -110);
        }
        if (sitCounter != 0)
            yOff += 25;
        return yOff;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if ((entity != null && getIsTamed() && entity instanceof PlayerEntity) || !(entity instanceof LivingEntity) ) {
                return false;
            }
            if (this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if (entity != this && super.shouldAttackPlayers()) {
                setAttackTarget((LivingEntity) entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onLivingFall(float f, float f1) {
        int i = (int) Math.ceil(f - 3F);
        if ((i > 0)) {
            i /= 2;
            if (i > 0) {
                attackEntityFrom(DamageSource.FALL, i);
            }
            if ((this.isBeingRidden()) && (i > 0)) {
                for (Entity entity : this.getRecursivePassengers())
                {
                    entity.attackEntityFrom(DamageSource.FALL, (float)i);
            }
            }
            BlockState iblockstate = this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosY() - 0.2D - (double)this.prevRotationYaw, this.getPosZ()));
            Block block = iblockstate.getBlock();

            if (iblockstate.getMaterial() != Material.AIR && !this.isSilent())
            {
                SoundType soundtype = block.getSoundType(iblockstate, world, new BlockPos(this.getPosX(), this.getPosY() - 0.2D - (double)this.prevRotationYaw, this.getPosZ()), this);
                this.world.playSound((PlayerEntity) null, this.getPosX(), this.getPosY(), this.getPosZ(), soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult() || getEdad() > 80 || getIsTamed();
    }
}
