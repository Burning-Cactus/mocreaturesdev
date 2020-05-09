package drzhark.mocreatures.init;

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
                .hunger(3)
                .saturation(0.3F)
                .effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F)
                .meat()
                .build();
        TURKEY_COOKED = new Food.Builder()
                .hunger(8)
                .saturation(0.6F)
                .meat()
                .build();
        CRAB_RAW = new Food.Builder()
                .hunger(2)
                .saturation(0.3F)
                .effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F)
                .meat()
                .build();
        CRAB_COOKED = new Food.Builder()
                .hunger(6)
                .saturation(0.6F)
                .meat()
                .build();
        OMELET = new Food.Builder()
                .hunger(4)
                .saturation(0.6F)
                .build();
        OSTRICH_RAW = new Food.Builder()
                .hunger(2)
                .saturation(0.3F)
                .effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F)
                .meat()
                .build();
        OSTRICH_COOKED = new Food.Builder()
                .hunger(6)
                .saturation(0.6F)
                .meat()
                .build();
        RAT_BURGER = new Food.Builder()
                .hunger(8)
                .saturation(0.6F)
                .meat()
                .build();
        RAT_COOKED = new Food.Builder()
                .hunger(4)
                .saturation(0.6F)
                .meat()
                .build();
        RAT_RAW = new Food.Builder()
                .hunger(2)
                .saturation(0.3F)
                .meat()
                .build();
        TURTLE_RAW = new Food.Builder()
                .hunger(2)
                .saturation(0.3F)
                .effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F)
                .meat()
                .build();
        TURTLE_SOUP = new Food.Builder()
                .hunger(6)
                .saturation(0.6F)
                .meat()
                .build();
    }
}
