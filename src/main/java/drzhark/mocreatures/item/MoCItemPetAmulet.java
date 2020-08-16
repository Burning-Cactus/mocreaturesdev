package drzhark.mocreatures.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

public class MoCItemPetAmulet extends MoCItem {

    private String name;
    private float health;
    private int edad;
    private int creatureType;
    private String spawnClass;
    private String ownerName;
    private UUID ownerUniqueId;
    private int amuletType;
    private boolean adult;
    private int PetId;

    public MoCItemPetAmulet(Item.Properties builder) {
        super(builder.maxStackSize(1));
//        setHasSubtypes(true);
    }

    public MoCItemPetAmulet(Item.Properties builder, int type) {
        this(builder);
        this.amuletType = type;
    }

    /*@Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        double dist = 1D;
        double newPosY = player.getPosY();
        double newPosX = player.getPosX() - (dist * Math.cos((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = player.getPosZ() - (dist * Math.sin((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));

        ItemStack emptyAmulet = new ItemStack(MoCItems.FISHNET, 1);
        if (this.amuletType == 1) {
            emptyAmulet = new ItemStack(MoCItems.PETAMULET, 1);
        }

        if (!world.isRemote) {
            initAndReadNBT(stack);
            if (this.spawnClass.isEmpty()) {
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }
            try {
                this.spawnClass = this.spawnClass.replace(MoCConstants.MOD_PREFIX, "").toLowerCase();
                if (this.spawnClass.equalsIgnoreCase("MoCHorse")) {
                    this.spawnClass = "WildHorse";
                } else if (this.spawnClass.equalsIgnoreCase("PolarBear")) {
                    this.spawnClass = "WildPolarBear";
                }

                if (this.spawnClass.equalsIgnoreCase("Ray")) {
                    switch (this.creatureType) {
                        case 1:
                            this.spawnClass = "MantaRay";
                            break;
                        case 2:
                            this.spawnClass = "StingRay";
                            break;
                    }
                }
//                LivingEntity tempLiving = (LivingEntity) EntityList.createEntityByIDFromName(new ResourceLocation(MoCConstants.MOD_PREFIX + this.spawnClass.toLowerCase()), world);
                if (tempLiving != null && tempLiving instanceof IMoCEntity) {
                    IMoCTameable storedCreature = (IMoCTameable) tempLiving;
                    ((LivingEntity) storedCreature).setPosition(newPosX, newPosY, newPosZ);
                    storedCreature.setType(this.creatureType);
                    storedCreature.setTamed(true);
                    storedCreature.setPetName(this.name);
                    storedCreature.setOwnerPetId(this.PetId);
                    storedCreature.setOwnerId(player.getUniqueID());
                    this.ownerName = player.getName().toString();
                    ((LivingEntity) storedCreature).setHealth(this.health);
                    storedCreature.setEdad(this.edad);
                    storedCreature.setAdult(this.adult);
                    if (storedCreature instanceof MoCEntityBigCat) {
                        ((MoCEntityBigCat) storedCreature).setHasAmulet(true);
                    }
                    // special case for kitty
                    if (this.spawnClass.equalsIgnoreCase("Kitty")) {
                        ((MoCEntityKitty) storedCreature).setKittyState(3); // allows name to render
                    }

                    if (this.ownerUniqueId == null) {
                        this.ownerUniqueId = player.getUniqueID();
                        if (MoCreatures.instance.mapData != null) {
                            final MoCPetData newOwner = MoCreatures.instance.mapData.getPetData(player.getUniqueID());
                            int maxCount = MoCConfig.COMMON_CONFIG.OWNERSHIP.maxTamedPerPlayer.get();
                            if (MoCTools.isThisPlayerAnOP(player)) {
                                maxCount = MoCConfig.COMMON_CONFIG.OWNERSHIP.maxTamedPerOP.get();
                            }
                            if (newOwner == null) {
                                if (maxCount > 0 || !MoCConfig.COMMON_CONFIG.OWNERSHIP.enableOwnership.get()) {
                                    // create new PetData for new owner
                                    MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
                                }
                            } else // add pet to existing pet data
                            {
                                if (newOwner.getTamedList().size() < maxCount || !MoCConfig.COMMON_CONFIG.OWNERSHIP.enableOwnership.get()) {
                                    MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
                                }
                            }
                        }
                    } else {
                        //if the player using the amulet is different than the original owner
                        if (!(this.ownerUniqueId.equals(player.getUniqueID())) && MoCreatures.instance.mapData != null) {
                            MoCPetData oldOwner = MoCreatures.instance.mapData.getPetData(this.ownerUniqueId);
                            MoCPetData newOwner = MoCreatures.instance.mapData.getPetData(player.getUniqueID());
                            int maxCount = MoCConfig.COMMON_CONFIG.OWNERSHIP.maxTamedPerPlayer.get();
                            if (MoCTools.isThisPlayerAnOP(player)) {
                                maxCount = MoCConfig.COMMON_CONFIG.OWNERSHIP.maxTamedPerOP.get();
                            }
                            if ((newOwner != null && newOwner.getTamedList().size() < maxCount) || (newOwner == null && maxCount > 0)
                                    || !MoCConfig.COMMON_CONFIG.OWNERSHIP.enableOwnership.get()) {
                                MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
                            }
                            // remove pet entry from old owner
                            if (oldOwner != null) {
                                for (int j = 0; j < oldOwner.getTamedList().size(); j++) {
                                    CompoundNBT petEntry = oldOwner.getTamedList().getCompound(j);
                                    if (petEntry.getInt("PetId") == this.PetId) {
                                        // found match, remove
                                        oldOwner.getTamedList().remove(j);
                                    }
                                }
                            }
                        }
                    }

                    if (player.getEntityWorld().addEntity((LivingEntity) storedCreature)) {
                        MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAppear(((LivingEntity) storedCreature).getEntityId()),
                                new TargetPoint(player.getEntityWorld().dimension.getType().getId(), player.getPosX(), player.getPosY(),
                                        player.getPosZ(), 64));
                        if (this.ownerUniqueId == null || this.name.isEmpty()) {
                            MoCTools.tameWithName(player, storedCreature);
                        }

                        player.setHeldItem(hand, emptyAmulet);
                        MoCPetData petData = MoCreatures.instance.mapData.getPetData(storedCreature.getOwnerId());
                        if (petData != null) {
                            petData.setInAmulet(storedCreature.getOwnerPetId(), false);
                        }
                    }
                }
            } catch (Exception ex) {
                if (MoCConfig.COMMON_CONFIG.GLOBAL.debug.get()) {
                    MoCLog.logger.warn("Error spawning creature from fishnet/amulet " + ex);
                }
            }
        }

        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }
*/
    public void readFromNBT(CompoundNBT nbt) {
        this.PetId = nbt.getInt("PetId");
        this.creatureType = nbt.getInt("CreatureType");
        this.health = nbt.getFloat("Health");
        this.edad = nbt.getInt("Edad");
        this.name = nbt.getString("Name");
        this.spawnClass = nbt.getString("SpawnClass");
        this.adult = nbt.getBoolean("Adult");
        this.ownerName = nbt.getString("OwnerName");
        if (nbt.hasUniqueId("OwnerUUID")) {
            this.ownerUniqueId = nbt.getUniqueId("OwnerUUID");
        }
    }

