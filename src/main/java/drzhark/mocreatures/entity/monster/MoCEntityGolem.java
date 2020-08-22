package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import java.util.ArrayList;
import java.util.List;

public class MoCEntityGolem extends MoCEntityMob implements IEntityAdditionalSpawnData {

    public int tcounter;
    public MoCEntityThrowableRock tempRock;
    private byte golemCubes[];
    private int dCounter = 0;
    private int sCounter;
    private static final DataParameter<Integer> GOLEM_STATE = EntityDataManager.<Integer>createKey(MoCEntityGolem.class, DataSerializers.VARINT);

    public MoCEntityGolem(EntityType<? extends MoCEntityGolem> type, World world) {
        super(type, world);
        this.texture = "golemt.png";
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes()
                .func_233815_a_(Attributes.MAX_HEALTH, 50.0D)
                .func_233815_a_(Attributes.MOVEMENT_SPEED, 0.25D)
                .func_233815_a_(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public void writeSpawnData(PacketBuffer data) {
        for (int i = 0; i < 23; i++) {
            data.writeByte(this.golemCubes[i]);
        }
    }

    @Override
    public void readSpawnData(PacketBuffer data) {
        for (int i = 0; i < 23; i++) {
            this.golemCubes[i] = data.readByte();
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        initGolemCubes();
        this.dataManager.register(GOLEM_STATE, 0); // 0 spawned / 1 summoning rocks /2 has enemy /3 half life (harder) /4 dying
    }

    public int getGolemState() {
        return this.dataManager.get(GOLEM_STATE);
    }

    public void setGolemState(int i) {
        this.dataManager.set(GOLEM_STATE, i);
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            if (getGolemState() == 0) //just spawned
            {
                PlayerEntity entityplayer1 = this.world.getClosestPlayer(this, 8D);
                if (entityplayer1 != null) {
                    setGolemState(1); //activated
                }
            }

            if (getGolemState() == 1 && !isMissingCubes())//getAttackTarget() != null)
            {
                setGolemState(2); //is complete
            }

            if (getGolemState() > 2 && getGolemState() != 4 && this.getAttackTarget() == null) {
                setGolemState(1);
            }

            if (getGolemState() > 1 && this.getAttackTarget() != null && this.rand.nextInt(20) == 0) {
                if (getHealth() >= 30) {
                    setGolemState(2);
                }
                if (getHealth() < 30 && getHealth() >= 10) {
                    setGolemState(3); //more dangerous
                }
                if (getHealth() < 10) {
                    setGolemState(4); //dying
                }
            }

            if (getGolemState() != 0 && getGolemState() != 4 && isMissingCubes()) {

                int freq = 21 - (getGolemState() * this.world.getDifficulty().getId());
                if (getGolemState() == 1) {
                    freq = 10;
                }
                if (this.rand.nextInt(freq) == 0) {
                    acquireRock(2);
                }
            }

            if (getGolemState() == 4) {
                this.getNavigator().clearPath();
                this.dCounter++;

                if (this.dCounter < 80 && this.rand.nextInt(3) == 0) {
                    acquireRock(4);
                }

                if (this.dCounter == 120) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_DYING, 3F);
//                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
//                            new TargetPoint(this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
                }

                if (this.dCounter > 140) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_EXPLODE, 3F);
                    destroyGolem();
                }
            }
        }

        if (this.tcounter == 0 && this.getAttackTarget() != null && this.canShoot()) {
            float distanceToTarget = this.getDistance(this.getAttackTarget());
            if (distanceToTarget > 6F) {
                this.tcounter = 1;
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
//                        new TargetPoint(this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            }

        }
        if (this.tcounter != 0) {
            if (this.tcounter++ == 70 && this.getAttackTarget() != null && this.canShoot() && this.getAttackTarget().isAlive()
                    && this.canEntityBeSeen(this.getAttackTarget())) {
                shootBlock(this.getAttackTarget());
            } else if (this.tcounter > 90) {
                this.tcounter = 0;
            }

        }

        if (/*MoCreatures.proxy.getParticleFX() > 0 &&*/ getGolemState() == 4 && this.sCounter > 0) {
            for (int i = 0; i < 10; i++) {
                this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), this.rand.nextGaussian(),
                        this.rand.nextGaussian(), this.rand.nextGaussian());
            }
        }
    }

    private void destroyGolem() {
        List<Integer> usedBlocks = usedCubes();
        if ((!usedBlocks.isEmpty()) && (MoCTools.mobGriefing(this.world)) && (MoCConfig.COMMON_CONFIG.GENERAL.monsterSettings.golemDestroyBlocks.get())) {
            for (Integer usedBlock : usedBlocks) {
                Block block = Block.getStateById(generateBlock(this.golemCubes[usedBlock])).getBlock();
                ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(block, 1));
                entityitem.setPickupDelay(10);
                this.world.addEntity(entityitem);
            }
        }
        this.remove();
    }

    @Override
    public boolean isMovementCeased() {
        return getGolemState() == 4;
    }

    protected void acquireRock(int type) {
        //finds a missing rock spot in its body
        //looks for a random rock around it
        BlockPos myTRockPos = MoCTools.getRandomBlockPos(this, 24D);

        boolean canDestroyBlocks = MoCTools.mobGriefing(this.world) && MoCConfig.COMMON_CONFIG.GENERAL.monsterSettings.golemDestroyBlocks.get();
        BlockState blockstate = this.world.getBlockState(myTRockPos);
        if (Block.getStateId(blockstate) == 0) {
            return;
        } //air blocks
        BlockEvent.BreakEvent event = null;
        if (!this.world.isRemote) {
            event =
                    new BlockEvent.BreakEvent(this.world, myTRockPos, blockstate, FakePlayerFactory.get((ServerWorld) this.world,
                            MoCreatures.MOCFAKEPLAYER));
        }
        if (canDestroyBlocks && event != null && !event.isCanceled()) {
            //destroys the original rock
            this.world.setBlockState(myTRockPos, Blocks.AIR.getDefaultState());
        } else {
            //couldn't destroy the original rock
            canDestroyBlocks = false;
        }

        if (!canDestroyBlocks) //make cheap rocks
        {
            blockstate = Block.getStateById(returnRandomCheapBlock());
        }

        MoCEntityThrowableRock trock = new MoCEntityThrowableRock(this.world, this, myTRockPos.getX(), myTRockPos.getY() + 1, myTRockPos.getZ());//, false, true);
        trock.setState(blockstate);
        trock.setBehavior(type);//so the rock: 2 follows the EntityGolem  or 3 - gets around the golem

        //spawns the new TRock
        this.world.addEntity(trock);
    }

    /**
     * returns a random block when the golem is unable to break blocks
     *
     * @return
     */
    private int returnRandomCheapBlock() {
        int i = this.rand.nextInt(4);
        switch (i) {
            case 0:
                return 3; //dirt
            case 1:
                return 4; //cobblestone
            case 2:
                return 5; //wood
            case 3:
                return 79; //ice
        }
        return 3;
    }

    /**
     * When the golem receives the rock, called from within EntityTRock
     *
     * @param state - Blockstate
     */
    public void receiveRock(BlockState state) {
        if (!this.world.isRemote) {
            byte myBlock = translateOre(Block.getStateId(state));
            byte slot = (byte) getRandomCubeAdj();
            if ((slot != -1) && (slot < 23) && (myBlock != -1) && getGolemState() != 4) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_ATTACH, 3F);
                int h = this.world.getDifficulty().getId();
                this.setHealth(getHealth() + h);
                if (getHealth() > getMaxHealth()) {
                    this.setHealth(getMaxHealth());
                }
                saveGolemCube(slot, myBlock);
            } else {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_HURT, 2F);
                if ((MoCTools.mobGriefing(this.world)) && (MoCConfig.COMMON_CONFIG.GENERAL.monsterSettings.golemDestroyBlocks.get())) {
                    ItemEntity entityitem =
                            new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(state.getBlock(), 1 /*, state.getBlock()*/));
                    entityitem.setPickupDelay(10);
