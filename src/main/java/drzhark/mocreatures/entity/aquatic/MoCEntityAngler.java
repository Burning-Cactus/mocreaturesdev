package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityAngler extends MoCEntitySmallFish{

    public MoCEntityAngler(EntityType<? extends MoCEntityAngler> type, World world) {
        super(type, world);
        this.setType(3);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("smallfish_angler.png");
    }

    @Override
    protected int getEggNumber() {
        return 82;
    }
}
