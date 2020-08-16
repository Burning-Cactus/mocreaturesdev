package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.registry.MoCItems;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

public class MoCEntityTameableAquatic extends MoCEntityAquatic implements IMoCTameable {

    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(MoCEntityTameableAquatic.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    protected static final DataParameter<Integer> PET_ID = EntityDataManager.<Integer>createKey(MoCEntityTameableAquatic.class, DataSerializers.VARINT);
    protected static final DataParameter<Boolean> TAMED = EntityDataManager.<Boolean>createKey(MoCEntityTameableAquatic.class, DataSerializers.BOOLEAN);

    private boolean hasEaten;
    private int gestationtime;
    
    public MoCEntityTameableAquatic(EntityType<? extends MoCEntityTameableAquatic> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
        this.dataManager.register(PET_ID, -1);
        this.dataManager.register(TAMED, false);
    }

    @Override
    public int getOwnerPetId() {
        return this.dataManager.get(PET_ID);
    }

    @Override
    public void setOwnerPetId(int i) {
        this.dataManager.set(PET_ID, i);
    }

    @Nullable
    public UUID getOwnerId() {
        return this.dataManager.get(OWNER_UNIQUE_ID).isPresent() ? this.dataManager.get(OWNER_UNIQUE_ID).get() : null;
    }

    public void setOwnerId(@Nullable UUID uniqueId) {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(uniqueId));
    }

    @Override
    public void setTamed(boolean flag) {
        this.dataManager.set(TAMED, flag);
    }

    @Override
    public boolean getIsTamed() {
        return this.dataManager.get(TAMED);
    }

    @Override
    public boolean renderName() {
        return false;
    }

    @Override
    public int getSubType() {
        return 0;
    }

    @Nullable
    public LivingEntity getOwner() {
        try
        {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerByUuid(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getTrueSource();
        if ((this.isBeingRidden() && entity == this.getRidingEntity()) || (this.getRidingEntity() != null && entity == this.getRidingEntity())) {
            return false;
        }
        if (usesNewAI()) {
            return super.attackEntityFrom(damagesource, i);
        }

        //this avoids damage done by Players to a tamed creature that is not theirs
        if (MoCConfig.COMMON_CONFIG.OWNERSHIP.enableOwnership.get() && this.getOwnerId() != null
                && entity instanceof PlayerEntity && !((PlayerEntity) entity).getUniqueID().equals(this.getOwnerId())
                && !MoCTools.isThisPlayerAnOP((PlayerEntity) entity)) {
            return false;
        }

        return super.attackEntityFrom(damagesource, i);
    }

    private boolean checkOwnership(PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!this.getIsTamed() || MoCTools.isThisPlayerAnOP(player)) {
            return true;
        }

        if (this.getIsGhost() && !stack.isEmpty() && stack.getItem() == MoCItems.PETAMULET) {
            if (!this.world.isRemote) {
                // Remove when client is updated
                ((ServerPlayerEntity) player).sendAllContents(player.openContainer, player.openContainer.getInventory());
                player.sendMessage(new TranslationTextComponent(TextFormatting.RED + "This pet does not belong to you."), Util.DUMMY_UUID);
            }
            return false;
        }

        //if the player interacting is not the owner, do nothing!
        if (MoCConfig.COMMON_CONFIG.OWNERSHIP.enableOwnership.get() && this.getOwnerId() != null
                && !player.getUniqueID().equals(this.getOwnerId())) {
            player.sendMessage(new TranslationTextComponent(TextFormatting.RED + "This pet does not belong to you."), Util.DUMMY_UUID);
            return false;
        }

        return true;
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        return super.processInteract(player, hand);
    }*/

    // This should always run first for all tameable aquatics
    public Boolean processTameInteract(PlayerEntity player, Hand hand) {
        if (!this.checkOwnership(player, hand)) {
            return false;
        }

        final ItemStack stack = player.getHeldItem(hand);
        //before ownership check
        if (!stack.isEmpty() && getIsTamed() && ((stack.getItem() == MoCItems.SCROLLOFOWNER)) && MoCConfig.COMMON_CONFIG.OWNERSHIP.enableResetOwnerScroll.get()
                && MoCTools.isThisPlayerAnOP(player)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            if (!this.world.isRemote) {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwnerId(null);

            }
            return true;
        }
        //if the player interacting is not the owner, do nothing!
        if (MoCConfig.COMMON_CONFIG.OWNERSHIP.enableOwnership.get() && getOwnerId() != null
                && !player.getUniqueID().equals(this.getOwnerId()) && !MoCTools.isThisPlayerAnOP(player)) {
            return true;
        }

        //changes name
        if (!this.world.isRemote && !stack.isEmpty() && getIsTamed()
                && (stack.getItem() == MoCItems.MEDALLION || stack.getItem() == Items.BOOK || stack.getItem() == Items.NAME_TAG)) {
            if (MoCTools.tameWithName(player, this)) {
                return true;
            }
            return false;
        }

        //sets it free, untamed
        if (!stack.isEmpty() && getIsTamed() && ((stack.getItem() == MoCItems.SCROLLOFFREEDOM))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            if (!this.world.isRemote) {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwnerId(null);
                this.setPetName("");
                this.dropMyStuff();
                this.setTamed(false);
            }

            return true;
        }

        //removes owner, any other player can claim it by renaming it
        if (!stack.isEmpty() && getIsTamed() && ((stack.getItem() == MoCItems.SCROLLOFSALE))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            if (!this.world.isRemote) {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwnerId(null);
            }
            return true;
        }

        if (!stack.isEmpty() && getIsTamed() && isMyHealFood(stack)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            if (!this.world.isRemote) {
                this.setHealth(getMaxHealth());
            }
            return true;
        }

        //stores in fishnet
        if (!stack.isEmpty() && stack.getItem() == MoCItems.FISHNET && stack.getDamage() == 0 && this.canBeTrappedInNet()) {
            if (!this.world.isRemote) {
                MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerId());
                if (petData != null) {
                    petData.setInAmulet(this.getOwnerPetId(), true);
                }
            }
            player.setHeldItem(hand, ItemStack.EMPTY);
            if (!this.world.isRemote) {
                MoCTools.dropAmulet(this, 1, player);
                this.remove();
            }

            return true;
        }

        return null;
    }