    public void writeToNBT(CompoundNBT nbt) {
        nbt.putInt("PetID", this.PetId);
        nbt.putInt("CreatureType", this.creatureType);
        nbt.putFloat("Health", this.health);
        nbt.putInt("Edad", this.edad);
        nbt.putString("Name", this.name);
        nbt.putString("SpawnClass", this.spawnClass);
        nbt.putBoolean("Adult", this.adult);
        nbt.putString("OwnerName", this.ownerName);
        if (this.ownerUniqueId != null) {
            nbt.putUniqueId("OwnerUUID", ownerUniqueId);
        }
    }

    @OnlyIn(Dist.CLIENT)
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        initAndReadNBT(stack);
        if (this.spawnClass != "") {
            tooltip.add(new StringTextComponent(TextFormatting.AQUA + this.spawnClass));
        }
        if (this.name != "") {
            tooltip.add(new StringTextComponent(TextFormatting.BLUE + this.name));
        }
        if (this.ownerName != "") {
            tooltip.add(new StringTextComponent(TextFormatting.DARK_BLUE + "Owned by " + this.ownerName));
        }
    }

    private void initAndReadNBT(ItemStack itemstack) {
        if (itemstack.getTag() == null) {
            itemstack.setTag(new CompoundNBT());
        }
        CompoundNBT nbtcompound = itemstack.getTag();
        readFromNBT(nbtcompound);
    }
}
