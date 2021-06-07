package drzhark.mocreatures.registry;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class MoCFoods {
    public static final Food TURKEY_RAW;
    public static final Food TURKEY_COOKED;
    public static final Food CRAB_RAW;
    public static final Food CRAB_COOKED;
    public static final Food OMELET;
    public static final Food OSTRICH_RAW;
    public static final Food OSTRICH_COOKED;
    public static final Food RAT_BURGER;
    public static final Food RAT_COOKED;
    public static final Food RAT_RAW;
    public static final Food TURTLE_RAW;
    public static final Food TURTLE_SOUP;

    static {
        TURKEY_RAW = new Food.Builder()
                .nutrition(3)
                .saturationMod(0.3F)
                .effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F)
                .meat()
                .build();
        TURKEY_COOKED = new Food.Builder()
                .nutrition(8)
                .saturationMod(0.6F)
                .meat()
                .build();
        CRAB_RAW = new Food.Builder()
                .nutrition(2)
                .saturationMod(0.3F)
                .effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F)
                .meat()
                .build();
        CRAB_COOKED = new Food.Builder()
                .nutrition(6)
                .saturationMod(0.6F)
                .meat()
                .build();
        OMELET = new Food.Builder()
                .nutrition(4)
                .saturationMod(0.6F)
                .build();
        OSTRICH_RAW = new Food.Builder()
                .nutrition(2)
                .saturationMod(0.3F)
                .effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F)
                .meat()
                .build();
        OSTRICH_COOKED = new Food.Builder()
                .nutrition(6)
                .saturationMod(0.6F)
                .meat()
                .build();
        RAT_BURGER = new Food.Builder()
                .nutrition(8)
                .saturationMod(0.6F)
                .meat()
                .build();
        RAT_COOKED = new Food.Builder()
                .nutrition(4)
                .saturationMod(0.6F)
                .meat()
                .build();
        RAT_RAW = new Food.Builder()
                .nutrition(2)
                .saturationMod(0.3F)
                .meat()
                .build();
        TURTLE_RAW = new Food.Builder()
                .nutrition(2)
                .saturationMod(0.3F)
                .effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F)
                .meat()
                .build();
        TURTLE_SOUP = new Food.Builder()
                .nutrition(6)
                .saturationMod(0.6F)
                .meat()
                .build();
    }
}