//                    entityitem.setAgeToCreativeDespawnTime(); // 4800
                }
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //rock throwing animation
        {
            this.tcounter = 1;
        }
        if (animationType == 1) //smoking animation
        {
            this.sCounter = 1;
        }
    }

    /**
     * Shoots one block to the target
     * @param entity
     */
    private void shootBlock(Entity entity) {
        if (entity == null) {
            return;
        }
        List<Integer> armBlocks = new ArrayList<>();

        for (int i = 9; i < 15; i++) {
            if (this.golemCubes[i] != 30) {
                armBlocks.add(i);
            }
        }
        if (armBlocks.isEmpty()) {
            return;
        }

        int j = this.rand.nextInt(armBlocks.size());
        int i = armBlocks.get(j);
        int x = i;

        if (i == 9 || i == 12) {
            if (this.golemCubes[i + 2] != 30) {
                x = i + 2;
            } else if (this.golemCubes[i + 1] != 30) {
                x = i + 1;
            }
        }

        if (i == 10 || i == 13) {
            if (this.golemCubes[i + 1] != 30) {
                x = i + 1;
            }
        }
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_SHOOT, 3F);
        MoCTools.throwStone(this, entity, Block.getStateById(generateBlock(this.golemCubes[x])), 10D, 0.4D);
        saveGolemCube((byte) x, (byte) 30);
        this.tcounter = 0;
    }

    private boolean canShoot() {
        int x = 0;
        for (byte i = 9; i < 15; i++) {
            if (this.golemCubes[i] != 30) {
                x++;
            }
        }
        return (x != 0) && getGolemState() != 4 && getGolemState() != 1;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (getGolemState() == 4) {
            return false;
        }

        List<Integer> missingChestBlocks = missingChestCubes();
        boolean uncoveredChest = (missingChestBlocks.size() == 4);
        if (!openChest() && !uncoveredChest && getGolemState() != 1) {
            int j = this.world.getDifficulty().getId();
            if (!this.world.isRemote && this.rand.nextInt(j) == 0) {
                destroyRandomGolemCube();
            } else {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_HURT, 2F);
            }

            Entity entity = damagesource.getTrueSource();
            if ((entity != this) && (this.world.getDifficulty().getId() > 0) && entity instanceof LivingEntity) {
                this.setAttackTarget((LivingEntity) entity);
                return true;
            } else {
                return false;
            }
        }
        if (i > 5) {
            i = 5; //so you can't hit a Golem too hard
        }
        if (getGolemState() != 1 && super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if ((entity != this) && (this.world.getDifficulty().getId() > 0) && entity instanceof LivingEntity) {
                this.setAttackTarget((LivingEntity) entity);
                return true;
            } else {
                return false;
            }
        }
        if (getGolemState() == 1) {
            Entity entity = damagesource.getTrueSource();
            if ((entity != this) && (this.world.getDifficulty().getId() > 0) && entity instanceof LivingEntity) {
                this.setAttackTarget((LivingEntity) entity);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Destroys a random cube, with the proper check for extremities and spawns
     * a block in world
     */
    private void destroyRandomGolemCube() {
        int i = getRandomUsedCube();
        if (i == 4) {
            return;
            //do not destroy the valuable back cube
        }

        int x = i;
        if (i == 10 || i == 13 || i == 16 || i == 19) {
            if (this.golemCubes[i + 1] != 30) {
                x = i + 1;
            }

        }

        if (i == 9 || i == 12 || i == 15 || i == 18) {
            if (this.golemCubes[i + 2] != 30) {
                x = i + 2;
            } else if (this.golemCubes[i + 1] != 30) {
                x = i + 1;
            }
        }

        if (x != -1 && this.golemCubes[x] != 30) {
            Block block = Block.getStateById(generateBlock(this.golemCubes[x])).getBlock();
            saveGolemCube((byte) x, (byte) 30);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_HURT, 3F);
            if ((MoCTools.mobGriefing(this.world)) && (MoCConfig.COMMON_CONFIG.GENERAL.monsterSettings.golemDestroyBlocks.get())) {
                ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(block, 1/*, 0*/));
                entityitem.setPickupDelay(10);
                this.world.addEntity(entityitem);
            }
        }
    }

    @Override
    public float getAdjustedYOffset() {
        if (this.golemCubes[17] != 30 || this.golemCubes[20] != 30) {
            //has feet
            return 0F;
        }
        if (this.golemCubes[16] != 30 || this.golemCubes[19] != 30) {
            //has knees but not feet
            return 0.4F;
        }
        if (this.golemCubes[15] != 30 || this.golemCubes[18] != 30) {
            //has thighs but not knees or feet
            return 0.7F;
        }

        if (this.golemCubes[1] != 30 || this.golemCubes[3] != 30) {
            //has lower chest
            return 0.8F;
        }
        //missing everything
        return 1.45F;
    }

    /**
     * Stretches the model to that size
     */
    @Override
    public float getSizeFactor() {
        return 1.8F;
    }

    /**
     * @param i = slot
     * @return the block type stored in that slot. 30 = empty
     */
    public byte getBlockText(int i) {
        return this.golemCubes[i];
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putInt("golemState", getGolemState());
        ListNBT cubeLists = new ListNBT();

        for (int i = 0; i < 23; i++) {
            CompoundNBT nbttag = new CompoundNBT();
            nbttag.putByte("Slot", this.golemCubes[i]);
            cubeLists.add(nbttag);
        }
        nbttagcompound.put("GolemBlocks", cubeLists);
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setGolemState(nbttagcompound.getInt("golemState"));
        ListNBT nbttaglist = nbttagcompound.getList("GolemBlocks", 10);
        for (int i = 0; i < 23; i++) {
            CompoundNBT var4 = nbttaglist.getCompound(i);
            this.golemCubes[i] = var4.getByte("Slot");
        }
    }

    /**
     * Initializes the goleCubes array
     */
    private void initGolemCubes() {
        this.golemCubes = new byte[23];

        for (int i = 0; i < 23; i++) {
            this.golemCubes[i] = 30;
        }

        int j = this.rand.nextInt(4);
        switch (j) {
            case 0:
                j = 7;
                break;
            case 1:
                j = 11;
                break;
            case 2:
                j = 15;
                break;
            case 3:
                j = 21;
                break;
        }
        saveGolemCube((byte) 4, (byte) j);
    }

    /**
     * Saves the type of Cube(value) on the given 'slot' if server, then sends a
     * packet to the clients
     *
     * @param slot
     * @param value
     */
    public void saveGolemCube(byte slot, byte value) {
        this.golemCubes[slot] = value;
//        if (!this.world.isRemote && MoCreatures.proxy.worldInitDone) // Fixes CMS initialization during world load
//        {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageTwoBytes(this.getEntityId(), slot, value), new TargetPoint(
//                    this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//        }
    }

    /**
     * returns a list of the empty blocks
     *
     * @return
     */
    private List<Integer> missingCubes() {
        List<Integer> emptyBlocks = new ArrayList<Integer>();

        for (int i = 0; i < 23; i++) {
            if (this.golemCubes[i] == 30) {
                emptyBlocks.add(i);
            }
        }
        return emptyBlocks;
    }

    /**
     * Returns true if is 'missing' any cube, false if it's full
     *
     * @return
     */
    public boolean isMissingCubes() {
        for (int i = 0; i < 23; i++) {
            if (this.golemCubes[i] == 30) {
                return true;
            }
        }
        return false;
    }

    private List<Integer> missingChestCubes() {
        List<Integer> emptyChestBlocks = new ArrayList<Integer>();

        for (int i = 0; i < 4; i++) {
            if (this.golemCubes[i] == 30) {
                emptyChestBlocks.add(i);
            }
        }
        return emptyChestBlocks;
    }

    /**
     * returns a list of the used block spots
     *
     * @return
     */
    private List<Integer> usedCubes() {
        List<Integer> usedBlocks = new ArrayList<Integer>();

        for (int i = 0; i < 23; i++) {
            if (this.golemCubes[i] != 30) {
                usedBlocks.add(i);
            }
        }
        return usedBlocks;
    }

    /**
     * Returns a random used cube position if the golem is empty, returns -1
     *
     * @return
     */
    private int getRandomUsedCube() {
        List<Integer> usedBlocks = usedCubes();
        if (usedBlocks.isEmpty()) {
            return -1;
        }
        int randomEmptyBlock = this.rand.nextInt(usedBlocks.size());
        return usedBlocks.get(randomEmptyBlock);
    }

    /**
     * Returns a random empty cube position if the golem is full, returns -1
     *
     * @return
     */
    private int getRandomMissingCube() {
        //first it makes sure it has the four chest cubes
        List<Integer> emptyChestBlocks = missingChestCubes();
        if (!emptyChestBlocks.isEmpty()) {
            int randomEmptyBlock = this.rand.nextInt(emptyChestBlocks.size());
            return emptyChestBlocks.get(randomEmptyBlock);
        }

        //otherwise returns any other cube
        List<Integer> emptyBlocks = missingCubes();
        if (emptyBlocks.isEmpty()) {
            return -1;
        }
        int randomEmptyBlock = this.rand.nextInt(emptyBlocks.size());
        return emptyBlocks.get(randomEmptyBlock);
    }

    /**
     * returns the position of the cube to be added, contains logic for the
     * extremities
     *
     * @return
     */
    private int getRandomCubeAdj() {
        int i = getRandomMissingCube();

        if (i == 10 || i == 13 || i == 16 || i == 19) {
            if (this.golemCubes[i - 1] == 30) {
                return i - 1;
            } else {
                saveGolemCube((byte) i, this.golemCubes[i - 1]);
                return i - 1;
            }
        }

        if (i == 11 || i == 14 || i == 17 || i == 20) {
            if (this.golemCubes[i - 2] == 30 && this.golemCubes[i - 1] == 30) {
                return i - 2;
            }
            if (this.golemCubes[i - 1] == 30) {
                saveGolemCube((byte) (i - 1), this.golemCubes[i - 2]);
                return i - 2;
            } else {
                saveGolemCube((byte) i, this.golemCubes[i - 1]);
                saveGolemCube((byte) (i - 1), this.golemCubes[i - 2]);
                return i - 2;
            }
        }
        return i;
    }

    @Override
    public float rollRotationOffset() {
        int leftLeg = 0;
        int rightLeg = 0;
        if (this.golemCubes[15] != 30) {
            leftLeg++;
        }
        if (this.golemCubes[16] != 30) {
            leftLeg++;
        }
        if (this.golemCubes[17] != 30) {
            leftLeg++;
        }
        if (this.golemCubes[18] != 30) {
            rightLeg++;
        }
        if (this.golemCubes[19] != 30) {
            rightLeg++;
        }
        if (this.golemCubes[20] != 30) {
            rightLeg++;
        }
        return (leftLeg - rightLeg) * 10F;
    }

    /**
     * The chest opens when the Golem is missing cubes and the summoned blocks
     * are close
     *
     * @return
     */
    public boolean openChest() {
        if (isMissingCubes()) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(2D, 2D, 2D));