    // Fixes despawn issue when chunks unload and duplicated mounts when disconnecting on servers
    @Override
    public void remove() {
        if (!this.world.isRemote && getIsTamed() && getHealth() > 0 && !this.riderIsDisconnecting) {
            return;
        }
        super.remove();
    }

    /**
     * Play the taming effect, will either be hearts or smoke depending on
     * status
     */
    @Override
    public void playTameEffect(boolean par1) {
        BasicParticleType particleType = ParticleTypes.HEART;

        if (!par1) {
            particleType = ParticleTypes.SMOKE;
        }

        for (int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.addParticle(particleType, this.getPosX() + this.rand.nextFloat() * this.getWidth() * 2.0F - this.getWidth(), this.getPosY() + 0.5D
                    + this.rand.nextFloat() * this.getHeight(), this.getPosZ() + this.rand.nextFloat() * this.getWidth() * 2.0F - this.getWidth(), d0, d1, d2);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Tamed", getIsTamed());
        if (this.getOwnerId() != null) {
            nbttagcompound.putString("OwnerUUID", this.getOwnerId().toString());
        }
        if (getOwnerPetId() != -1) {
            nbttagcompound.putInt("PetId", this.getOwnerPetId());
        }
        if (this instanceof IMoCTameable && getIsTamed() && MoCreatures.instance.mapData != null) {
            MoCreatures.instance.mapData.updateOwnerPet(this);
        }
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setTamed(nbttagcompound.getBoolean("Tamed"));
        String s = "";
        if (nbttagcompound.contains("OwnerUUID", 8))
        {
            s = nbttagcompound.getString("OwnerUUID");
        }
        if (!s.isEmpty())
        {
            this.setOwnerId(UUID.fromString(s));
        }
        if (nbttagcompound.contains("PetId")) {
            setOwnerPetId(nbttagcompound.getInt("PetId"));
        }
        if (this.getIsTamed() && nbttagcompound.contains("PetId")) {
            MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerId());
            if (petData != null) {
                ListNBT tag = petData.getOwnerRootNBT().getList("TamedList", 10);
                for (int i = 0; i < tag.size(); i++) {
                    CompoundNBT nbt = tag.getCompound(i);
                    if (nbt.getInt("PetId") == nbttagcompound.getInt("PetId")) {
                        // update amulet flag
                        nbt.putBoolean("InAmulet", false);
                        // check if cloned and if so kill
                        if (nbt.contains("Cloned")) {
                            // entity was cloned
                            nbt.remove("Cloned"); // clear flag
                            this.setTamed(false);
                            this.remove();
                        }
                    }
                }
            } else // no pet data was found, mocreatures.dat could of been deleted so reset petId to -1
            {
                this.setOwnerPetId(-1);
            }
        }
    }

