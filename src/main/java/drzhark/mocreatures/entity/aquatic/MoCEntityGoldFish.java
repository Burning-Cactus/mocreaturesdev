package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityGoldFish extends MoCEntitySmallFish{

    public MoCEntityGoldFish(EntityType<? extends MoCEntityGoldFish> type, World world) {
        super(type, world);
        this.setType(5);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("smallfish_goldfish.png");
    }

    @Override
    protected int getEggNumber() {
        return 84;
    }
}