//            for (int i = 0; i < list.size(); i++) {
//                Entity entity1 = list.get(i);
//                if (entity1 instanceof MoCEntityThrowableRock) {
//                    if (MoCreatures.proxy.getParticleFX() > 0) {
//                        MoCreatures.proxy.VacuumFX(this);
//                    }
//                    return true;
//                }
//            }
        }
        return false;
    }

    /**
     * Converts the world block into the golem block texture if not found,
     * returns -1
     *
     * @param blockType
     * @return
     */
    private byte translateOre(int blockType) {
        switch (blockType) {
            case 0:
                return 0;
            case 1:
                return 0;
            case 18:
                return 10; //leaves
            case 2:
            case 3:
                return 1; //dirt, grass
            case 4:
            case 48:
                return 2; //cobblestones
            case 5:
                return 3;
            case 12:
                return 4;
            case 13:
                return 5;
            case 16:
            case 21:
            case 56:
            case 74:
            case 73:
                return 24; //all ores are transformed into diamond ore
            case 14:
            case 41:
                return 7; //ore gold and block gold = block gold
            case 15:
            case 42:
                return 11;//iron ore and blocks = block iron
            case 57:
                return 15; //block diamond
            case 17:
                return 6; //wood
            case 20:
                return 8;
            case 22:
            case 35: //lapis and cloths
                return 9;
            case 45:
                return 12; //brick
            case 49:
                return 14; //obsidian
            case 58:
                return 16; //workbench
            case 61:
            case 62:
                return 17; //stonebench
            case 78:
            case 79:
                return 18; //ice
            case 81:
                return 19; //cactus
            case 82:
                return 20; //clay
            case 86:
            case 91:
            case 103:
                return 22; //pumpkin pumpkin lantern melon
            case 87:
                return 23; //netherrack
            case 89:
                return 25; //glowstone
            case 98:
                return 26; //stonebrick
            case 112:
                return 27; //netherbrick
            case 129:
            case 133:
                return 21; //emeralds
            default:
                return -1;
        }
    }

    /**
     * Provides the blockID originated from the golem's block
     *
     * @param golemBlock
     * @return
     */
    private int generateBlock(int golemBlock) {
        switch (golemBlock) {
            case 0:
                return 1;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 5;
            case 4:
                return 12;
            case 5:
                return 13;
            case 6:
                return 17;
            case 7:
                return 41;
            case 8:
                return 20;
            case 9:
                return 35;
            case 10:
                return 18;
            case 11:
                return 42;
            case 12:
                return 45;
            case 13: //unused
                return 2;
            case 14:
                return 49;
            case 15:
                return 57;
            case 16:
                return 58;
            case 17:
                return 51;
            case 18:
                return 79;
            case 19:
                return 81;
            case 20:
                return 82;
            case 21:
                return 133;
            case 22:
                return 86;
            case 23:
                return 87;
            case 24:
                return 56;
            case 25:
                return 89;
            case 26:
                return 98;
            case 27:
                return 112;
            default:
                return 2;
        }
    }

    private int countLegBlocks() {
        int x = 0;
        for (byte i = 15; i < 21; i++) {
            if (this.golemCubes[i] != 30) {
                x++;
            }
        }
        return x;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.15F * (countLegBlocks() / 6F);
    }

    /**
     * Used for the power texture used on the golem
     *
     * @return
     */
    public ResourceLocation getEffectTexture() {
        switch (getGolemState()) {
            case 1:
                return MoCreatures.getTexture("golemeffect1.png");
            case 2:
                return MoCreatures.getTexture("golemeffect2.png");
            case 3:
                return MoCreatures.getTexture("golemeffect3.png");
            case 4:
                return MoCreatures.getTexture("golemeffect4.png");
            default:
                return null;
        }
    }

    /**
     * Used for the particle FX
     *
     * @param i
     * @return
     */
    public float colorFX(int i) {
        switch (getGolemState()) {
            case 1:
                if (i == 1) {
                    return 65F / 255F;
                }
                if (i == 2) {
                    return 157F / 255F;
                }
                if (i == 3) {
                    return 254F / 255F;
                }
            case 2:
                if (i == 1) {
                    return 244F / 255F;
                }
                if (i == 2) {
                    return 248F / 255F;
                }
                if (i == 3) {
                    return 36F / 255F;
                }
            case 3:
                if (i == 1) {
                    return 255F / 255F;
                }
                if (i == 2) {
                    return 154F / 255F;
                }
                if (i == 3) {
                    return 21F / 255F;
                }
            case 4:
                if (i == 1) {
                    return 248F / 255F;
                }
                if (i == 2) {
                    return 10F / 255F;
                }
                if (i == 3) {
                    return 10F / 255F;
                }
        }
        return 0;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(MoCSoundEvents.ENTITY_GOLEM_WALK, 1.0F, 1.0F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_GOLEM_AMBIENT;
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason reason) {
        return (super.canSpawn(worldIn, reason)
                && this.world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY()), MathHelper
                        .floor(this.getPosZ()))) && (this.getPosY() > 50D));
    }
}
