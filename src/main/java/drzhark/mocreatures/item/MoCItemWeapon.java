package drzhark.mocreatures.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCItemWeapon extends MoCItem {

    private float attackDamage;
    private final IItemTier material;
    private int specialWeaponType = 0;
    private boolean breakable = false;

    public MoCItemWeapon(IItemTier materialIn, Item.Properties builder) {
        super(builder.maxDamage(materialIn.getMaxUses()).maxStackSize(1));
        this.material = materialIn;
        this.attackDamage = 4F + materialIn.getAttackDamage();
    }

    /**
     *
     * @param materialIn
     * @param fragile
     * @param damageType 0 = default, 1 = poison, 2 = slow down, 3 = fire, 4 =
     *        confusion, 5 = blindness
     */
    public MoCItemWeapon(IItemTier materialIn, int damageType, boolean fragile, Item.Properties builder) {
        this(materialIn, builder);
        this.specialWeaponType = damageType;
        this.breakable = fragile;
    }

    /**
     * Returns the amount of damage this item will deal. One heart of damage is equal to 2 damage points.
     */
    public float getAttackDamage() {
        return this.material.getAttackDamage();
    }

    public float getStrVsBlock(ItemStack stack, BlockState state) {
        if (state.getBlock() == Blocks.COBWEB) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES
                    && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     *  
     * @param target The Entity being hit
     * @param attacker the attacking entity
     */
    @Override
    public boolean hitEntity(ItemStack par1ItemStack, LivingEntity target, LivingEntity attacker) {
        int i = 1;
        if (this.breakable) {
            i = 10;
        }
        par1ItemStack.damageItem(i, attacker, d -> d.sendBreakAnimation(EquipmentSlotType.MAINHAND)); //TODO: Add lambda function the way vanilla does it to send break animation
        int potionTime = 100;
        switch (this.specialWeaponType) {
            case 1: //poison
                target.addPotionEffect(new EffectInstance(Effects.POISON, potionTime, 0));
                break;
            case 2: //frost slowdown
                target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, potionTime, 0));
                break;
            case 3: //fire
                target.setFire(10);
                break;
            case 4: //confusion
                target.addPotionEffect(new EffectInstance(Effects.NAUSEA, potionTime, 0));
                break;
            case 5: //blindness
                target.addPotionEffect(new EffectInstance(Effects.BLINDNESS, potionTime, 0));
                break;
            default:
                break;
        }

        return true;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, LivingEntity par6EntityLiving) {
        par1ItemStack.damageItem(2, par6EntityLiving, d -> d.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    /**
     * returns the action that specifies what animation to play when the items
     * is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.BLOCK;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    @Override
    public boolean canHarvestBlock(BlockState state) {
        return state.getBlock() == Blocks.COBWEB;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based
     * on material.
     */
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity playerIn) {
        if ((double) state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, playerIn, d -> d.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }

        return true;
    }

    /**
     * Return the name for this tool's material.
     */
    public String getToolMaterialName() {
        return this.material.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     *  
     * @param toRepair The ItemStack to be repaired
     * @param repair The ItemStack that should repair this Item (leather for leather armor, etc.)
     */
//    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
//        Ingredient mat = this.material.getRepairMaterial();
//        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false))
//            return true;
//        return super.getIsRepairable(toRepair, repair);
//    } TODO: Figure out a replacement for the oredictionary

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @SuppressWarnings("deprecation")
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier",
                    (double) this.attackDamage, 0));
        }
        return multimap;
    }
}
