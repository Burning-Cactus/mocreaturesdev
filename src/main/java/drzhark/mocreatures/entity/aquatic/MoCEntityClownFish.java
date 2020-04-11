package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityClownFish extends MoCEntitySmallFish{

    public MoCEntityClownFish(EntityType<? extends MoCEntityClownFish> type, World world) {
        super(type, world);
        this.setType(4);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("smallfish_clownfish.png");
    }

    @Override
    protected int getEggNumber() {
        return 83;
    }
}
