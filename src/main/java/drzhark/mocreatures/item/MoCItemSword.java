package drzhark.mocreatures.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class MoCItemSword extends SwordItem {

    private int specialWeaponType = 0;
    private boolean breakable = false;

    public MoCItemSword(Item.Properties builder) {
        this(ItemTier.IRON, 0, 0, builder);
    }
    public MoCItemSword(IItemTier material, Item.Properties builder) {
        this(material, 0, 0, builder);
    }

    public MoCItemSword(IItemTier material, int strength, float speed, Item.Properties builder) {
        super(material, strength, speed, builder);
    }

    public MoCItemSword(IItemTier material, int strength, float speed, int damageType, boolean fragile, Item.Properties builder) {
        super(material, strength, speed, builder);
        this.specialWeaponType = damageType;
        this.breakable = fragile;
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
        par1ItemStack.damageItem(i, attacker, d -> d.sendBreakAnimation(EquipmentSlotType.MAINHAND));
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
}
