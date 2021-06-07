package drzhark.mocreatures.util;

import drzhark.mocreatures.registry.MoCItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.function.Supplier;

public enum MoCArmorMaterial implements IArmorMaterial {

    CROCARMOR("crocarmor", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ARMOR_EQUIP_GENERIC, 0, () -> {
        return Ingredient.of(new IItemProvider[] {MoCItems.HIDECROC});
    }),
    FURARMOR("furarmor", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ARMOR_EQUIP_GENERIC, 0, () -> {
        return Ingredient.of(new IItemProvider[] {MoCItems.FUR});
    }),
    HIDEARMOR("hidearmor", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ARMOR_EQUIP_GENERIC, 0, () -> {
        return Ingredient.of(new IItemProvider[] {MoCItems.ANIMALHIDE});
    }),
    SCORPDARMOR("scorpdarmor", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ARMOR_EQUIP_GENERIC, 0, () -> {
        return Ingredient.of(new IItemProvider[] {MoCItems.CHITIN});
    }),
    SCORPFARMOR("scorpfarmor", 18, new int[] {2, 7, 6, 2}, 12, SoundEvents.ARMOR_EQUIP_GENERIC, 0, () -> {
        return Ingredient.of(new IItemProvider[] {MoCItems.CHITINFROST});
    }),
    SCORPNARMOR("scorpnarmor", 20, new int[] {2, 7, 6, 3}, 15, SoundEvents.ARMOR_EQUIP_GENERIC, 0, () -> {
        return Ingredient.of(new IItemProvider[] {MoCItems.CHITINNETHER});
    }),
    SCORPCARMOR("scorpcarmor", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ARMOR_EQUIP_GENERIC, 0, () -> {
        return Ingredient.of(new IItemProvider[] {MoCItems.CHITINCAVE});
    }),
    SILVERARMOR("silverarmor", 15, new int[] {2, 6, 5, 2}, 15, SoundEvents.ARMOR_EQUIP_GENERIC, 0, () -> Ingredient.EMPTY);

    MoCArmorMaterial(String name, int durability, int[] defense, int enchantabilityIn, SoundEvent soundEvent, float toughness, Supplier<Ingredient> material) {
        this.name = name;
        this.maxDamageFactor = durability;
        this.defense = defense;
        this.enchantability = enchantabilityIn;
        this.sound = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = new LazyValue(material);
    }

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final int maxDamageFactor;
    private final int[] defense;
    private final int enchantability;
    private final SoundEvent sound;
    private final LazyValue<Ingredient> repairMaterial;
    private final String name;
    private final float toughness;

    @Override
    public int getDurabilityForSlot(EquipmentSlotType equipmentSlotType) {
        return this.MAX_DAMAGE_ARRAY[equipmentSlotType.getIndex()]*maxDamageFactor;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType equipmentSlotType) {
        return this.defense[equipmentSlotType.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
