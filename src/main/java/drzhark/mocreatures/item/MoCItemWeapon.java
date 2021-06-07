package drzhark.mocreatures.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MoCItemWeapon extends MoCItem {

    private float attackDamage;
    private final IItemTier material;
    private int specialWeaponType = 0;
    private boolean breakable = false;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public MoCItemWeapon(IItemTier materialIn, Item.Properties builder) {
        super(builder.durability(materialIn.getUses()));
        this.material = materialIn;
        this.attackDamage = 4F + materialIn.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
        attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = attributeBuilder.build();
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
        return this.material.getAttackDamageBonus();
    }

    public float getStrVsBlock(ItemStack stack, BlockState state) {
        if (state.getBlock() == Blocks.COBWEB) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.CORAL && material != Material.LEAVES
                    && material != Material.VEGETABLE ? 1.0F : 1.5F;
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
    public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity target, LivingEntity attacker) {
        int i = 1;
        if (this.breakable) {
            i = 10;
        }
        par1ItemStack.hurtAndBreak(i, attacker, d -> d.broadcastBreakEvent(EquipmentSlotType.MAINHAND)); //TODO: Add lambda function the way vanilla does it to send break animation
        int potionTime = 100;
        switch (this.specialWeaponType) {
            case 1: //poison
                target.addEffect(new EffectInstance(Effects.POISON, potionTime, 0));
                break;
            case 2: //frost slowdown
                target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, potionTime, 0));
                break;
            case 3: //fire
                target.setSecondsOnFire(10);
                break;
            case 4: //confusion
                target.addEffect(new EffectInstance(Effects.CONFUSION, potionTime, 0));
                break;
            case 5: //blindness
                target.addEffect(new EffectInstance(Effects.BLINDNESS, potionTime, 0));
                break;
            default:
                break;
        }

        return true;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, LivingEntity par6EntityLiving) {
        par1ItemStack.hurtAndBreak(2, par6EntityLiving, d -> d.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        return true;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public boolean isFull3D() {
//        return true;
//    }

    /**
     * returns the action that specifies what animation to play when the items
     * is being used
     */
    @Override
    public UseAction getUseAnimation(ItemStack par1ItemStack) {
        return UseAction.BLOCK;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.getBlock() == Blocks.COBWEB;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based
     * on material.
     */
    @Override
    public int getEnchantmentValue() {
        return this.material.getEnchantmentValue();
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity playerIn) {
        if ((double) state.getDestroySpeed(worldIn, pos) != 0.0D) {
            stack.hurtAndBreak(2, playerIn, d -> d.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
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
     * @return
     */
    @SuppressWarnings("deprecation")
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }
}