    // Override to fix heart animation on clients
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte par1) {
        if (par1 == 2) {
            this.limbSwingAmount = 1.5F;
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.hurtTime = (this.maxHurtTime = 10);
            this.attackedAtYaw = 0.0F;
            playSound(getHurtSound(DamageSource.GENERIC), getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            attackEntityFrom(DamageSource.GENERIC, 0.0F);
        } else if (par1 == 3) {
            playSound(getDeathSound(), getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            setHealth(0.0F);
            onDeath(DamageSource.GENERIC);
        }
    }

    @Override
    public float getPetHealth() {
        return this.getHealth();
    }

    @Override
    public boolean isRiderDisconnecting() {
        return this.riderIsDisconnecting;
    }

    @Override
    public void setRiderDisconnecting(boolean flag) {
        this.riderIsDisconnecting = flag;
    }

    /**
     * Used to spawn hearts at this location
     */
    @Override
    public void spawnHeart() {
        double var2 = this.rand.nextGaussian() * 0.02D;
        double var4 = this.rand.nextGaussian() * 0.02D;
        double var6 = this.rand.nextGaussian() * 0.02D;

        this.world.addParticle(ParticleTypes.HEART, this.getPosX() + this.rand.nextFloat() * this.getWidth() * 2.0F - this.getWidth(), this.getPosY() + 0.5D
                + this.rand.nextFloat() * this.getHeight(), this.getPosZ() + this.rand.nextFloat() * this.getWidth() * 2.0F - this.getWidth(), var2, var4, var6);
    }

    /**
     * ready to start breeding
     */
    @Override
    public boolean readytoBreed() {
        return !this.isBeingRidden() && this.getRidingEntity() == null && this.getIsTamed() && this.getHasEaten() && this.getIsAdult();
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        return "";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 0;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return mate instanceof IMoCTameable;
    }
    
    @Override
    public void livingTick() {
        super.livingTick();
        //breeding code
        if (!this.world.isRemote && readytoBreed() && this.rand.nextInt(100) == 0) {
            doBreeding();
        }
    }

    /**
     * fixes despawning tamed creatures
     */
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (this.getIsTamed()) {
            return false;
        }

        return super.isEntityInsideOpaqueBlock();
    }

    /**
     * Breeding code
     */
    protected void doBreeding() { //TODO: Make breeding code work in both aquatic and ambients
        int i = 0;

        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(8D, 3D, 8D));
        for (int j = 0; j < list.size(); j++) {
            Entity entity = list.get(j);
            if (compatibleMate(entity)) {
                i++;
            }
        }

        if (i > 1) {
            return;
        }

        List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(4D, 2D, 4D));
        for (int k = 0; k < list1.size(); k++) {
            Entity mate = list1.get(k);
            if (!(compatibleMate(mate)) || (mate == this)) {
                continue;
            }

            if (!this.readytoBreed()) {
                return;
            }

            if (!((IMoCTameable) mate).readytoBreed()) {
                return;
            }

            setGestationTime(getGestationTime()+1);
//            if (!this.world.isRemote) {
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHeart(this.getEntityId()),
//                        new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//            }

            if (getGestationTime() <= 50) {
                continue;
            }

            try {

                String offspringName = this.getOffspringClazz((IMoCTameable) mate);

//                LivingEntity offspring = (LivingEntity) EntityList.createEntityByIDFromName(new ResourceLocation(MoCConstants.MOD_PREFIX + offspringName.toLowerCase()), this.world);
//                if (offspring != null && offspring instanceof IMoCTameable) {
//                    IMoCTameable baby = (IMoCTameable) offspring;
//                    ((LivingEntity) baby).setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
//                    this.world.addEntity((LivingEntity) baby);
//                    baby.setAdult(false);
//                    baby.setEdad(35);
//                    baby.setTamed(true);
//                    baby.setOwnerId(this.getOwnerId());
//                    baby.setType(getOffspringTypeInt((IMoCTameable) mate));
//
//                    PlayerEntity entityplayer = this.world.getPlayerByUuid(this.getOwnerId());
//                    if (entityplayer != null) {
//                        MoCTools.tameWithName(entityplayer, baby);
//                    }
//                }
                MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);

            } catch (Exception e) {
            }

            this.setHasEaten(false);
            this.setGestationTime(0);
            ((IMoCTameable) mate).setHasEaten(false);
            ((IMoCTameable) mate).setGestationTime(0);
            break;
        }
    }

    @Override
    public void setHasEaten(boolean flag) {
        hasEaten = flag;
    }

    /**
     * used to determine if the entity has eaten and is ready to breed
     */
    @Override
    public boolean getHasEaten() {
        return hasEaten;
    }

    @Override
    public void setGestationTime(int time) {
        gestationtime = time;
    }

    /**
     * returns breeding timer
     */
    @Override
    public int getGestationTime() {
        return gestationtime;
    }
}
